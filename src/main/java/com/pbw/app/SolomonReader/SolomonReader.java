package com.pbw.app.SolomonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolomonReader {

    private Vehicle vehicles;
    private ArrayList<Vehicle> vehicleList;
    public SolomonReader() {
        // TODO Auto-generated constructor stub
        vehicles=new Vehicle();
        vehicleList =new ArrayList<Vehicle>();
    }

    public Vehicle getVechicles(){
        return vehicles;
    }

    public void setVechicles(Vehicle vechicles){
        this.vehicles = vechicles;
    }


    public SolomonReader(String path) throws FileNotFoundException{
        vehicles=new Vehicle();
        vehicleList =new ArrayList<Vehicle>();
        Scanner scan = null;
        Customer c= new Customer();
        ArrayList<Customer> customers = new ArrayList<Customer>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int k = 0; k < listOfFiles.length; k++) {
            vehicles=new Vehicle();
            customers=new ArrayList<Customer>();
            File file = listOfFiles[k];
            if (file.isFile() && file.getName().endsWith(".txt")) {
                vehicles.setDataFileSource(file.getName());

                File f = new File(file.getAbsolutePath());
                try {
                    scan = new Scanner(f);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found.");
                    System.exit(0);
                }

                int total = 0;
                boolean foundInts = false; //flag to see if there are any integers
                int i=0;
                while (scan.hasNextLine()) { //Note change

                    String currentLine = scan.nextLine();
                    //split into words
                    String words[] = currentLine.split(" ");


                    //For each word in the line
                    for(String str : words) {
                        try {

                            int num = Integer.parseInt(str);
                            total += num;
                            foundInts = true;
                            //create Vehicle
                            if(i==0){ vehicles.setVehicleNumber(num); 	}
                            if(i==1){ vehicles.setCapacity(num); }
                            //class Customer
                            if(i!=0&i!=1)
                            {
                                if(i==2){ c= new Customer(); c.setCustNo(num); }
                                if(i==3){ c.setxCoord(num); }
                                if(i==4){ c.setyCoord(num); }
                                if(i==5){ c.setDemand(num); }
                                if(i==6){ c.setReadyTime(num);}
                                if(i==7){ c.setDueDate(num); }
                                if(i==8){ c.setService(num); i=1; customers.add(c);  }
                            }
                            i++;
                        }catch(NumberFormatException nfe){ }; //word is not an integer, do nothing
                    }
                }//end while

                vehicles.setCustomers(customers);
                vehicleList.add(vehicles);
                // System.out.println(vehicles.getCustomers().get(vehicles.getCustomers().size()-1).getDueDate());

                if(!foundInts)
                    System.out.println("No numbers found.");

                // close the scanner
                scan.close();
            }

        }
    }

    public Vehicle getVehicles() {
        return vehicles;
    }

    public void setVehicles(Vehicle vehicles) {
        this.vehicles = vehicles;
    }

    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public int getVehicleIndexByVehicleNumber(int vehicleNo){
        for(int i = 0; i<this.vehicleList.size(); i++){
            if(this.vehicleList.get(i).getVehicleNumber()==vehicleNo){
                return i;
            }
        }
        return -1;
    }

    public int getVehicleIndexByVehicleCapacity(int vehicleCapacity){
        for(int i = 0; i<this.vehicleList.size(); i++){
            if(this.vehicleList.get(i).getCapacity()==vehicleCapacity){
                return i;
            }
        }
        return -1;
    }


}

