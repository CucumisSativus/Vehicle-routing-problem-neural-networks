package com.pbw.app;

/**
 * Created by Krzysiek on 2016-01-28.
 */
public class NieMaCustomeraWChujSrogiException extends Exception {
    public NieMaCustomeraWChujSrogiException(int missingCustomerId) {
        super("No customer with id " + missingCustomerId);
    }
}
