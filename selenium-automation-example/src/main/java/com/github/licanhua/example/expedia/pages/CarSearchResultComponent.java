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

import com.github.licanhua.test.framework.Container;

import com.github.licanhua.test.framework.annotation.RelativeElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CarSearchResultComponent extends Container{
    private static Logger logger = Logger.getLogger(CarSearchResultComponent.class.getName());

    @RelativeElement
    @FindBy(xpath=".//a[starts-with(@id, 'ember')]")
    List<WebElement> reserveButtons;

    public void reserve(int index) {
        logger.info("Search result item found " + reserveButtons.size());
        for (int i=0; i<reserveButtons.size(); i++) {
            logger.info(reserveButtons.get(i).getText());
        }
        if (reserveButtons.size() > index)
            reserveButtons.get(index).click();
        else
            throw new RuntimeException("index is out of boundary, index= " + index);

    }
}
