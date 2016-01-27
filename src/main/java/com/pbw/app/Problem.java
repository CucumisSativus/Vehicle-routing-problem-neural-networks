package com.pbw.app;

import java.util.ArrayList;

public class Problem {
    public Problem(String name, ArrayList<Customer> customers, ArrayList<Vehicle> vehicles) {
        this.customers = customers;
        this.vehicles = vehicles;
        this.name = name;
        findGridSize();

    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public String getName() {
        return name;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    private void findGridSize(){
        int width =0, height =0;
        for(Customer c : customers){
            if(c.getxCoord() > width){
                width = c.getxCoord();
            }
            if(c.getyCoord() > height){
                height = c.getyCoord();
            }
        }

        this.gridWidth = width;
        this.gridHeight = height;
    }

    private ArrayList<Customer> customers;
    private ArrayList<Vehicle> vehicles;
    private String name;
    private int gridWidth;
    private int gridHeight;

}
