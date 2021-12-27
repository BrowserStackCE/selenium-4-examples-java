package com.features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class MouseActionsTest extends BaseTest {

    @Test(description = "Click element")
    public void clickElement() {
        WebDriver driver = getDriver();
        driver.get("https://mousetester.com/");
        Actions actions = new Actions(driver);
        WebElement clickMe = driver.findElement(By.id("clickMe"));
        actions.click(clickMe).perform();
        assertEquals(driver.findElement(By.id("button_0_normal")).getText(), "1", "Click did not happen");
    }

    @Test(description = "Click and hold element")
    public void clickAndHoldElement() {
        WebDriver driver = getDriver();
        driver.get("https://mousetester.com/");
        Actions actions = new Actions(driver);
        WebElement clickMe = driver.findElement(By.id("clickMe"));
        actions.clickAndHold(clickMe).pause(Duration.ofSeconds(5)).release().perform();
        assertEquals(driver.findElement(By.id("button_0_normal")).getText(), "1", "Click did not happen");
    }

    @Test(description = "Double click element")
    public void doubleClickElement() {
        WebDriver driver = getDriver();
        driver.get("https://mousetester.com/");
        Actions actions = new Actions(driver);
        WebElement clickMe = driver.findElement(By.id("clickMe"));
        actions.doubleClick(clickMe).perform();
        assertEquals(driver.findElement(By.id("button_0_normal")).getText(), "2", "Double click did not happen");
    }

    @Test(description = "Right click element")
    public void rightClickElement() {
        WebDriver driver = getDriver();
        driver.get("https://mousetester.com/");
        Actions actions = new Actions(driver);
        WebElement clickMe = driver.findElement(By.id("clickMe"));
        actions.contextClick(clickMe).perform();
        assertEquals(driver.findElement(By.id("button_2_normal")).getText(), "1", "Right click did not happen");
    }


}
