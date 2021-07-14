package com.testng.demo;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomInvokeMethodListener.class)
public class InvokeMethodListenerTest {
    @BeforeClass
    public void setUp() {
        System.out.println("TestNGTestListener1类中的setUp方法");
    }

    @AfterClass
    public void tearDown() {
        System.out.println("TestNGTestListener1类中的tearDown方法");
    }

    @Test
    public void testMethod1() {
        System.out.println("TestNGTestListener1类中的testMethod1方法");
        Assert.assertTrue(true);
    }

    @Test
    public void testMethod2() {
        System.out.println("TestNGTestListener1类中的testMethod2方法");
        Assert.assertTrue(false);
    }
}
