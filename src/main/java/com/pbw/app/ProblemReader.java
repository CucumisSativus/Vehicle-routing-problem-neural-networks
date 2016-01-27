package com.pbw.app;

import com.pbw.app.SolomonReader.SolomonReader;

import java.util.ArrayList;

public class ProblemReader {
    private SolomonReader reader;
    private ArrayList<Problem> problems;

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public ProblemReader(SolomonReader reader) {
        this.reader = reader;
        this.problems = new ArrayList<Problem>();
        prepareProblems();
    }

    private void prepareProblems(){
        for(int p=0; p< reader.getVehicleList().size(); ++p){
            com.pbw.app.SolomonReader.Vehicle readProblem =  reader.getVehicleList().get(p);
            int capacity = readProblem.getCapacity();
            int vehicleCount = readProblem.getVehicleNumber();
            ArrayList<Customer> customers = new ArrayList<Customer>();

            for(int c = 0; c < readProblem.getCustomers().size(); ++c){
                com.pbw.app.SolomonReader.Customer customer = readProblem.getCustomers().get(c);

                customers.add(
                        new Customer(
                                customer.getCustNo(),
                                customer.getxCoord(),
                                customer.getyCoord(),
                                customer.getDemand(),
                                customer.getReadyTime(),
                                customer.getDueDate(),
                                customer.getService()
                        )
                );
            }

            problems.add(
                    new Problem(readProblem.getDataFileSource(), customers, prepareVehicles(vehicleCount, capacity))
            );
        }
    }

    private ArrayList<Vehicle> prepareVehicles(int count, int capacity){
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        for(int i=0; i< count; ++i){
            vehicles.add(
                    new Vehicle(i, capacity)
            );
        }
        return vehicles;
    }
}
