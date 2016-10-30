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

package com.github.licanhua.test.framework;

import com.github.licanhua.test.framework.base.*;
import com.github.licanhua.test.framework.config.Configuration;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 *
 * All Page object should be extends from {@code Page}.
 *<pre style="code">
 *public class HomePage{
 *     {@literal @}{@link org.openqa.selenium.support.FindBy}(id="navi")
 *     NavigationMen navigationMenu;
 *
 *     {@literal @}{@link org.openqa.selenium.support.FindBy}(id="copyright")
 *     WebElement copyright
 *</pre>
 *
 *
 *<pre style="code">
 *public class CarSearchTest{
 *     {@literal @}{@code Rule}
 *     public {@link AutomationDriver} automationDriver = new {@code AutomationDriver}();
 *
 *     {@literal @}{@code Test}
 *     public void carSearchTest() {
 * 		HomePage homePage = new HomePage();
 * 		CarSearchPage carSearchPage = new CarSearchPage();
 * 		carSearchPage.search("Seattle", "LAS");
 *     }
 * }
 *</pre>
 *
 *If two or more browser need to be controlled, all subclass extends from {@code Page}
 * should provide two constuctors, a no parameter one and one with paremeter{@code (AutomationDriver automationDriver)}
 *<pre style="code">
 *public class HomePage{
 *  public HomePage() {super();}
 *  public HomePage((AutomationDriver automationDriver)) {super(automationDriver);}
 *     {@literal @}{@link org.openqa.selenium.support.FindBy}(id="navi")
 *     NavigationMen navigationMenu;
 *
 *     {@literal @}{@link org.openqa.selenium.support.FindBy}(id="copyright")
 *     WebElement copyright
 *</pre>
 *
 *<pre style="code">
 *public class CarSearchTest{
 *     {@literal @}Rule
 *     public {@link AutomationDriver} automationDriver = new AutomationDriver();
 *     public {@link AutomationDriver} automationDriver2 = new AutomationDriver();
 *
 *     {@literal @}Test
 *     public void carSearchTest() {
 * 		HomePage homePage = new HomePage(automationDriver);
 * 		HomePage homePage2 = new HomePage(automationDriver2);
 * 		CarSearchPage carSearchPage = new CarSearchPage(automationDriver);
 * 		carSearchPage.search("Seattle", "LAS");
 * 		FlightSearchPage flightSearchPage = new FlightSearchPage(automationDriver2);
 * 		flightSearchPage.search("SEA", "LAS");
 *     }
 * }
 *</pre>
 *
 * @author Canhua Li
 */
public class Page extends AbstractElement {
    static class RootPage implements Element{
        ElementContext elementContext;

        public RootPage(ElementContext elementContext) {
            this.elementContext = elementContext;
        }

        public ElementContext getElementContext() {
            return elementContext;
        }

        public Configuration getConfiguration() {
            return elementContext.getConfiguration();
        }

        public void setWrappedElement(WebElement wrappedElement) {
        }

        public Element getParent() {
            return this;
        }
    }
    private static final Logger logger = Logger.getLogger(Page.class.getName());

    Element createDummyPage(final ElementContext elementContext) {
        logger.debug("Create dummy root page for the page object");
        return new RootPage(elementContext);
    }

    public Page() {
        super();
        logger.debug("Page object is to be created with Global EnvironmentContext");
        initPage(ElementContext.createDummyContext());
    }

    private void initPage(ElementContext elementContext) {
        logger.info("Create Page - " + this.getClass().getName());
        initElements(createDummyPage(elementContext));
    }

    public Page(ElementContext parent) {
        super();
        initPage(parent);
    }

    /**
     * Create and initialize a page, you don't need to care about only if you have two or more
     * {@link AutomationDriver} in your test case.
     *
     * @param automationDriver used to get the EnvironmentContext
     */
    public Page(AutomationDriver automationDriver) {
        super();
        EnvironmentContext environmentContext = automationDriver.getEnvironmentContext();
        ElementContext elementContext = ElementContext.createDummyContext(environmentContext);
        initElements(createDummyPage(elementContext));
    }
}
