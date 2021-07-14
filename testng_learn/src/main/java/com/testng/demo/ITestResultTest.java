package com.testng.demo;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ITestResultTest {
    //Demo1会运行失败
    @Test
    public void Demo1() {

        System.out.println("This is Demo1 Method");

        // Assert.assertFalse(false);
        assertEquals(false, true);
    }

    //Demo2会运行成功
    @Test
    public void Demo2() {

        System.out.println("This is Demo2 Method");

        assertEquals(true, true);
    }

    @AfterMethod
    public void ITestResult_Demo(ITestResult result) {

        System.out.println("正在运行的方法是：" + result.getName());
        System.out.println(result.getInstanceName());
    }

}
