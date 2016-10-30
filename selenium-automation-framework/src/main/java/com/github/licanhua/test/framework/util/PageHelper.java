/* Copyright (C) 2016 The Selenium Automation Framework Authors
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License
* is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
* or implied. See the License for the specific language governing permissions and limitations under
* the License.
*
* Created by canhua li (licanhua@live.com)
*
*/

package com.github.licanhua.test.framework.util;

import com.github.licanhua.test.framework.AutomationDriver;
import com.github.licanhua.test.framework.Global;
import com.github.licanhua.test.framework.base.Element;
import com.github.licanhua.test.framework.base.ElementContext;
import com.github.licanhua.test.framework.base.EnvironmentContext;
import com.github.licanhua.test.framework.base.WebDriverContext;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PageHelper {
    private static Logger logger = Logger.getLogger(PageHelper.class.getName());

    public static void takeSnapshot(ElementContext elementContext){
        WebDriverContext webDriverContext = elementContext.getWebDriverContext();
        webDriverContext.getWebDriverProvider().takesScreenshot(webDriverContext);
    }

    public static <T> T toPage(Class<T> pageClass) {
        try {
            logger.info("create page with " + pageClass.getName() + "()");
            return (T) pageClass.newInstance();
        } catch (Exception e) {
            Throwables.propagate(e);
        }

        return null;
    }

    public static <T> T toPage(Class<T> pageClass, ElementContext parent) {
        try {
            logger.info("create page with " + pageClass.getName() + "(ElementContext)");
            Constructor e = pageClass.getConstructor(new Class[]{ElementContext.class});
            return (T) e.newInstance(new Object[]{parent});
        } catch (NoSuchMethodException e) {
            logger.info(pageClass.getName() + "(ElementContext parent) is not defined, try no parameter constructor");
            return toPage(pageClass);
        } catch (Exception e) {
            Throwables.propagate(e);
        }

        return null;
    }

    // Use this to create page if need to interact with more than 1 browser
    public static <T> T toPage(Class<T> pageClass, AutomationDriver driver) {
        logger.info("Try to create new page with AutomationDriver context");
        try {
            logger.debug("   create page with " + pageClass.getName() + "(AutomationDriver)");
            Constructor e = pageClass.getConstructor(new Class[]{AutomationDriver.class});
            return (T) e.newInstance(new Object[]{driver});
        } catch (Exception e) {
            Throwables.propagate(e);
        }

        return null;
    }

    /*
    public static <T> T clickAndToPage(WebElement webElement, Class<T> pageClass) {
        logger.info("Try to create ElementContext for " + pageClass.getName());
        return clickAndToPage(webElement, pageClass, null);
    }
    */

    public static <T> T clickAndToPage(WebElement webElement, Class<T> pageClass, Element parent) {
        checkNotNull(webElement);
        checkNotNull(pageClass);
        checkNotNull(parent);

        ElementContext elementContext = parent.getElementContext();
        logger.info("Take snapshot");

        // Take snapshot
        takeSnapshot(elementContext);

        logger.info("Click on "  + webElement.getText());
        webElement.click();

        // Wait for Page Load Complete

        logger.info("jump to new page " + pageClass.getName());
        return toPage(pageClass, elementContext);
    }

    private static <T> void waitForElement(String message, ElementContext context, final T element, final Predicate<T> predicate) {
        WebDriver webDriver = context.getWebDriver();
        int waitDuration = context.getWaitDurationInSeconds();

        try{
            Wait<WebDriver> wait = new WebDriverWait(webDriver, waitDuration, 100);
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(final WebDriver webDriver) {
                    try {
                        return predicate.apply(element);
                    } catch (final NoSuchElementException e) {
                        return false;
                    }catch (final StaleElementReferenceException e)
                    {
                        return false;
                    }
                }
            });
        } catch (TimeoutException e){
            TimeoutException te = new TimeoutException(message + " timeout!", e);
            throw te;
        }
    }

    private static <T> void waitForElement(String functionName, Element parent, T element, final Predicate<T> predicate) {
        String message = constructFunctionMessage(functionName, element, parent);
        logger.info(message);
        waitForElement(message, parent.getElementContext(), element, predicate);
        logger.info(message + " complete");
    }

    private static <T> String constructFunctionMessage(String functionName, T element, Element parent) {
        checkNotNull(element);
        checkNotNull(parent);

        return functionName + "(" + element.getClass().getName() + ", " + parent.getClass().getName() + ")";
    }

    /**
     * Use this method to ensure that this element is displayed.
     * @param element to
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForElementToBeDisplayed(WebElement element, Element parent) {
        waitForElement("waitForElementToBeDisplayed", parent, element, ElementPredicate.elementToBeDisplayed());
    }

    /**
     * Use this method to ensure that this element is hidden.
     * @param element to check
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForElementToBeHidden(WebElement element, Element parent) {
        waitForElement("waitForElementToBeHidden", parent, element, ElementPredicate.elementToBeHidden());
    }

    /**
     * Use this method to ensure that this element is enabled.
     * @param element to check
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForElementToBeEnabled(final WebElement element, Element parent) {
        waitForElement("waitForElementToBeEnabled", parent, element, ElementPredicate.elementToBeEnabled());
    }

    /**
     * Use this method to ensure that this element is disabled.
     * @param element to check
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForElementToBeDisabled(final WebElement element, Element parent) {
        waitForElement("waitForElementToBeDisabled", parent, element, ElementPredicate.elementToBeDisabled());
    }

    /**
     * Use this method to ensure that this element is present.
     * @param element to
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForElementToBePresent(final WebElement element, Element parent) {
        waitForElement("waitForElementToBePresent", parent, element, ElementPredicate.presenceOfElement());
    }

    /**
     * Use this method to ensure that this element is not present.
     * @param element to
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForElementToBeAbsent(final WebElement element, Element parent) {
        waitForElement("waitForElementToBeAbsent", parent, element, ElementPredicate.absentOfElement());
    }

    /**
     * Use this method to ensure that at list one of element is present.
     * @param elements to
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForPresentOfAllElements(List<WebElement> elements, Element parent) {
        waitForElement("waitForPresentOfAllElements", parent, elements, ElementPredicate.presenceOfAllElements());
    }

    /**
     * Use this method to ensure that no element is present.
     * @param elements to
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForAbsentOfAllElements(final List<WebElement> elements, Element parent) {
        waitForElement("waitForAbsentOfAllElements", parent, elements, ElementPredicate.absentOfAllElement());
    }

    /**
     * Use this method to ensure that element is present. it can be displayed or hidden
     * @param element to wait
     * @param text to wait
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForTextToBePresentInElement(WebElement element, Element parent, String text) {
        waitForElement("waitForTextToBePresentInElement", parent, element, ElementPredicate.textToBePresentInElement(text));
    }

    /**
     * Use this method to ensure that element not is present.
     * @param element to wait
     * @param text to wait
     * @param parent the parent of element, mostly it's a subclass of Container or Page
     */
    public static void waitForTextToBeNotPresentInElement(WebElement element, Element parent, String text) {
        waitForElement("waitForTextToBeNotPresentInElement", parent, element, ElementPredicate.textToBeNotPresentInElement(text));
    }

    public static void waitForDocumentReadyState(WebDriverContext webDriverContext) {
        logger.info("Wait for document.readyState is complete");
        Wait<WebDriver> wait = new WebDriverWait(webDriverContext.getWebDriver(),webDriverContext.getWaitDurationInSeconds() , 100);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try{
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                }
                catch(TimeoutException e){
                    throw new TimeoutException("Load document timeout\n" + e.getMessage());
                }
            }
        });

    }
    public static void waitForDocumentReadyState(Element element) {
        waitForDocumentReadyState(element.getElementContext().getWebDriverContext());
    }

    public static void waitForDocumentReadyState() {
        waitForDocumentReadyState(Global.getWebDriverContext());
    }


}
