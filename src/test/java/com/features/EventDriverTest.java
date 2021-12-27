package com.features;

import org.openqa.selenium.*;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v96.emulation.Emulation;
import org.openqa.selenium.devtools.v96.log.Log;
import org.openqa.selenium.devtools.v96.network.Network;
import org.openqa.selenium.devtools.v96.performance.Performance;
import org.openqa.selenium.devtools.v96.performance.model.Metric;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Optional.empty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EventDriverTest extends BaseTest {

    @Test(description = "Console log events")
    public void consoleLogEvents() {
        AtomicBoolean success = new AtomicBoolean(false);

        WebDriver driver = new Augmenter().augment(getDriver());
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(),
                logEntry -> {
                    success.set(true);
                    System.out.println("text: " + logEntry.getText());
                    System.out.println("level: " + logEntry.getLevel());
                });
        driver.get("https://bstackdemo.com");
        super.sleep(10);
        assertTrue(success.get(), "Unable to fetch console logs");
    }

    @Test(description = "JavaScript exception")
    public void javaScriptException() {
        AtomicBoolean success = new AtomicBoolean(false);

        WebDriver driver = new Augmenter().augment(getDriver());
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        List<JavascriptException> jsExceptionsList = new ArrayList<>();
        devTools.getDomains().events().addJavascriptExceptionListener(jsExceptionsList::add);
        driver.get("https://facebook.com");
        WebElement login = driver.findElement(By.name("login"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                login, "onclick", "throw new Error('BrowserStack provides Selenium 4 support');");
        login.click();
        jsExceptionsList.forEach(jsException -> {
            System.out.println("JS exception message: " + jsException.getMessage());
            System.out.println("JS exception system information: " + jsException.getSystemInformation());
            jsException.printStackTrace();
            success.set(true);
        });
        assertTrue(success.get(), "Unable to fetch JavaScript Exceptions");
    }

    @Test(description = "Intercept network")
    public void interceptNetwork() {
        WebDriver driver = new Augmenter().augment(getDriver());
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        NetworkInterceptor interceptor = new NetworkInterceptor(
                driver,
                Route.matching(req -> true)
                        .to(() -> req -> new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", StandardCharsets.UTF_8.toString())
                                .setContent(() -> new ByteArrayInputStream("Creamy, delicious cheese!".getBytes(StandardCharsets.UTF_8)))));
        driver.get("https://example-sausages-site.com");
        String source = driver.getPageSource();
        System.out.println(source);
        assertTrue(source.contains("Creamy, delicious cheese!"), "Unable to mock network response");
        interceptor.close();
    }

    @Test(description = "Capture performance metrics")
    public void capturePerformanceMetrics() {
        AtomicBoolean success = new AtomicBoolean(false);

        WebDriver driver = new Augmenter().augment(getDriver());
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Performance.enable(empty()));
        List<Metric> metricList = devTools.send(Performance.getMetrics());
        driver.get("https://browserstack.com");
        metricList.forEach(metric -> {
            System.out.println(metric.getName() + " = " + metric.getValue());
            success.set(true);
        });
        assertTrue(success.get(), "Unable to capture Performance Metrics");
    }

    @Test(description = "Enable basic auth")
    public void enableBasicAuth() {
        WebDriver driver = new Augmenter().augment(getDriver());
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        Augmenter augmenter = new Augmenter();
        driver = augmenter.
                addDriverAugmentation("chrome", HasAuthentication.class,
                        (caps, exec) -> (whenThisMatches, useTheseCredentials) -> devTools.getDomains()
                                .network().addAuthHandler(whenThisMatches, useTheseCredentials))
                .augment(driver);
        ((HasAuthentication) driver).register(UsernameAndPassword.of("foo", "bar"));
        driver.get("http://httpbin.org/basic-auth/foo/bar");
        String text = driver.findElement(By.tagName("body")).getText();
        System.out.println(text);
        assertTrue(text.contains("authenticated"), "Unable to log in");
    }

    @Test(description = "Emulate network conditions")
    public void emulateNetworkConditions() {
        WebDriver driver = new Augmenter().augment(getDriver());
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Network.emulateNetworkConditions(false, 0, 64 * 1024, 64 * 1024, empty()));
        driver.get("https://fast.com");
        super.sleep(15);
        String speed = driver.findElement(By.id("speed-value")).getText();
        String units = driver.findElement(By.id("speed-units")).getText();
        System.out.println(speed);
        System.out.println(units);
        assertTrue(Double.parseDouble(speed) < 512.0 && units.equals("Kbps"), "Unable to restrict network speeds");
    }

    @Test(description = "Set device dimensions")
    public void setDeviceDimensions() {
        WebDriver driver = new Augmenter().augment(getDriver());
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Emulation.setDeviceMetricsOverride(375, 812, 50, true,
                empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty()));
        driver.get("http://whatismyscreenresolution.net/");
        String resolution = driver.findElement(By.id("resolution")).getText();
        System.out.println(resolution);
        assertEquals(resolution, "375x812", "Unable to set device dimentions");
    }

}
