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
import org.openqa.selenium.support.FindBy;

public class CarSearchPage extends Page {
    private static Logger logger = Logger.getLogger(CarSearchPage.class.getName());

    @FindBy(id="wizard-theme-wrapper")
    CarSearchComponent carSearchComponent;

    public void doSearch() {
        logger.info("Search car from DEN to SAN");
        carSearchComponent.searchCar("DEN","SAN");
    }
}
