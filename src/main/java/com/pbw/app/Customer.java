package com.pbw.app;

/**
 * Created by michal on 27.01.2016.
 */
public class Customer {
    private int custNo;
    private int xCoord;
    private int yCoord;
    private int demand;
    private int readyTime;
    private int dueDate;
    private int serviceTime;

    public Customer(int custNo, int xCoord, int yCoord, int demand, int readyTime, int dueDate, int service) {
        this.custNo = custNo;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.demand = demand;
        this.readyTime = readyTime;
        this.dueDate = dueDate;
        this.serviceTime = service;
    }

    public int getCustNo() {
        return custNo;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public int getDemand() {
        return demand;
    }

    public int getReadyTime() {
        return readyTime;
    }

    public int getDueDate() {
        return dueDate;
    }

    public int getServiceTime() {
        return serviceTime;
    }
}
