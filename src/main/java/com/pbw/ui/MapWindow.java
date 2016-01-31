package com.pbw.ui;

import com.pbw.app.Customer;
import com.pbw.app.Route;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Krzysiek on 2016-01-28.
 */
public class MapWindow extends JFrame implements ChangeListener {

    private Map<java.lang.Integer, List<Route>> seed;
    private List<Customer> customers;
    private MapPanel map;
    private MapSlider slider;
    private Integer pointInTime;

    public MapWindow() {
        this(new HashMap<java.lang.Integer, List<Route>>());
    }

    public MapWindow(Map<java.lang.Integer, List<Route>> seed) {
        super("Map");

        this.customers = new ArrayList<Customer>();
        this.seed = seed;
        this.map = new MapPanel(this.seed);
        this.slider = new MapSlider();
        this.pointInTime = 0;

        this.slider.addChangeListener(this);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.add(map);
        this.add(slider);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        this.map.setCustomers(customers);

        this.map.repaint();
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        this.pointInTime = (int)source.getValue();
        this.map.updatePointInTime(pointInTime);


    }
}
