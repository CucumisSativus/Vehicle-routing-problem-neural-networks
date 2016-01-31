package com.pbw.app;

/**
 * Created by Krzysiek on 2016-01-28.
 */
public class Route {
    private Integer customerFromId;
    private Integer customerToId;
    private int time;
    private double distance;


    public Route(Integer customerFromId, Integer customerToId, int time, double distance) {
        this.customerFromId = customerFromId;
        this.customerToId = customerToId;
        this.time = time;
        this.distance = distance;
    }

    public Integer getCustomerFromId() {
        return customerFromId;
    }

    public void setCustomerFromId(Integer customerFromId) {
        this.customerFromId = customerFromId;
    }

    public Integer getCustomerToId() {
        return customerToId;
    }

    public void setCustomerToId(Integer customerToId) {
        this.customerToId = customerToId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString(){

        return "From " + (customerFromId > 0 ? customerFromId : "base") + " to " + (customerToId > 0 ? customerToId : "base");
    }
}
