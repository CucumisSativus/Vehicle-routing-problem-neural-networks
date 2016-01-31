package com.pbw.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michal on 31.01.2016.
 */
public class RouteFinder {


    public RouteFinder(Customer startpoint, ArrayList<Customer> customersInCluster){
        orderedCustomersInCluster = (ArrayList<Customer>) customersInCluster.clone();
        Collections.sort(orderedCustomersInCluster);
        this.timeStart = orderedCustomersInCluster.get(0).getReadyTime();
        this.startPoint = startpoint;

        timeStop = 0;
        for (Customer c : orderedCustomersInCluster) {
            if(c.getDueDate() > this.timeStop){
                this.timeStop = c.getDueDate();
            }
        }
    }

    List<Route> preprareRoutes(){
        ArrayList<Route> routes = new ArrayList<Route>();
        Customer lastCustomer = startPoint;
        while(orderedCustomersInCluster.size() != 0){
            Customer currentCustomer = orderedCustomersInCluster.remove(0);

            routes.add(new Route(
                    lastCustomer.getCustNo(),
                    currentCustomer.getCustNo(), 0,
                    distance(lastCustomer, currentCustomer)));

            lastCustomer = currentCustomer;
        }
        routes.add(new Route(lastCustomer.getCustNo(), startPoint.getCustNo(), 0, 0));

        return routes;
    }

    private double distance(Customer from, Customer to){
        return Math.sqrt(
          Math.pow(from.getxCoord() - to.getxCoord(), 2) +
                  Math.pow(from.getyCoord() - to.getyCoord(), 2)
        );
    }

    private ArrayList<Customer> orderedCustomersInCluster;
    private Customer startPoint;
    private int timeStart;
    private int timeStop;
}
