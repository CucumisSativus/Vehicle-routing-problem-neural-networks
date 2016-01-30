package com.pbw.ui;

import com.pbw.app.Customer;
import com.pbw.app.Route;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Krzysiek on 2016-01-28.
 */
public class MapPanel extends JPanel {

    private final int CUSTOMER_SIZE = 40;

    private final Map<Integer, List<Route>> seed;
    private List<Customer> customers;
    private Integer pointInTime;

    public MapPanel(Map<Integer, List<Route>> seed) {
        this(seed, new ArrayList<Customer>());
    }

    public MapPanel(Map<Integer, List<Route>> seed, List<Customer> customers) {
        this.customers = customers;
        this.seed = seed;
        this.pointInTime = 0;

        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            Iterator mapEntriesIterator = this.seed.entrySet().iterator();
            Integer groupIndex = 0;
            Integer groupsCount  = this.seed.size();

            while (mapEntriesIterator.hasNext()) {
                Map.Entry<java.lang.Integer, List<Route>> pair = (Map.Entry) mapEntriesIterator.next();
                List<Route> routes = pair.getValue();

                for (Route singleRoute : routes) {
                    paintRouteWithCustomers(groupIndex, groupsCount, singleRoute, customers, g);
                }

                groupIndex++;
            }
        } catch (NieMaCustomeraWChujSrogiException e) {
            // deal with it
        }
    }

    private void paintRouteWithCustomers(Integer groupIndex, Integer groupsCount, Route route, List<Customer> customers, Graphics graphics) throws NieMaCustomeraWChujSrogiException {
        Customer customerFrom = null;
        Customer customerTo = null;
        Integer customerFromId = route.getCustomerFromId();
        Integer customerToId = route.getCustomerToId();
        Color groupColor = null;

        for (Customer singleCustomer : customers) {
            Integer singleCustomerId = singleCustomer.getCustNo();

            if (customerFrom == null && singleCustomerId == customerFromId) {
                customerFrom = singleCustomer;
            }
            if (customerTo == null && singleCustomerId == customerToId) {
                customerTo = singleCustomer;
            }
            if (customerFrom != null && customerTo != null) {
                break;
            }
        }

        if (customerFrom == null || customerTo == null) {
            throw new NieMaCustomeraWChujSrogiException(customerFrom == null ? customerFromId : customerToId);
        }

        groupColor = Color.getHSBColor(groupIndex*1.0f/groupsCount, 1.0f, 1.0f);

        paintCustomer(groupColor, customerFrom, graphics, pointInTime);
        paintCustomer(groupColor, customerTo, graphics, pointInTime);

        paintRoute(groupColor, customerFrom, customerTo,graphics);
    }

    private void paintRoute(Color groupColor, Customer customerFrom, Customer customerTo, Graphics graphics) {
        int halvedCustomerSize = CUSTOMER_SIZE/2;
        graphics.setColor(groupColor);
        graphics.drawLine(customerFrom.getxCoord() + halvedCustomerSize, customerFrom.getyCoord() + halvedCustomerSize, customerTo.getxCoord() + halvedCustomerSize, customerTo.getyCoord() + halvedCustomerSize);
    }

    private void paintCustomer(Color groupColor, Customer customer, Graphics graphics, Integer pointInTime) {
        if(customer.isAvailableAtTime(pointInTime)) {
            graphics.setColor(groupColor);
        }
        else {
            graphics.setColor(groupColor.darker());
        }

        graphics.fillArc(customer.getxCoord(), customer.getyCoord(), CUSTOMER_SIZE, CUSTOMER_SIZE, 0, 360);
        graphics.setColor(Color.white);
        graphics.drawString(Integer.toString(customer.getCustNo()), customer.getxCoord() + (CUSTOMER_SIZE/2), customer.getyCoord() + (CUSTOMER_SIZE/2) - 1);

    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void updatePointInTime(Integer pointInTime) {
        this.pointInTime = pointInTime;
        this.repaint();
    }
}
