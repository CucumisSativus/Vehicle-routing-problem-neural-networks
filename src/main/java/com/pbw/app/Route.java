package com.pbw.app;

/**
 * Created by Krzysiek on 2016-01-28.
 */
public class Route {
    private Integer customerFromId;
    private Integer customerToId;
    private int time;

    public Route(Integer customerFromId, Integer customerToId, int time) {
        this.customerFromId = customerFromId;
        this.customerToId = customerToId;
        this.time = time;
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
}
