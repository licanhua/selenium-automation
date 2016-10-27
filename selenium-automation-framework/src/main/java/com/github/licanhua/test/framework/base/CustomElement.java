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

import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

/**
 * @author Canhua Li
 */
public abstract class CustomElement extends AbstractElement implements WebElement {

    public void click() {
        getWrappedElement().click();
    }

    public void submit() {
        getWrappedElement().submit();
    }

    public void sendKeys(CharSequence... keysToSend) {
        getWrappedElement().sendKeys(keysToSend);
    }

    public void clear() {
        getWrappedElement().clear();
    }

    public String getTagName() {
        return getWrappedElement().getTagName();
    }

    public String getAttribute(String name) {
        return getWrappedElement().getAttribute(name);
    }

    public boolean isSelected() {
        return getWrappedElement().isSelected();
    }

    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    public String getText() {
        return getWrappedElement().getText();
    }

    public List<WebElement> findElements(By by) {
        return getWrappedElement().findElements(by);
    }

    public WebElement findElement(By by) {
        return getWrappedElement().findElement(by);
    }

    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    public Point getLocation() {
        return getWrappedElement().getLocation();
    }

    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }

    public String getCssValue(String propertyName) {
        return getWrappedElement().getCssValue(propertyName);
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(target);
    }
}
