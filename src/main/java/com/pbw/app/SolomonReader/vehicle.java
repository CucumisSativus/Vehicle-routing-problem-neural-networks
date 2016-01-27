package com.pbw.app.SolomonReader;
import java.util.ArrayList;

public class Vehicle {
    private ArrayList<Customer> customers;
    private String dataFileSource;
    private int vehicleNumber;
    private int capacity;

    public Vehicle() {
        // TODO Auto-generated constructor stub
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public String getDataFileSource() {
        return dataFileSource;
    }

    public void setDataFileSource(String dataFileSource) {
        this.dataFileSource = dataFileSource;
    }





}
