package com.pbw.app;

import com.pbw.app.SolomonReader.*;
import org.neuroph.util.NeuralNetworkFactory;

import java.io.FileNotFoundException;

public class App
{
    public static void main( String[] args ) throws FileNotFoundException {
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
        for(int i =0; i<p.getCustomers().size(); ++i) {
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
}
