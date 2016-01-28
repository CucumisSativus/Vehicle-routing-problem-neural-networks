package com.pbw.app;

import com.pbw.app.SolomonReader.*;
import com.pbw.ui.MapWindow;
import com.pbw.ui.NieMaCustomeraWChujSrogiException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    MapWindow mapWindow = new MapWindow(getDummyRoutes());
                    mapWindow.setCustomers(getDummyCustomers());

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

        result.add(new Customer(1, 10, 10, 20, 30, 80, 10));
        result.add(new Customer(2, 100, 10, 40, 40, 60, 10));
        result.add(new Customer(3, 10, 100, 5, 10, 100, 10));

        return result;
    }

    private static Map<java.lang.Integer,List<Route>> getDummyRoutes() {
        return new HashMap<java.lang.Integer, List<Route>>();
    }
}
