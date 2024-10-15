package com.w2a.listeners;

import com.w2a.base.BasePage;
import com.w2a.errorController.AssertLib;
import org.testng.*;
import org.testng.internal.Utils;

import java.util.List;

public class TestListenerAdapter extends BasePage implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult result) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        Reporter.setCurrentTestResult(result);

        if (method.isTestMethod()) {
            List<Throwable> verification = AssertLib.getVerificationFailures();
            if (verification.size() > 0) {
                // Only set status to FAILURE if there are verification failures
                if (result.getStatus() != ITestResult.SKIP) {
                    result.setStatus(ITestResult.FAILURE);
                }

                if (result.getThrowable() != null) {
                    verification.add(result.getThrowable());
                }

                int size = verification.size();

                if (size == 1) {
                    result.setThrowable(verification.get(0));
                } else {
                    StringBuffer failureMessage =
                            new StringBuffer("Multiple failures (").append(size).append("):\n\n");
                    for (int i = 1; i < size; i++) {
                        failureMessage.append("Failure ").append(i).append(" of ").append(size).append(":\n");
                        Throwable t = verification.get(i - 1);
                        String fullStackTrace = Utils.longStackTrace(t, false);
                        failureMessage.append(fullStackTrace).append("\n\n");
                    }

                    Throwable last = verification.get(size - 1);
                    failureMessage.append("Failure ").append(size).append(" of ").append(size).append(":\n");
                    failureMessage.append(last.toString());

                    // Set merged throwable
                    Throwable merged = new Throwable(failureMessage.toString());
                    merged.setStackTrace(last.getStackTrace());

                    result.setThrowable(merged);
                }
            }
        }
    }
}
