package com.testng.demo;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

public class NewTest {
    @Test(expectedExceptions = ArithmeticException.class)
    public void divisionWithException() {
        int i = 1 / 0;
        System.out.println("After division the value of i is :"+ i);
    }

    @Test
    public void f() {
        System.out.println("this is new test");
        Assert.assertTrue(true);
    }

    @Test(groups = "group1")
    public void test1() {
        System.out.println("test1 from group1");
        Assert.assertTrue(true);
    }

    @Test(groups = "group1")
    public void test11() {
        System.out.println("test11 from group1");
        Assert.assertTrue(true);
    }

    @Test(groups = "group2")
    public void test2() {
        System.out.println("test2 from group2");
        Assert.assertTrue(true);
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("beforeTest");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("afterTest");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("afterClass");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("beforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("afterSuite");
    }

    //只对group1有效，即test1和test11
    @BeforeGroups(groups = "group1")
    public void beforeGroups() {
        System.out.println("beforeGroups");
    }

    //只对group1有效，即test1和test11
    @AfterGroups(groups = "group1")
    public void afterGroups() {
        System.out.println("afterGroups");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("beforeMethod");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("afterMethod");
    }
}
