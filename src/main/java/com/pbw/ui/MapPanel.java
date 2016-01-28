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

    private final Map<Integer, List<Route>> seed;
    private List<Customer> customers;

    public MapPanel(Map<Integer, List<Route>> seed) {
        this(seed, new ArrayList<Customer>());
    }

    public MapPanel(Map<Integer, List<Route>> seed, List<Customer> customers) {
        this.customers = customers;
        this.seed = seed;

        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(250, 200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            for (Customer singleCustomer : customers) {
                paintCustomer(singleCustomer, g);
            }

            Iterator mapEntriesIterator = this.seed.entrySet().iterator();
            while (mapEntriesIterator.hasNext()) {
                Map.Entry<java.lang.Integer, List<Route>> pair = (Map.Entry) mapEntriesIterator.next();
                List<Route> routes = pair.getValue();

                for (Route singleRoute : routes) {
                    paintRoute(singleRoute, customers, g);
                }

                mapEntriesIterator.remove();
            }
        } catch (NieMaCustomeraWChujSrogiException e) {
            // deal with it
        }
    }

    private void paintRoute(Route route, List<Customer> customers, Graphics graphics) throws NieMaCustomeraWChujSrogiException {
        Customer customerFrom = null;
        Customer customerTo = null;
        Integer customerFromId = route.getCustomerFromId();
        Integer customerToId = route.getCustomerToId();

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
        graphics.drawLine(customerFrom.getxCoord(), customerFrom.getyCoord(), customerTo.getxCoord(), customerTo.getyCoord());
    }

    private void paintCustomer(Customer customer, Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillArc(customer.getxCoord(), customer.getyCoord(),
                10, 10, 0, 360);
        graphics.setColor(Color.white);
        graphics.drawString(java.lang.Integer.toString(customer.getCustNo()), customer.getxCoord() + 1, customer.getyCoord() - 5);
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
