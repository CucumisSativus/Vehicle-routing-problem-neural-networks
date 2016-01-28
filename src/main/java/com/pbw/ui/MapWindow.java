package com.pbw.ui;

import com.pbw.app.Customer;
import com.pbw.app.Route;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Krzysiek on 2016-01-28.
 */
public class MapWindow extends JFrame {

    private Map<java.lang.Integer, List<Route>> seed;
    private List<Customer> customers;
    private MapPanel map;

    public MapWindow() {
        this(new HashMap<java.lang.Integer, List<Route>>());
    }

    public MapWindow(Map<java.lang.Integer, List<Route>> seed) {
        super("Map");

        this.customers = new ArrayList<Customer>();
        this.seed = seed;
        this.map = new MapPanel(this.seed);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(map);
        this.pack();
        this.setVisible(true);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        this.map.setCustomers(customers);
    }

}