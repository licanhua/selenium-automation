/*
 * Copyright (C) 2016 The Selenium Automation Framework Authors
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

package com.github.licanhua.test.framework.base;

import com.github.licanhua.test.framework.config.Configuration;
import com.github.licanhua.test.framework.util.PageHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
* @author Canhua Li
*/
public abstract class AbstractElement implements Element, ValidationHandler,
        ElemementInitAware, WaitFunctions, Navigation{
    private static final Logger logger = Logger.getLogger(AbstractElement.class.getName());
    private WebElement wrappedElement;

    public void setWrappedElement(WebElement wrappedElement) {
         this.wrappedElement = wrappedElement;
    }

    protected WebElement getWrappedElement() {
        return wrappedElement;
    }
    private ValidationHandler nextValidatationHandler;
    private ElementContext elementContext;
    private Element parent;

    /**
     * {@inheritDoc}
     */
    public Element getParent() {
        return parent;
    }

    // this will be called by framework automatically, you should not override this function.
    public void setNextHandler(ValidationHandler handler) {
        nextValidatationHandler = handler;
    }

    /**
    * After page is created, framework automatically validates the page.
    * This function is call first. nextValidatationHandler validates for the present of
     * all non-optional WebElements.
    * Subclass can override this function. skip nextValidatationHandler if bypass framework validation
    */
    public void validate() {
        if (nextValidatationHandler != null) {
            nextValidatationHandler.validate();
        }
    }


    /**
     * {@inheritDoc}
     */
    public void onInitComplete() {

    }

    /**
     * {@inheritDoc}
     */
    public ElementContext getElementContext() {
        return elementContext;
    }


    public void initElements(Element parent) {
        this.parent = parent;
        this.elementContext = ElementContext.createFromParentContext(parent, this.getClass());

        logger.info(this.getClass()+ " try to create all Element and WebElement belongs to this object");

        if (elementContext.isAjax()) {
            logger.debug("This is a ajax page");
        } else {
            logger.debug("This is not a ajax page, waitForDocumentReadyState is automatically called for you");
            waitForDocumentReadyState();
        }

        FieldDecorator fieldDecorator = new CustomFieldDecorator(new CustomElementLocatorFactory(this), this);
        PageFactory.initElements(new ValidateFieldDecorator(fieldDecorator, this, elementContext.isValidate()), this);

        logger.info(this.getClass() + " create elements complete");
        onInitComplete();
    }


    public WebDriver getWebDriver() {
        return elementContext.getWebDriver();
    }
    public Configuration getConfiguration() { return elementContext.getConfiguration();}

    public <T> T toPage(Class<T> pageClass) {
        return PageHelper.toPage(pageClass, this.getElementContext());
    }

    public <T> T clickAndToPage(WebElement webElement, Class<T> pageClass) {
        return PageHelper.clickAndToPage(webElement, pageClass, this);
    }

    public void waitForDocumentReadyState() {
        PageHelper.waitForDocumentReadyState(this);
    }

    public void waitForTextToBePresentInElement(WebElement element, String text) {
        PageHelper.waitForTextToBePresentInElement(element, this, text);
    }

    public void waitForElementToBeHidden(WebElement element) {
        PageHelper.waitForElementToBeHidden(element, this);
    }

    public void waitForElementToBePresent(WebElement element) {
        PageHelper.waitForElementToBePresent(element, this);
    }

    public void waitForElementToBeAbsent(WebElement element) {
        PageHelper.waitForElementToBeAbsent(element, this);
    }

    public void waitForPresenceOfAllElements(List<WebElement> elements) {
        PageHelper.waitForPresentOfAllElements(elements, this);
    }

    public void waitForAbsentOfAllElements(List<WebElement> elements) {
        PageHelper.waitForAbsentOfAllElements(elements, this);
    }

    public void waitForElementToBeDisplayed(WebElement element) {
        PageHelper.waitForElementToBeDisplayed(element, this);
    }

    public void waitForElementToBeEnabled(WebElement element) {
        PageHelper.waitForElementToBeEnabled(element, this);
    }

    public void waitForElementToBeDisabled(WebElement element) {
        PageHelper.waitForElementToBeDisabled(element, this);
    }
}
