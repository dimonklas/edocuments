package utils;

import lombok.extern.log4j.Log4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

@Log4j
public final class AllureOnFailListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("Start test: " + iTestResult.getName());

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("Test: " + iTestResult.getName() + " successfully ended");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            new DriverUtil().screenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Test " + iTestResult.getName() + " ended failed!");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
