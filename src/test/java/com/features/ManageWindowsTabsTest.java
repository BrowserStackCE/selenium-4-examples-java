package com.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.annotations.Test;

public class ManageWindowsTabsTest extends BaseTest {

    private static final String URL = "https://bstackdemo.com";
    private static final String NEW_URL = "https://www.browserstack.com";

    @Test(description = "Window management")
    public void windowManagement() {
        WebDriver driver = getDriver();
        driver.get(URL);
        String bstackHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.navigate().to(NEW_URL);
        sleep(5);
        driver.switchTo().window(bstackHandle);
    }

    @Test(description = "Tab management")
    public void tabManagement() {
        WebDriver driver = getDriver();
        driver.get(URL);
        String bstackHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to(NEW_URL);
        sleep(5);
        driver.switchTo().window(bstackHandle);
    }


}
