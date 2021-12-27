package com.features;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v94.emulation.Emulation;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GeoLocationTest {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String URL = "http://hub-cloud.browserstack.com/wd/hub";
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult tr) throws MalformedURLException {
        Map<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("userName", USERNAME);
        browserstackOptions.put("accessKey", ACCESS_KEY);
        browserstackOptions.put("projectName", "BrowserStack");
        browserstackOptions.put("buildName", "Selenium-4 Features");
        browserstackOptions.put("sessionName", tr.getMethod().getDescription());
        browserstackOptions.put("os", "Windows");
        browserstackOptions.put("osVersion", "10");
        browserstackOptions.put("debug", true);
        browserstackOptions.put("seleniumVersion", "4.0.0");
        browserstackOptions.put("seleniumCdp", true);

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("googlegeolocationaccess.enabled", true);
        prefs.put("profile.default_content_setting_values.geolocation", 1); // 1:allow 2:block
//        prefs.put("profile.default_content_setting_values.notifications", 1);
//        prefs.put("profile.managed_default_content_settings", 1);
        options.setExperimentalOption("prefs", prefs);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "latest");
        capabilities.setCapability("bstack:options", browserstackOptions);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new RemoteWebDriver(new URL(URL), capabilities);
    }

    @Test(description = "Emulate geo-location")
    public void emulateGeolocation() {
        WebDriver driver = new Augmenter().augment(this.driver);
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Emulation.setGeolocationOverride(Optional.of(19.1700841),
                Optional.of(72.8579897),
                Optional.of(1)));
        driver.get("https://my-location.org");

        String address = driver.findElement(By.id("address")).getText();
        System.out.println(address);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult tr) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        if (tr.isSuccess()) {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        } else {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"" + tr.getThrowable().getClass().getSimpleName() + "\"}}");
        }
        driver.quit();
    }


}
