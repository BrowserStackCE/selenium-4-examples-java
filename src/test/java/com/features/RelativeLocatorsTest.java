package com.features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RelativeLocatorsTest extends BaseTest {

    @Test(description = "Relative locators")
    public void relativeLocators() {
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.get("https://automationbookstore.dev/");
        String id = driver.findElement(
                RelativeLocator.with(By.tagName("li"))
                        .toLeftOf(By.id("pid6"))
                        .below(By.id("pid1")))
                .getAttribute("id");
        assertEquals(id, "pid5", "Incorrect element");
    }

}
