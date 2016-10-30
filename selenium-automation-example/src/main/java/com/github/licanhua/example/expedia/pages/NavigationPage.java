/*
 *
 *  * Copyright (C) 2016 The Selenium Automation Framework Authors
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  * in compliance with the License. You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *  *
 *  * Created by canhua li (licanhua@live.com)
 *  *
 *
 */

package com.github.licanhua.example.expedia.pages;

import com.github.licanhua.test.framework.Page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationPage extends Page {
    private static Logger logger = Logger.getLogger(NavigationPage.class.getName());

    @FindBy(id="primary-header-home")
    WebElement home;

    @FindBy(id="primary-header-package")
    WebElement bundleDeal;

    @FindBy(id="primary-header-hotel")
    WebElement hotels;

    @FindBy(id="primary-header-car")
    WebElement car;


    public void toCarPage() {
        logger.info("navigate to CarSearchPage");
        clickAndToPage(car, CarSearchPage.class);
    }

    public void toHomePage() {
        logger.info("navigate to HomePage");
        this.clickAndToPage(home, HomePage.class);
    }
}
