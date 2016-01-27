package com.pbw.app;

public class Vehicle {
    int vehicleNumber;
    int capacity;
    int xCoord;
    int yCoord;

    public Vehicle(int vehicleNumber, int capacity) {
        this.vehicleNumber = vehicleNumber;
        this.capacity = capacity;
        this.xCoord = 0;
        this.yCoord = 0;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
}
