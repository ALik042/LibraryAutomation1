package com.library_practice.utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BrowserUtils {

    //1. This method will accept int (in seconds) and execute Thread.sleep for given duration
    public static void sleep(int second) {
        second *= 1000;
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {

        }
    }

    //2. When method is called, it should switch window and verify title.
    public static void switchWindowAndVerify(String expectedInUrl, String expectedInTitle) {
        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(windowHandle);
            if (Driver.getDriver().getCurrentUrl().contains(expectedInUrl)) {
                break;
            }
        }
        Assert.assertTrue(Driver.getDriver().getTitle().contains(expectedInTitle));
    }



    //4. Creating a utility method for ExplicitWait, so we don't need to repeat code
    public static void waitForInvisibilityOf(WebElement webElement) {
        Driver.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);//turning off implicitWait
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    //5. Verify if title contains provided keyword
    public static void verifyURLContains(String expectedInURL) {
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(expectedInURL));
    }

    //6. This method will accept dropdown as an argument and
    //return all options as a List of String
    public static List<String> dropdownOptionsAsString(WebElement dropdownElement) {
        Select selectMonth = new Select(dropdownElement);
        List<WebElement> options = selectMonth.getOptions();
        List<String> result = new ArrayList<>();

        for (WebElement option : options) {
            result.add(option.getText());
        }
        return result;
    }

    /**
     * This method will accept a group radio buttons as a List of WebElement.
     * It will loop through the List, and click to the radio button with provided attributeValue
     * @param radioButtons
     * @param attributeValue
     */
    public static void clickRadioButton(List<WebElement> radioButtons, String attributeValue) {
        for (WebElement each : radioButtons) {
            if (each.getAttribute("value").equals(attributeValue)) {
                each.click();
            }
        }

    }

    /**
     * Switches to new window by the exact title. Returns to original window if target title not found
     * @param targetTitle
     */
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    /**
     * Moves the mouse to given element
     *
     * @param element on which to hover
     */
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    /**
     * return a list of string from a list of elements
     *
     * @param list of webelements
     * @return list of string
     */
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * Extracts text from list of elements matching the provided locator into new List<String>
     *
     * @param locator
     * @return list of strings
     */
    public static List<String> getElementsText(By locator) {

        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();

        for (WebElement el : elems) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * Performs a pause
     *
     * @param seconds
     */
    public static void  waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element parameter
     * @param timeToWaitInSec parameter
     * @return parameter
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element matching the locator to be visible on the page
     *
     * @param locator
     * @param timeout
     * @return
     */
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for provided element to be clickable
     *
     * @param element
     * @param timeout
     * @return
     */
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for element matching the locator to be clickable
     *
     * @param locator
     * @param timeout
     * @return
     */
    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOutInSeconds
     */
    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     *
     * @param by
     * @throws AssertionError if the element matching the provided locator is not found or not displayed
     */
    public static void verifyElementDisplayed(By by) {
        try {
            Assert.assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + by);

        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param by
     * @throws AssertionError the element matching the provided locator is displayed
     */
    public static void verifyElementNotDisplayed(By by) {
        try {
            Assert.assertFalse("Element should not be visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();

        }
    }


    /**
     * Verifies whether the element is displayed on page
     *
     * @param element
     * @throws AssertionError if the element is not found or not displayed
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }


    /**
     * Waits for element to be not stale
     *
     * @param element
     */
    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }


    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Performs double click action on an element
     *
     * @param element
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Changes the HTML attribute of a Web Element to the given value using JavaScript
     *
     * @param element
     * @param attributeName
     * @param attributeValue
     */
    public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    /**
     * Highlighs an element by changing its background and border color
     * @param element
     */
    public static void highlight(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        waitFor(1);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].removeAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * Checks or unchecks given checkbox
     *
     * @param element
     * @param check
     */
    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * attempts to click on provided element until given time runs out
     *
     * @param element
     * @param timeout
     */
    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param element
     */
    public static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command, element);

    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param command
     */
    public static void executeJScommand(String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command);

    }


    /**
     * This method will recover in case of exception after unsuccessful the click,
     * and will try to click on element again.
     *
     * @param by
     * @param attempts
     */
    public static void clickWithWait(By by, int attempts) {
        int counter = 0;
        //click on element as many as you specified in attempts parameter
        while (counter < attempts) {
            try {
                //selenium must look for element again
                clickWithJS(Driver.getDriver().findElement(by));
                //if click is successful - then break
                break;
            } catch (WebDriverException e) {
                //if click failed
                //print exception
                //print attempt
                e.printStackTrace();
                ++counter;
                //wait for 1 second, and try to click again
                waitFor(1);
            }
        }
    }

    /**
     *  checks that an element is present on the DOM of a page. This does not
     *    * necessarily mean that the element is visible.
     * @param by
     * @param time
     */
    public static void waitForPresenceOfElement(By by, long time) {
        new WebDriverWait(Driver.getDriver(), time).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    // Andriy's added methods below:

    public static void goTo(String url){
        Driver.getDriver().get(url);
    }

    public static void clickElement(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    public static void enterText(WebElement webElement, String textToEnter){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.clear();
        webElement.sendKeys(textToEnter);
    }


    //this method accepts a String 'expected title' and asserts if it's true
    public static void verifyTitle(String expectedTitle){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),3);
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        assertEquals( expectedTitle, Driver.getDriver().getTitle() );
    }

    public static void verifyTitleContains(String expectedInTitle){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),3);
        wait.until(ExpectedConditions.titleContains(expectedInTitle));
        assertTrue(Driver.getDriver().getTitle().contains(expectedInTitle));
    }

    public static void verifyElementText(WebElement webElement, String expectedText){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        assertEquals( expectedText, webElement.getText());
    }

    public static void verifyElementTextContains(WebElement webElement, String expectedInText){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        assertTrue(webElement.getText().contains(expectedInText));
    }

    public static void verifyListsAreEqual(List<WebElement> actual, List<String> expected){
        List<String> actualTexts = actual.stream().map(WebElement::getText).collect(Collectors.toList());
        assertEquals(expected, actualTexts);
    }

    public static void moveToElement(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(webElement).perform();
    }

    public static void switchToIframe(WebElement webElement){
        Driver.getDriver().switchTo().frame(webElement);  // locate the frame and switch to it
    }
    public static void switchToIframe(String idOrNameOfIframe){
        Driver.getDriver().switchTo().frame(idOrNameOfIframe);
    }
    public static void switchToIframe(int indexOfIframe){
        Driver.getDriver().switchTo().frame(indexOfIframe);
    }

    public static void acceptAlert(){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
        wait.until(ExpectedConditions.alertIsPresent());
        Driver.getDriver().switchTo().alert().accept();
    }
    public static void dismissAlert(){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
        wait.until(ExpectedConditions.alertIsPresent());
        Driver.getDriver().switchTo().alert().dismiss();
    }
    public static void enterTextAndAcceptAlert(String text){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = Driver.getDriver().switchTo().alert();
        alert.sendKeys(text);
        alert.accept();
    }

    public static void switchToWindowHandle(String expectedInWindowTitle){
        String currentHandle = Driver.getDriver().getWindowHandle();
        for (String each: Driver.getDriver().getWindowHandles()){
            Driver.getDriver().switchTo().window(each);
            if(Driver.getDriver().getTitle().contains(expectedInWindowTitle)){
                break;
            }else{
                Driver.getDriver().switchTo().window(currentHandle);
            }
        }
    }

    public static void selectFromDropdownByIndex(WebElement selectTagElement ,int index){
        new Select(selectTagElement).selectByIndex(index);
    }
    public static void selectFromDropdownByValue(WebElement selectTagElement, String value){
        new Select(selectTagElement).selectByValue(value);
    }
    public static void selectFromDropdownByVisibleText(WebElement selectTagElement, String visibleText){
        new Select(selectTagElement).selectByVisibleText(visibleText);
    }

    public static List<String> getDropdownOptionsAsListOfString(WebElement dropdownElement){
        Select select = new Select(dropdownElement);
        return select.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // only works if attribute @type='file'
    public static void uploadFile(WebElement webElement, String filePath){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
        wait.until(ExpectedConditions.attributeToBe(webElement, "type", "file"));
        webElement.sendKeys(filePath);
    }

    public static void openNewWindow(String url){
        ((JavascriptExecutor)Driver.getDriver()).executeScript("window.open('"+url+"','_blank')");
    }

    public static void scrollIntoView(WebElement webElement){
        ((JavascriptExecutor)Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true)", webElement);
    }

    public static String getElementText(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),5);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        return webElement.getText();
    }

    public static void verifyCheckboxIsSelected(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
        wait.until(ExpectedConditions.elementSelectionStateToBe(webElement, true));
        Assert.assertTrue(webElement.isSelected());
    }

    public static void verifyCheckboxIsNotSelected(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
        wait.until(ExpectedConditions.elementSelectionStateToBe(webElement, false));
        Assert.assertFalse(webElement.isSelected());
    }

    public static void refreshPage(){
        Driver.getDriver().navigate().refresh();
    }

    // this method will work after popup window for file upload is opened
    public static void uploadFileUsingRobot(String filePath){
        //path to file
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
        try {
            //creating robot object
            Robot rb  = new Robot();
            // Ctr+C
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);

            // Ctr+V
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_V);

            // Enter
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);

        } catch (AWTException e) {
            e.printStackTrace();
            System.out.println("There's no file bar for file upload");
        }
    }

    public static void waitUntilElementIsVisible(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void verifyElementIsNotDisplayed(WebElement webElement){
        try {
            Assert.assertFalse(webElement.isDisplayed());
        }catch (NoSuchElementException e){
            System.out.println("Assertion passed. Element is not displayed");
        }
    }

    public static void waitForElementTextToBe(WebElement webElement, String expectedText){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
        wait.until(ExpectedConditions.textToBePresentInElement(webElement, expectedText));
    }

    // this method sets any element's attribute value to whatever we need
    public static void setAttributeWithAnyValue(WebElement element, String attName, String attValue) {
        ((JavascriptExecutor)Driver.getDriver()).
executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attName, attValue);
    }

    public static void verifyIfCheckboxIsProperlySelectable(WebElement checkbox){
        if(!checkbox.isSelected()){
            clickElement(checkbox);
            verifyCheckboxIsSelected(checkbox);
        }else if(checkbox.isSelected()){
            clickElement(checkbox);
            verifyCheckboxIsNotSelected(checkbox);
        }
    }






}
