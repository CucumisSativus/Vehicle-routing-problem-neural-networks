package com.pbw.app;

import com.pbw.app.SolomonReader.*;
import com.pbw.app.validators.AvailabilityValidator;
import com.pbw.app.validators.CapacityValidator;
import com.pbw.ui.MapWindow;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            System.out.println("usage problems_path solution_path");
            System.exit(1);
        }

        ProblemReader reader = new ProblemReader(new SolomonReader(args[0]));

        for (Problem p : reader.getProblems()) {
            for(int solutionRerun =0; solutionRerun < 5; ++solutionRerun) {
                Solution s = processProblem(p);
                new SolutionSaver(s, solutionRerun, args[1]).saveToFile();
            }
        }
        final Problem p = reader.getProblems().get(0);

    }

    private static Solution processProblem(final Problem p) {
        ArrayList<Customer> customers = p.getCustomers();
        final Customer depot = customers.remove(0);

        System.out.println(p.getName());
        System.out.println("Customer num" + p.getCustomers().size());
        System.out.println("Vehicles num" + p.getVehicles().size());

        Clusterer clusterer = new Clusterer(
                p.getGridWidth(),
                p.getGridHeight(),
                customers,
                3, 100);
        clusterer.train();

        Map<Integer, ArrayList<Customer>> clusters= clusterer.getClusters();
        final Map<Integer, List<Route>> allRoutes = new HashMap<Integer, List<Route>>();

        CapacityValidator capacityValidator = new CapacityValidator(p.getVehicleCapacity());

        for (Integer clusterId : clusters.keySet()) {
            System.out.println("Cluster " + clusterId);
            ArrayList<Customer> customersInCluster = clusters.get(clusterId);
            System.out.println("Capacity validation " + capacityValidator.validate(customersInCluster));
            for(Customer c : customersInCluster){
                System.out.println("\t" + c.getCustNo() + " x: " + c.getxCoord() + " y: " + c.getyCoord());
            }
            System.out.println();

            RouteFinder finder = new RouteFinder(depot, customersInCluster);
            List<Route> routes = finder.preprareRoutes();

            AvailabilityValidator availabilityValidator = new AvailabilityValidator(
                    concatenateCustomers(depot, customersInCluster)
            );
            System.out.println("Time validation " + availabilityValidator.validate(routes));
            for (Route r :
                    routes) {
                System.out.println("From: " + r.getCustomerFromId() + " to " + r.getCustomerToId());
            }

            allRoutes.put(clusterId, routes);
        }

//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                    MapWindow mapWindow = new MapWindow(allRoutes);
//                    mapWindow.setCustomers(concatenateCustomers(depot, p.getCustomers()));
//            }
//        });

        return new Solution(p, allRoutes);
    }

    private static List<Customer> concatenateCustomers(Customer startPoint, ArrayList<Customer> customersInCluster){
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(startPoint);
        customers.addAll(customersInCluster);
        return customers;
    }
}
