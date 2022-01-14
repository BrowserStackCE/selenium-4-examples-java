package com.features;

import com.utility.SessionStatus;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class GeoLocationTest {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String URL = "http://hub-cloud.browserstack.com/wd/hub";
    private final SessionStatus status = new SessionStatus();
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult tr) throws MalformedURLException {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/capabilities.json"));
        Map<String, Object> browserstackOptions = new HashMap<>(jsonPath.getMap("browserstackOptions"));
        browserstackOptions.put("userName", USERNAME);
        browserstackOptions.put("accessKey", ACCESS_KEY);
        browserstackOptions.put("sessionName", tr.getMethod().getDescription());

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", jsonPath.getMap("geolocationOptions"));

        DesiredCapabilities capabilities = new DesiredCapabilities(jsonPath.getMap("capabilities"));
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

        assertEquals(driver.findElement(By.id("address")).getText(),
                "Goregaon East, Mumbai, 400063, Maharashtra, India", "Incorrect address");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult tr) {
        status.markTestSessionStatus(driver, tr);
        driver.quit();
    }

}
