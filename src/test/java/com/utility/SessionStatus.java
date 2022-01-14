package com.utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

public class SessionStatus {

    public void markTestSessionStatus(WebDriver driver, ITestResult tr) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        if (tr.isSuccess()) {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        } else {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"" + tr.getThrowable().getClass().getSimpleName() + "\"}}");
        }
    }

}
