/*
 *
 *  Copyright (C) 2016. The Selenium Automation Framework Authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing permissions and limitations under
 *  the License.
 *
 *  Created by canhua li (licanhua@live.com)
 *
 * /
 */

package com.github.licanhua.test.framework.base;

import org.openqa.selenium.WebElement;

/**
 * @author Canhua Li
 */
public interface Navigation {
    /**
     * Navigate to another Page, a page class should be provided.
     * @param pageClass a instance of pageClass will be created.
     * @return a instance of T
     * @see com.github.licanhua.test.framework.util.PageHelper#toPage(Class, ElementContext)
     */
    <T> T toPage(Class<T> pageClass);
    /**
     * First click on a webElement, then
     * Navigate to another Page, a page class should be provided.
     * @param pageClass a instance of pageClass will be created.
     * @param webElement to be clicked. it can be WebElement or any subclass of
     *                   WebElement like {@link SelectBox} and {@link CheckBox}
     * @return a instance of T
     * @see com.github.licanhua.test.framework.util.PageHelper#clickAndToPage(WebElement, Class, Element)
     */
    <T> T clickAndToPage(WebElement webElement, Class<T> pageClass);
}
