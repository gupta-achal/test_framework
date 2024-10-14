package com.w2a.listeners;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.annotation.Environment;
import com.w2a.base.Page;
import com.w2a.utilities.TestUtil;
import org.testng.*;

import java.lang.reflect.Method;

public class CustomListener extends Page implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        System.out.println("Suite Started ************************************************************************");
    }

    @Override
    public void onFinish(ISuite suite) {
        // Flush the report here once at the end of the suite
        rep.flush();
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test suite finished: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        start();
        System.out.println("Test Case started...................................................................");
        test = rep.startTest(result.getName());

        String name = result.getName().toLowerCase();
        Method method = result.getMethod().getConstructorOrMethod().getMethod();

        String environment = System.getenv("environment") != null && !System.getenv("environment").isEmpty() ?
                System.getenv("environment") : config.getProperty("environment");

        if (method.isAnnotationPresent(Environment.class)) {
            Environment env = method.getAnnotation(Environment.class);
            if (!environment.equals(env.value().toString())) {
                System.out.println("Skipping the test due to environment.......");
                test.log(LogStatus.SKIP, "Environment not supported");
                result.setStatus(ITestResult.SKIP);
                throw new SkipException(" .................... ");
            }
        }

        if (!TestUtil.isTestRunnable(name, getExcel())) {
            test.log(LogStatus.SKIP, "Skipping the test case since it is disabled.");
            result.setStatus(ITestResult.SKIP);
            System.out.println("Test Case name: " + name);
            System.out.println("Skipping the test case..........................................................");

            throw new SkipException("................................................................");
        }

        configureDriver();
     }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
        rep.endTest(test);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            TestUtil.captureScreen();
            test.log(LogStatus.FAIL, result.getName().toUpperCase() + " Failed. Exception: " + result.getThrowable());
            test.log(LogStatus.FAIL, test.addScreencast(TestUtil.scrName));
            result.setStatus(ITestResult.FAILURE);

            Reporter.log("Click to see Screenshot");
            Reporter.log("<a target=\"_blank\" href=" + TestUtil.scrName + ">Screenshot</a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            Reporter.log("<a target=\"_blank\" href=" + TestUtil.scrName + "><img src=" + TestUtil.scrName + " height=200 width=200></img></a>");

        } catch (Exception ex) {
            test.log(LogStatus.ERROR, "Error capturing screenshot: " + ex.getMessage());
        } finally {
            rep.endTest(test);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(LogStatus.SKIP, result.getName().toUpperCase() + " Skipped as the Run mode is NO");
        rep.endTest(test);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test failed but within success percentage: " + result.getName());
    }
}
