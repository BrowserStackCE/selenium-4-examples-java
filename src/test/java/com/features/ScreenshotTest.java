package com.features;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ScreenshotTest extends BaseTest {

    @Test(description = "Specific element screenshot")
    public void specificElementScreenshot() throws IOException {
        WebDriver driver = getDriver();
        driver.get("https://bstackdemo.com/");
        WebElement device = driver.findElement(By.cssSelector("img[alt='iPhone 12']"));
        File screenshot = device.getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), Paths.get("target/device.png"), StandardCopyOption.REPLACE_EXISTING);
    }

}
