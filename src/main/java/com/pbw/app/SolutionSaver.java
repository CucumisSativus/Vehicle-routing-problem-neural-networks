package com.pbw.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by michal on 31.01.2016.
 */
public class SolutionSaver {

    public SolutionSaver(Solution solution, int solutionNumber, String solutionsPath) {
        this.solution = solution;
        this.solutionNumber = solutionNumber;
        this.solutionsPath = solutionsPath;
    }

    public void saveToFile() throws IOException {
        File solutionPath = new File(solutionsPath + File.separator + solution.getProblemName() + "_" + solutionNumber);
        solutionPath.getParentFile().mkdirs();

        FileWriter out = new FileWriter(solutionPath.getAbsolutePath());
        out.write("Total distance" + solution.getTotalDistance());
    }
    private Solution solution;
    private int solutionNumber;
    private String solutionsPath;
}
