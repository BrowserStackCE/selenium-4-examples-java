# Selenium 4: Understanding Key Features <a href="https://www.browserstack.com/"><img src="https://www.vectorlogo.zone/logos/browserstack/browserstack-icon.svg" alt="BrowserStack" height="30"/></a> <a href="https://java.com"><img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="Java" height="30" /></a> <a href="https://www.selenium.dev/"><img src="https://seeklogo.com/images/S/selenium-logo-DB9103D7CF-seeklogo.com.png" alt="Selenium" height="30" /></a>

[![BrowserStack Status](https://automate.browserstack.com/badge.svg?badge_key=SkY5aTNjQkJGekNDNmpGMlVJOXo0anZqVzZYMXdJSzhDT1piZ3IyTzBPOD0tLS8vbjFOcEN0TDZaTHcveG5WRk9GTlE9PQ==--162f8400d22ee6afe02f8674e54a31747e7e08c3)](https://automate.browserstack.com/public-build/SkY5aTNjQkJGekNDNmpGMlVJOXo0anZqVzZYMXdJSzhDT1piZ3IyTzBPOD0tLS8vbjFOcEN0TDZaTHcveG5WRk9GTlE9PQ==--162f8400d22ee6afe02f8674e54a31747e7e08c3)

[Selenium](https://www.browserstack.com/selenium) has been the most preferred tool suite when it comes to automated cross-browser testing of web applications. Simon Stewart (creator of WebDriver and core contributor to Selenium projects) had publicly introduced Selenium 4 in 2018. Since then, Selenium 4 has been gaining immense traction for its new makeover in terms of features and functionalities.

This repo provides BrowserStack Automate's integration with Selenium 4 showcasing key features.

---

## Setup

- Clone the repo
- Install dependencies `mvn compile`
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

To run all the Selenium 4 features present in the repo, run the below mentioned maven command.

``` 
mvn -P selenium4-features test
```

---

## Event-driven testing

Selenium 4 uses Chrome Debugging Protocol (CDP) to achieve event-driven testing with the help of the features provided below.

If you plan to perform event-driven tests, use the seleniumCdp capability in your test script.

| Capability | Description | Expected values |
| ---   | --- | --- |
| ```seleniumCdp``` | Enables the Selenium’s CDP features in a test script. | Boolean. Set to ```true``` if you plan to use CDP features. Accepted values are ```true``` and ```false```. Default is ```false```. |

### Console log events

Run ```mvn -Dtest=EventDriverTest#consoleLogEvents test```. The [test script](src/test/java/com/features/EventDriverTest.java) opens the [BrowserStack Demo](https://bstackdemo.com/) website and logs the console events to your IDE’s console.

### JavaScript exception

Run ```mvn -Dtest=EventDriverTest#javaScriptException test```. The [test script](src/test/java/com/features/EventDriverTest.java) opens the Facebook Sign in page and clicks the "Log In" button without entering the required sign-in credentials. Thus, an exception is thrown and displayed on your IDE’s console.

### Intercept network

Run ```mvn -Dtest=EventDriverTest#interceptNetwork test```. The [test script](src/test/java/com/features/EventDriverTest.java) performs network interception by mocking the response of the request sent to a non-existent web page.

### Capture performance metrics

Run ```mvn -Dtest=EventDriverTest#capturePerformanceMetrics test```. The [test script](src/test/java/com/features/EventDriverTest.java) opens [BrowserStack](https://browserstack.com) web page and logs its performance metrics on your IDE’s console.

### Enable basic auth

Run ```mvn -Dtest=EventDriverTest#enableBasicAuth test```. The [test script](src/test/java/com/features/EventDriverTest.java) opens a website and enters sample credentials in the basic auth popup window.

### Emulate network conditions

Run ```mvn -Dtest=EventDriverTest#emulateNetworkConditions test```. The [test script](src/test/java/com/features/EventDriverTest.java) emulates the network speed of 512 Kbps, visits a speed calculator website, and then prints the internet speed.

### Set device dimensions

Run ```mvn -Dtest=EventDriverTest#setDeviceDimensions test```. The [test script](src/test/java/com/features/EventDriverTest.java) overrides the default screen dimension with a new dimension and prints it.

### Emulate geo-location

Run ```mvn -Dtest=GeoLocationTest test```. The [test script](src/test/java/com/features/GeoLocationTest.java) sets the geolocation, visits the real-time location website, and then retrieves your machine’s live location

---

## Modifications in the Actions Class

### Click element

Run ```mvn -Dtest=MouseActionsTest#clickElement test```. In the [test script](src/test/java/com/features/MouseActionsTest.java), the ```click(WebElement)``` method is used which replaces the ```moveToElement(onElement).click()```. It is used to click on a certain web element.

### Click and hold element

Run ```mvn -Dtest=MouseActionsTest#clickAndHoldElement test```. In the [test script](src/test/java/com/features/MouseActionsTest.java), the ```clickAndHold(WebElement)``` method is used which replaces the ```moveToElement(onElement).clickAndHold()```. It is used to click on an element without releasing the click.

### Double click element

Run ```mvn -Dtest=MouseActionsTest#doubleClickElement test```. In the [test script](src/test/java/com/features/MouseActionsTest.java), the ```doubleClick(WebElement)``` method is used which replaces the ```moveToElement(onElement).doubleClick()```. It will perform a double click on an element.

### Right click element

Run ```mvn -Dtest=MouseActionsTest#rightClickElement test```. In the [test script](src/test/java/com/features/MouseActionsTest.java), the ```contextClick(WebElement)``` method is used which replaces the ```moveToElement(onElement).contextClick()```. It will perform the right click operation.

---

## Better Window/Tab Management

Selenium 4 comes with a new API – newWindow that allows users to create and switch to a new window/tab without creating a new WebDriver object.

### Window management

Run ```mvn -Dtest=ManageWindowsTabsTest#windowManagement test```. In the [test script](src/test/java/com/features/ManageWindowsTabsTest.java), the code ```driver.switchTo().newWindow(WindowType.WINDOW);``` is used to open a new window.

### Tab management

Run ```mvn -Dtest=ManageWindowsTabsTest#tabManagement test```. In the [test script](src/test/java/com/features/ManageWindowsTabsTest.java), the code ```driver.switchTo().newWindow(WindowType.TAB);``` is used to open a new tab within the same window.

---

## Specific element screenshot

Run ```mvn -Dtest=ScreenshotTest test```. The [test script](src/test/java/com/features/ScreenshotTest.java) takes a screenshot of an iPhone displayed on the [BrowserStack Demo](https://bstackdemo.com/) website and creates a device.png image file in the target folder.

---

## Relative locators

Run ```mvn -Dtest=RelativeLocatorsTest test```. The [test script](src/test/java/com/features/RelativeLocatorsTest.java) uses the [Automation book store](https://automationbookstore.dev/) website to locate web elements based on the visual location relative to other DOM elements.

---

## Notes
- You can view your Automate test results on the [BrowserStack Automate dashboard](https://automate.browserstack.com/).
- You can view your App-Automate test results on the [BrowserStack App-Automate dashboard](https://app-automate.browserstack.com/).
- Export the environment variables for the Username and Access Key of your BrowserStack account.
  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> && export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
---

## Documentation
- [Selenium 4: Understanding Key Features](https://www.browserstack.com/guide/selenium-4-features)
- [Learn about event-driven testing](https://www.browserstack.com/docs/automate/selenium/event-driven-testing)
- [BrowserStack Selenium-4 Capabilities](https://www.browserstack.com/automate/capabilities?tag=selenium-4)
- [Selenium Documentation: Upgrade to Selenium 4](https://www.selenium.dev/documentation/webdriver/getting_started/upgrade_to_selenium_4/)