package com.pbw.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by michal on 31.01.2016.
 */
public class SolutionSaver {

    private final String crlf = "\r\n";

    public SolutionSaver(Solution solution, int solutionNumber, String solutionsPath) {
        this.solution = solution;
        this.solutionNumber = solutionNumber;
        this.solutionsPath = solutionsPath;
    }

    public void saveToFile() throws IOException {
        File solutionPath = new File(solutionsPath + File.separator + solution.getProblemName() + "_" + solutionNumber + ".txt");
        FileWriter out = new FileWriter(solutionPath.getAbsolutePath());
        Map<Integer, List<Route>> routesByCar = this.solution.getRoutesByCar();
        Iterator routesByCarIterator = routesByCar.values().iterator();
        Integer routeIndex = 1;
        Integer customersCount = 0;

        solutionPath.getParentFile().mkdirs();

        out.write("SOLUTION" + crlf);
        out.write("Average number of customers\t\t\t" + solution.getAverageCustomerOnARoad() + crlf);
        out.write("Average load\t\t\t" + solution.getAverageLoad() + crlf);
        out.write("Number of routes\t\t\t" + solution.getTotalRoutes() + crlf);
        out.write("Number of paths\t\t\t" + routesByCar.size() + crlf);
        out.write("Total distance\t\t\t" + solution.getTotalDistance() + crlf + crlf);

        out.write("Path #\tPath\tCustomers\tLength" + crlf);

        while (routesByCarIterator.hasNext()) {
            List<Route> routesBySingleCar = (List<Route>) routesByCarIterator.next();
            double totalLength = 0;
            int customersOnRoute = (routesBySingleCar.size() - 1);

            customersCount += customersOnRoute;

            out.write(routeIndex++ + "\tbase");

            for (Route singleRoute :
                    routesBySingleCar) {
                int customerToId = singleRoute.getCustomerToId();

                totalLength += singleRoute.getDistance();
                out.write(" > " + (customerToId > 0 ? customerToId : "base"));
            }

            out.write("\t" + customersOnRoute);
            out.write("\t" + totalLength + crlf);
        }


        out.close();
    }

    private Solution solution;
    private int solutionNumber;
    private String solutionsPath;
}
