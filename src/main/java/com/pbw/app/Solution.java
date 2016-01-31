package com.pbw.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michal on 31.01.2016.
 */
public class Solution {
    public Solution(Problem problem, Map<Integer, List<Route>> routesByCar) throws NieMaCustomeraWChujSrogiException {
        this.problem = problem;
        this.routesByCar = routesByCar;
        this.totalDistance = 0;
        this.totalLoad = 0;
        this.totalRoutes = 0;
        this.customers = problem.getCustomers();
        this.carCount = problem.getVehicles().size();

        this.calculateTotalDistance();
    }

    private void calculateTotalDistance() throws NieMaCustomeraWChujSrogiException {
            for (Integer carId : routesByCar.keySet()) {
                List<Route> routesBySingleCar = routesByCar.get(carId);
                for (Route r : routesBySingleCar) {
                    Integer fromCustomerId = r.getCustomerFromId();

                    if (fromCustomerId > 0) {
                        Customer fromCustomer = Customer.findByNo(fromCustomerId, customers);
                        totalLoad += fromCustomer.getDemand();
                    }

                    totalDistance += r.getDistance();
                    totalRoutes += 1;
                }
            }

    }

    public double getAverageDistance() {
        return totalDistance / carCount;
    }

    public double getAverageLoad() {
        return totalLoad / carCount;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getAverageCustomerOnARoad() {
        return customers.size() / carCount;
    }

    public String getProblemName() {
        return problem.getName();
    }

    private int totalRoutes;
    private double totalDistance;
    private double totalLoad;
    private Problem problem;
    private int carCount;
    private Map<Integer, List<Route>> routesByCar;
    private ArrayList<Customer> customers;

    public Map<Integer, List<Route>> getRoutesByCar() {
        return routesByCar;
    }

    public int getTotalRoutes() {
        return totalRoutes;
    }
}
