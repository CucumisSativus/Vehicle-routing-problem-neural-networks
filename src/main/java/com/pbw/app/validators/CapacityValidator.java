package com.pbw.app.validators;

import com.pbw.app.Customer;

import java.util.ArrayList;

/**
 * Created by michal on 31.01.2016.
 */
public class CapacityValidator {

    public CapacityValidator(int capacity) {
        this.capacity = capacity;
    }

    public boolean validate(ArrayList<Customer> customers){
        int totalNeededCapacity = 0;
        for (Customer c : customers) {
            totalNeededCapacity += c.getDemand();
        }
        return totalNeededCapacity <= capacity;
    }
    private int capacity;
}
