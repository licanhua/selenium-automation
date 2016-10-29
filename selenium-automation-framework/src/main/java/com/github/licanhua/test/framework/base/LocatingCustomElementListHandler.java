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

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Canhua Li
 */
public class LocatingCustomElementListHandler implements InvocationHandler {
    private static Logger logger = Logger.getLogger(LocatingCustomElementListHandler.class.getName());

    ElementLocator locator;
    Class type;
    Element parent;

    public LocatingCustomElementListHandler(ElementLocator locator, Class type, Element parent) {
        checkNotNull(locator);
        checkNotNull(type);
        checkNotNull(parent);

        this.locator = locator;
        this.type = type;
        this.parent = parent;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.debug("proxy method is called: " + method.getName());
        List<WebElement> elements = locator.findElements();
        List<AbstractElement> wrappers = new ArrayList<AbstractElement>();
        for (WebElement element : elements) {
            try {
                wrappers.add((AbstractElement)CustomElementHelper.createLazyProxyCustomElement(element, type, parent));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return method.invoke(wrappers, args);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
