package com.features;

import com.utility.SessionStatus;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String URL = "https://hub-cloud.browserstack.com/wd/hub";

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private final SessionStatus status = new SessionStatus();

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult tr) throws MalformedURLException {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/capabilities.json"));
        Map<String, Object> browserstackOptions = new HashMap<>(jsonPath.getMap("browserstackOptions"));
        browserstackOptions.put("userName", USERNAME);
        browserstackOptions.put("accessKey", ACCESS_KEY);
        browserstackOptions.put("sessionName", tr.getMethod().getDescription());

        DesiredCapabilities capabilities = new DesiredCapabilities(jsonPath.getMap("capabilities"));
        capabilities.setCapability("bstack:options", browserstackOptions);

        driverThread.set(new RemoteWebDriver(new URL(URL), capabilities));
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult tr) {
        status.markTestSessionStatus(driverThread.get(), tr);
        driverThread.get().quit();
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
