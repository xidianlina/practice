package com.testng.demo;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({IReporterListener.class})
public class ReporterTest {
    @DataProvider
    public Object[][] dataProvider() {
        return new Object[][]{{1}, {2}};
    }

    @Test(dataProvider = "dataProvider")
    public void testAssert1(int a) {
        Assert.assertEquals(1, a);
    }

    @Test
    public void testAssert2() {
        Assert.assertEquals("2", "2");
    }
}
