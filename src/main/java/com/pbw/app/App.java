package com.pbw.app;

import com.pbw.app.SolomonReader.*;
import com.pbw.ui.MapWindow;
import com.pbw.ui.NieMaCustomeraWChujSrogiException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.*;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                List<Customer> dummyCustomers = getDummyCustomers();
                    MapWindow mapWindow = new MapWindow(getDummyRoutes(dummyCustomers));
                    mapWindow.setCustomers(dummyCustomers);

            }
        });

        ProblemReader reader = new ProblemReader(new SolomonReader(args[0]));
        Problem p = reader.getProblems().get(0);
        System.out.println(p.getName());
        System.out.println("Customer num" + p.getCustomers().size());
        System.out.println("Vehicles num" + p.getVehicles().size());
        int gridWidth = p.getGridWidth();
        int gridHeight = p.getGridHeight();
        Clusterer clusterer = new Clusterer(
                p.getVehicleCapacity(),
                p.getGridWidth(),
                p.getGridHeight(),
                p.getCustomers()
        );
        clusterer.train();
        for (int i = 0; i < p.getCustomers().size(); ++i) {
            double clusters[] = clusterer.getClusters(i);
            System.out.print("" + i + "\t\t");
            for (double el : clusters) {
                System.out.print("" + (int) el + "\t");
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    private static List<Customer> getDummyCustomers() {
        ArrayList<Customer> result = new ArrayList<Customer>();

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
