package com.w2a.listeners;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.Page;
import com.w2a.utilities.TestUtil;
import org.testng.*;

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
        System.out.println("Test Case started...................................................................");
        test = rep.startTest(result.getName());

        // Check if the test should run
        String name = result.getName().toLowerCase();
        if (!TestUtil.isTestRunnable(name, getExcel())) {
            test.log(LogStatus.INFO, "Skipping the test case since it is disabled.");
            result.setStatus(ITestResult.SKIP);
            System.out.println("Skipping the test case...................................................................");
            throw new SkipException("Skipping the test case as it is disabled in the configuration.");
        } else {

            configureDriver(); // Ensure driver is configured only if the test will run
        }
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
