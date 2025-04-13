package com.w2a.errorController;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ActionLib extends BasePage {

    public static void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            test.log(LogStatus.INFO, "Clicked on the element: " + element);
        } catch (ElementNotInteractableException e) {
            throw new ElementNotInteractableException("Unable to click on the element: " + element, e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while clicking the element: " + element, e);
        }
    }

    public static void click(WebElement element, String message) {
        click(element); // Reuse the click method
        test.log(LogStatus.INFO, message);
    }

    public static void enterText(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            test.log(LogStatus.INFO, "Entered text '" + text + "' in the field.");
        } catch (ElementNotInteractableException e) {
            throw new ElementNotInteractableException("Unable to enter text in the element: " + element, e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while entering text in the element: " + element, e);
        }
    }

    public static String getText(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            String text = element.getText();
            test.log(LogStatus.INFO, "Retrieved text: '" + text + "' from the element.");
            return text;
        } catch (Exception e) {
            throw new RuntimeException("Unable to get text from the element: " + element, e);
        }
    }

    public static boolean isVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            return false; // If there's an exception, assume it's not displayed
        }
    }

    public static void selectDropDownOption(WebElement element, Action optionType, String optionValue) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            Select dropdown = new Select(element);

            switch (optionType) {
                case TEXT:
                    dropdown.selectByVisibleText(optionValue);
                    test.log(LogStatus.INFO, "Selected option by text: '" + optionValue + "' from the dropdown.");
                    break;
                case VALUE:
                    dropdown.selectByValue(optionValue);
                    test.log(LogStatus.INFO, "Selected option by value: '" + optionValue + "' from the dropdown.");
                    break;
                case INDEX:
                    int index = Integer.parseInt(optionValue);
                    dropdown.selectByIndex(index);
                    test.log(LogStatus.INFO, "Selected option by index: " + index + " from the dropdown.");
                    break;
                default:
                    log.info("Invalid option type. Please use 'text', 'value', or 'index'.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid index value for dropdown selection: " + optionValue, e);
        } catch (ElementNotInteractableException e) {
            throw new ElementNotInteractableException("Unable to interact with the dropdown element: " + element, e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while selecting an option in the dropdown: " + element, e);
        }
    }

    public static void hoverOverElement(WebElement element){
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.moveToElement(element).perform();
            test.log(LogStatus.INFO, "Hover over the elements: "+element);
        }catch (Exception e) {
            throw new RuntimeException("An error occurred while hovering over the element: "+e.getMessage());
        }
    }

    public static void doubleClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.doubleClick(element).perform();
            test.log(LogStatus.INFO, "Double-clicked on the element: " + element);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while hovering over the element: " + e.getMessage());
        }
    }
    public static void rightClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.contextClick(element).perform();
            test.log(LogStatus.INFO, "Right-clicked on the element: " + element);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while right-clicking the element: " + element, e);
        }
    }
    public static void pressKey(Keys key) {
        try {
            actions.sendKeys(key).perform();
            test.log(LogStatus.INFO, "Pressed key: " + key.name());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while pressing the key: " + key.name(), e);
        }
    }
    public static void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            test.log(LogStatus.INFO, "Scrolled to the element: " + element);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while scrolling to the element: " + element, e);
        }
    }

    public static void dragAndDrop(WebElement source, WebElement target) {
        try {
            wait.until(ExpectedConditions.visibilityOf(source));
            wait.until(ExpectedConditions.visibilityOf(target));
            actions.dragAndDrop(source, target).perform();
            test.log(LogStatus.INFO, "Dragged element to target: " + source + " -> " + target);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while dragging and dropping the element: " + source + " to " + target, e);
        }
    }

    public static void jsClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
            test.log(LogStatus.INFO, "Clicked on the element using JavaScript: " + element);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while clicking the element using JavaScript: " + element, e);
        }
    }

    public static void setValueUsingJS(WebElement element, String value) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='" + value + "';", element);
            test.log(LogStatus.INFO, "Set value using JavaScript: '" + value + "' for the element.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while setting the value using JavaScript: " + element, e);
        }
    }

    public static void uploadFile(WebElement element, String filePath) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(filePath);
            test.log(LogStatus.INFO, "Uploaded file at path: " + filePath);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while uploading the file: " + filePath, e);
        }
    }

    public static void switchToFrame(WebElement frameElement) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
            test.log(LogStatus.INFO, "Switched to frame: " + frameElement);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while switching to the frame: " + frameElement, e);
        }
    }

    public static void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            test.log(LogStatus.INFO, "Switched back to the default content.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while switching back to the default content.", e);
        }
    }

    public static void navigateTo(String url) {
        try {
            driver.get(url);
            test.log(LogStatus.INFO, "Navigated to URL: " + url);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while navigating to the URL: " + url, e);
        }
    }

    public static void refreshPage() {
        try {
            driver.navigate().refresh();
            test.log(LogStatus.INFO, "Page refreshed.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while refreshing the page.", e);
        }
    }

    public static void navigateBack() {
        try {
            driver.navigate().back();
            test.log(LogStatus.INFO, "Navigated back.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while navigating back.", e);
        }
    }

    public static void navigateForward() {
        try {
            driver.navigate().forward();
            test.log(LogStatus.INFO, "Navigated forward.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while navigating forward.", e);
        }
    }

    public static void acceptAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            test.log(LogStatus.INFO, "Alert accepted.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while accepting the alert.", e);
        }
    }


    public static void dismissAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss();
            test.log(LogStatus.INFO, "Alert dismissed.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while dismissing the alert.", e);
        }
    }

    public static String getAlertText() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            test.log(LogStatus.INFO, "Retrieved alert text: " + alertText);
            return alertText;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving alert text.", e);
        }
    }

    public static void switchToWindow(String windowTitle) {
        try {
            String currentWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
                if (driver.getTitle().equals(windowTitle)) {
                    test.log(LogStatus.INFO, "Switched to window with title: " + windowTitle);
                    return;
                }
            }
            driver.switchTo().window(currentWindow);
            throw new RuntimeException("Window with title " + windowTitle + " not found.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while switching to the window: " + windowTitle, e);
        }
    }

    public static void closeCurrentWindow() {
        try {
            driver.close();
            test.log(LogStatus.INFO, "Closed the current window/tab.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while closing the current window/tab.", e);
        }
    }

    public static void waitForPageToLoad() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
            test.log(LogStatus.INFO, "Page has fully loaded.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while waiting for the page to load.", e);
        }
    }


    public static void addCookie(Cookie cookie) {
        try {
            driver.manage().addCookie(cookie);
            test.log(LogStatus.INFO, "Added cookie: " + cookie.getName());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while adding the cookie: " + cookie.getName(), e);
        }
    }


    public static Cookie getCookie(String name) {
        try {
            Cookie cookie = driver.manage().getCookieNamed(name);
            test.log(LogStatus.INFO, "Retrieved cookie: " + name);
            return cookie;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the cookie: " + name, e);
        }
    }

    public static boolean isFileDownloaded(String downloadPath, String fileName) {
        try {
            File file = new File(downloadPath + fileName);
            boolean exists = file.exists();
            test.log(LogStatus.INFO, "File " + (exists ? "exists" : "does not exist") + ": " + fileName);
            return exists;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while verifying the file download: " + fileName, e);
        }
    }


    public static void highlightElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid red'", element);
            test.log(LogStatus.INFO, "Element highlighted: " + element);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while highlighting the element: " + element, e);
        }
    }
}
