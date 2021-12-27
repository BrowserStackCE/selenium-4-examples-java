package com.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.annotations.Test;

public class ManageWindowsTabsTest extends BaseTest {

    @Test(description = "Window management")
    public void windowManagement() {
        WebDriver driver = getDriver();
        driver.get("https://bstackdemo.com");
        String bstackHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.navigate().to("https://www.browserstack.com/");
        sleep(5);
        driver.switchTo().window(bstackHandle);
    }

    @Test(description = "Tab management")
    public void tabManagement() {
        WebDriver driver = getDriver();
        driver.get("https://bstackdemo.com");
        String bstackHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://www.browserstack.com/");
        sleep(5);
        driver.switchTo().window(bstackHandle);
    }


}
