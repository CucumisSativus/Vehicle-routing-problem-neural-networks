package com.pbw.app;

import com.pbw.app.SolomonReader.*;
import com.pbw.app.validators.AvailabilityValidator;
import com.pbw.app.validators.CapacityValidator;
import com.pbw.ui.MapWindow;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.FileNotFoundException;
import java.util.*;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        ProblemReader reader = new ProblemReader(new SolomonReader(args[0]));
        final Problem p = reader.getProblems().get(0);
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

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    MapWindow mapWindow = new MapWindow(allRoutes);
                    mapWindow.setCustomers(concatenateCustomers(depot, p.getCustomers()));
            }
        });

    }
    private static List<Customer> concatenateCustomers(Customer startPoint, ArrayList<Customer> customersInCluster){
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(startPoint);
        customers.addAll(customersInCluster);
        return customers;
    }

    private static List<Customer> getDummyCustomers() {
        ArrayList<Customer> result = new ArrayList<Customer>();

        //Customer(int custNo, int xCoord, int yCoord, int demand, int readyTime, int dueDate, int service)
        result.add(new Customer(1, 10, 10, 20, 20, 80, 10));
        result.add(new Customer(2, 100, 10, 40, 30, 60, 10));
        result.add(new Customer(3, 10, 100, 5, 0, 100, 10));

        result.add(new Customer(4, 300,200,10, 0, 10, 30));
        result.add(new Customer(5, 200,300,10, 0, 10, 30));
        result.add(new Customer(6, 200,100,10, 40, 100, 20));

        return result;
    }

    private static Map<Integer,List<Route>> getDummyRoutes(List<Customer> customers) {
        HashMap<Integer, List<Route>> result = new HashMap<Integer, List<Route>>();

        List<Route> cluster1Routes = new ArrayList<Route>();
        cluster1Routes.add(new Route(1,2,10));
        cluster1Routes.add(new Route(2,3,20));
        cluster1Routes.add(new Route(3,1,1));
        result.put(1, cluster1Routes);

        List<Route> cluster2Routes = new ArrayList<Route>();
        cluster2Routes.add(new Route(4,5,5));
        cluster2Routes.add(new Route(5,6,100));
        result.put(2, cluster2Routes);

        return result;
    }
}
