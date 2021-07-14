package com.testng.demo;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataProviderTest {

    @DataProvider(name = "provideNumbers")
    public Object[][] providerData() {
        return new Object[][]{{10, 20}, {100, 110}, {200, 210}};
    }

    @Test(dataProvider = "provideNumbers")
    public void TestNgLearn(int param1, int param2) {
        System.out.println("this is TestNG test case1, and param1 is:" + param1 + "; param2 is:" + param2);
        Assert.assertFalse(false);
    }
}
