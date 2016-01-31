package com.pbw.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michal on 31.01.2016.
 */
public class Solution {
    public Solution(Problem problem, Map<Integer, List<Route>> routesByCar) {
        this.problem = problem;
        this.routesByCar = routesByCar;
        this.totalDistance =0;
        this.totalLoad = 0;
        this.totalRoutes = 0;
        this.customers = problem.getCustomers();
        this.carCount = problem.getVehicles().size();
    }

    private void calculateTotalDistance() throws NieMaCustomeraWChujSrogiException {
        for (Integer carId : routesByCar.keySet()) {
            for (Route r : routesByCar.get(carId)) {
                totalDistance += r.getDistance();

                Customer fromCustomer = Customer.findByNo(r.getCustomerFromId(), customers);
                totalLoad += fromCustomer.getDemand();

                totalRoutes += 1;
            }
        }
    }

    public double getAverageDistance(){
        return totalDistance / carCount;
    }

    public double getAverageLoad(){
        return totalLoad / carCount;
    }

    public double getTotalDistance(){
        return totalDistance;
    }

    public double getAverageCustomerOnARoad(){
        return customers.size() / carCount;
    }

    public String getProblemName(){
        return problem.getName();
    }

    private int totalRoutes;
    private double totalDistance;
    private double totalLoad;
    private Problem problem;
    private int carCount;
    private Map<Integer, List<Route>> routesByCar;
    private ArrayList<Customer> customers;
}
