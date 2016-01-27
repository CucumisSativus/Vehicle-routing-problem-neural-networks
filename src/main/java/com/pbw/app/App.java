package com.pbw.app;

import com.pbw.app.SolomonReader.*;

import java.io.FileNotFoundException;

public class App
{
    public static void main( String[] args ) throws FileNotFoundException {
        ProblemReader reader = new ProblemReader(new SolomonReader(args[0]));
    }
}
