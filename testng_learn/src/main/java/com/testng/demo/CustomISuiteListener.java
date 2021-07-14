package com.testng.demo;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class CustomISuiteListener implements ISuiteListener {
    public void onStart(ISuite iSuite) {
        System.out.println("-----------iSuite onStart-------------");
        System.out.println("name " + iSuite.getName());
        System.out.println("AllInvokedMethods " + iSuite.getAllInvokedMethods());
        System.out.println("Results " + iSuite.getResults());
        System.out.println("Host " + iSuite.getHost());
        System.out.println("AllMethods " + iSuite.getAllMethods());
        System.out.println("-----------iSuite onStart-------------");
    }

    public void onFinish(ISuite iSuite) {
        System.out.println("-----------iSuite onFinish-------------");
        System.out.println("Name " + iSuite.getName());
        System.out.println("AllInvokedMethods " + iSuite.getAllInvokedMethods());
        System.out.println("Results " + iSuite.getResults());
        System.out.println("Host " + iSuite.getHost());
        System.out.println("AllMethods " + iSuite.getAllMethods());
        System.out.println("-----------iSuite onFinish-------------");
    }
}
