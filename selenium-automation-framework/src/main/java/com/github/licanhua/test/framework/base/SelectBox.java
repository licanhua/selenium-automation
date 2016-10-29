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

import com.github.licanhua.test.framework.util.PageHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * @author Canhua Li
 */
public class SelectBox extends CustomElement implements ISelect {
    private static Logger logger = Logger.getLogger(SelectBox.class.getName());

    Select select;

    private Select getSelect() {
        if (select == null)
           select = new Select(this.getWrappedElement());
        return select;
    }

    @Override
    public void validate() {
        logger.info("validate for SelectBox");
        //logger.debug("Wrapper object: " + getWrappedElement());
        PageHelper.waitForElementToBePresent(getWrappedElement(), getParent());
    }

    public boolean isMultiple() {
        return getSelect().isMultiple();
    }

    public List<WebElement> getOptions() {
        return getSelect().getOptions();
    }

    public List<WebElement> getAllSelectedOptions() {
        return getSelect().getAllSelectedOptions();
    }

    public WebElement getFirstSelectedOption() {
        return getSelect().getFirstSelectedOption();
    }

    public void selectByVisibleText(String text) {
        getSelect().selectByVisibleText(text);
    }

    public void selectByIndex(int index) {
        getSelect().selectByIndex(index);
    }

    public void selectByValue(String value) {
        getSelect().selectByValue(value);
    }

    public void deselectAll() {
        getSelect().deselectAll();
    }

    public void deselectByValue(String value) {
        getSelect().deselectByValue(value);
    }

    public void deselectByIndex(int index) {
        getSelect().deselectByIndex(index);
    }

    public void deselectByVisibleText(String text) {
        getSelect().deselectByVisibleText(text);
    }
}
