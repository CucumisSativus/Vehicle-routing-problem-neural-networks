package com.pbw.ui;

import com.pbw.app.Customer;
import com.pbw.app.NieMaCustomeraWChujSrogiException;
import com.pbw.app.Route;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.*;
import java.util.List;

/**
 * Created by Krzysiek on 2016-01-28.
 */
public class MapPanel extends JPanel implements MouseWheelListener {

    private final int CUSTOMER_SIZE = 40;
    private final int REFERENCE_WIDTH = 640;
    private final int REFERENCE_HEIGHT = 480;

    private final Map<Integer, List<Route>> seed;
    private List<Customer> customers;
    private Integer pointInTime;
    private Double scale;
    private Integer offsetX;
    private Integer offsetY;

    public MapPanel(Map<Integer, List<Route>> seed) {
        this(seed, new ArrayList<Customer>());
    }

    public MapPanel(Map<Integer, List<Route>> seed, List<Customer> customers) {
        this.customers = customers;
        this.offsetX = 0;
        this.offsetY = 0;
        this.pointInTime = 0;
        this.scale = 1.0;
        this.seed = seed;

        this.addMouseWheelListener(this);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(REFERENCE_WIDTH, REFERENCE_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            Iterator mapEntriesIterator = this.seed.entrySet().iterator();
            Integer groupIndex = 0;
            Integer groupsCount = this.seed.size();

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
        Integer customerFromId = route.getCustomerFromId();
        Integer customerToId = route.getCustomerToId();
        Customer customerFrom = Customer.findByNo(route.getCustomerFromId(), customers);
        Customer customerTo = Customer.findByNo(route.getCustomerToId(), customers);
        Color groupColor = null;

        groupColor = Color.getHSBColor(groupIndex * 1.0f / groupsCount, 1.0f, 1.0f);

        paintCustomer(groupColor, customerFrom, graphics, pointInTime);
        paintCustomer(groupColor, customerTo, graphics, pointInTime);

        paintRoute(groupColor, customerFrom, customerTo, graphics);
    }

    private void paintRoute(Color groupColor, Customer customerFrom, Customer customerTo, Graphics graphics) {
        int halvedCustomerSize = CUSTOMER_SIZE / 2;
        int scaledFromX = (int) Math.round((customerFrom.getxCoord() - offsetX) * scale);
        int scaledFromY = (int) Math.round((customerFrom.getyCoord() - offsetY) * scale);
        int scaledToX = (int) Math.round((customerTo.getxCoord() - offsetX) * scale);
        int scaledToY = (int) Math.round((customerTo.getyCoord() - offsetY) * scale);

        graphics.setColor(groupColor);
        graphics.drawLine(
                scaledFromX + halvedCustomerSize,
                scaledFromY + halvedCustomerSize,
                scaledToX + halvedCustomerSize,
                scaledToY + halvedCustomerSize
        );
    }

    private void paintCustomer(Color groupColor, Customer customer, Graphics graphics, Integer pointInTime) {
        int scaledX = (int) Math.round((customer.getxCoord() - offsetX) * scale);
        int scaledY = (int) Math.round((customer.getyCoord() - offsetY) * scale);

        if (customer.isAvailableAtTime(pointInTime)) {
            graphics.setColor(groupColor);
        } else {
            graphics.setColor(groupColor.darker());
        }

        graphics.fillArc(
                scaledX,
                scaledY,
                CUSTOMER_SIZE,
                CUSTOMER_SIZE,
                0,
                360
        );
        graphics.setColor(Color.white);
        graphics.drawString(
                Integer.toString(customer.getCustNo()),
                scaledX + (CUSTOMER_SIZE / 2),
                scaledY + (CUSTOMER_SIZE / 2) - 1
        );

    }

    public void setCustomers(List<Customer> customers) {
        int[] offsets = this.recalculateOffsets(customers);

        this.customers = customers;
        this.offsetX = offsets[0];
        this.offsetY = offsets[1];
        this.scale = this.recalculateScale(customers, this.offsetX, this.offsetY);
    }

    private int[] recalculateOffsets(List<Customer> customers) {
        int[] result = new int[2];
        int minX = REFERENCE_WIDTH;
        int minY = REFERENCE_HEIGHT;

        for (Customer singleCustomer :
                customers) {
            if (minX > singleCustomer.getxCoord()) {
                minX = singleCustomer.getxCoord();
            }

            if (minY > singleCustomer.getyCoord()) {
                minY = singleCustomer.getyCoord();
            }
        }

        result[0] = minX;
        result[1] = minY;

        return result;
    }

    private Double recalculateScale(List<Customer> customers, int offsetX, int offsetY) {
        int greatestX = 0;
        int greatestY = 0;
        Double scaleX;
        Double scaleY;

        for (Customer singleCustomer :
                customers) {
            if (singleCustomer.getxCoord() > greatestX) {
                greatestX = singleCustomer.getxCoord();
            }
            if (singleCustomer.getyCoord() > greatestY) {
                greatestY = singleCustomer.getyCoord();
            }
        }

        scaleX = 1.0 * REFERENCE_WIDTH / (greatestX - offsetX + CUSTOMER_SIZE);
        scaleY = 1.0 * REFERENCE_HEIGHT / (greatestY - offsetY + CUSTOMER_SIZE);

        return  scaleX < scaleY ? scaleX : scaleY;
    }

    public void updatePointInTime(Integer pointInTime) {
        this.pointInTime = pointInTime;
        this.repaint();
    }


    public void mouseWheelMoved(MouseWheelEvent e) {
        this.scale += e.getWheelRotation();

        if(this.scale < 0) {
            this.scale = 0.0;
        }

        this.repaint();
    }
}
