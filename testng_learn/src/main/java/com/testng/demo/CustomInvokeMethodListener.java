package com.testng.demo;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class CustomInvokeMethodListener implements IInvokedMethodListener {
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        //在测试类中的每一个方法运行之前跑
        System.out.println("beforeInvocation"
                + iTestResult.getTestClass().getName() + "--->" + iInvokedMethod.getTestMethod().getMethodName());
    }

    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
//在测试类中的每一个方法运行之后跑
        System.out.println("afterInvocation"
                + iTestResult.getTestClass().getName() + "--->" + iInvokedMethod.getTestMethod().getMethodName());
    }
}
