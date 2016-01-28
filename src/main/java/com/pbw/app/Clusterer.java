package com.pbw.app;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Kohonen;
import org.neuroph.nnet.learning.kmeans.KMeansClustering;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.Normalizer;

import java.util.ArrayList;

public class Clusterer {
    public Clusterer( int vehicleCapacity, int gridWidth, int gridHeight, ArrayList<Customer> customers) {
        this.network = NeuralNetworkFactory.createKohonen(2, 25);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.customers = customers;
    }

    public void train(){
        DataSet trainingSet = new DataSet(2); // x and y coordinates
        for(Customer c: customers){
            trainingSet.addRow(prepareRow(c));
        }

        Normalizer normalizer = new MaxMinNormalizer();
        normalizer.normalize(trainingSet);

        network.learn(trainingSet);
    }

    double[] getClusters(int customerNo){
        Customer customer = customers.get(customerNo);
        network.setInput(customer.getxCoord(), customer.getyCoord());
        network.calculate();
        double[] output = network.getOutput();

        return output;
    }

    private DataSetRow prepareRow(Customer c){
        return new DataSetRow(new double[]{c.getxCoord(), c.getyCoord()});
    }
    private Kohonen network;
    private int gridWidth;
    private int gridHeight;
    private ArrayList<Customer> customers;


}
