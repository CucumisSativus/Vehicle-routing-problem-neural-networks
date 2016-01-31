package com.pbw.app.validators;

import com.pbw.app.Customer;
import com.pbw.app.NieMaCustomeraWChujSrogiException;
import com.pbw.app.Route;

import java.util.List;

/**
 * Created by Krzysiek on 2016-01-31.
 */
public class AvailabilityValidator {

    private final List<Customer> customers;

    public AvailabilityValidator(List<Customer> customers) {
        this.customers = customers;
    }

    public Boolean validate(List<Route> routes) {
        try {
            if(routes.size() == 0) {
                return true;
            }

            Boolean result = true;
            Integer pointInTime = 0;
            Integer routesSize = routes.size();
            Integer lastCustomerId = routes.get(routesSize-1).getCustomerToId();
            Customer lastCustomer = Customer.findByNo(lastCustomerId, this.customers);

            for (Route singleRoute : routes) {
                Customer customerFrom = Customer.findByNo(singleRoute.getCustomerFromId(), this.customers);

                if(pointInTime < customerFrom.getReadyTime()) {
                    pointInTime = customerFrom.getReadyTime();
                }

                if(!customerFrom.isAvailableAtTime(pointInTime)) {
                    return false;
                }

                pointInTime += customerFrom.getServiceTime();

                if(!customerFrom.isAvailableAtTime(pointInTime)) {
                    return false;
                }

                pointInTime += singleRoute.getTime();
            }

            if(!lastCustomer.isAvailableAtTime(pointInTime)) {
                return false;
            }

            pointInTime += lastCustomer.getServiceTime();

            if(!lastCustomer.isAvailableAtTime(pointInTime)) {
                return false;
            }

            return result;
        } catch (NieMaCustomeraWChujSrogiException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
