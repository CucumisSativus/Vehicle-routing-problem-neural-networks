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
        out.write("Average number of customers\t" + solution.getAverageCustomerOnARoad() + crlf);
        out.write("Average load\t" + solution.getAverageLoad() + crlf);
        out.write("Number of routes\t" + solution.getTotalRoutes() + crlf);
        out.write("Total distance\t" + solution.getTotalDistance() + crlf);

        while (routesByCarIterator.hasNext()) {
            List<Route> routesBySingleCar = (List<Route>) routesByCarIterator.next();
            int totalLength = 0;
            int customersOnRoute = (routesBySingleCar.size() - 1);

            customersCount += customersOnRoute;

            out.write(crlf + "Route #" + routeIndex++ + crlf);

            for (Route singleRoute :
                    routesBySingleCar) {
                totalLength += singleRoute.getDistance();
                out.write(singleRoute + crlf);
            }

            out.write(crlf + "Number of customers on the route\t" + customersOnRoute + crlf);
            out.write("Length of the route\t" + totalLength + crlf);
        }


        out.close();
    }

    private Solution solution;
    private int solutionNumber;
    private String solutionsPath;
}
