package com.pbw.app;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Kohonen;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.Normalizer;

import java.util.*;

public class Clusterer {
    public Clusterer(int gridWidth, int gridHeight, ArrayList<Customer> customers, int clusterResolution, int maxClustersNum) {
        this.network = NeuralNetworkFactory.createKohonen(2, 25);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.customers = customers;
        this.clusterResolution = clusterResolution;
        this.maxClustersNum = maxClustersNum;
        prepareClusters();
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

    Map<Integer, ArrayList<Customer>> getClusters(){
        for(Customer customer : customers) {
            network.setInput(customer.getxCoord(), customer.getyCoord());
            network.calculate();
            double[] output = network.getOutput();
            int clusterId = roundToNearest((int)average(output), clusterResolution);
            clusteredCustomers.get(clusterId).add(customer);

        }
        return clusteredCustomers;
    }

    private double average(double[] neuronOutputs){
        double sum = 0;
        for(double d : neuronOutputs) { sum += d; }
        return sum/neuronOutputs.length;
    }

    private int roundToNearest(int number, int nearest){
        return Math.round((int)(Math.round( number / nearest) * nearest));
    }

    private void prepareClusters(){
        clusteredCustomers = new HashMap<Integer, ArrayList<Customer>>();
        for(int i = 0; i < maxClustersNum; ++i ){
            clusteredCustomers.put(i * clusterResolution, new ArrayList<Customer>());
        }
    }


    private DataSetRow prepareRow(Customer c){
        return new DataSetRow(new double[]{c.getxCoord(), c.getyCoord()});
    }
    private Kohonen network;
    private int gridWidth;
    private int gridHeight;
    private ArrayList<Customer> customers;
    private int clusterResolution;
    private int maxClustersNum;
    private Map<Integer, ArrayList<Customer>> clusteredCustomers;


}
