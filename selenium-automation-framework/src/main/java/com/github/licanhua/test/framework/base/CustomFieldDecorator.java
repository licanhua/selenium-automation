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

import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.*;
import java.util.List;
import org.apache.log4j.Logger;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

/**
 * @author Canhua Li
 */
public class CustomFieldDecorator implements FieldDecorator {
    private static Logger logger = Logger.getLogger(CustomFieldDecorator.class.getName());

    private ElementLocatorFactory factory;
    private DefaultFieldDecorator decorator;
    private Element parent;

    public CustomFieldDecorator(ElementLocatorFactory factory, Element parent) {
        this.factory = factory;
        this.parent = parent;
        decorator = new DefaultFieldDecorator(factory);
    }

    private boolean inSkipList(Class clazz) {
        Class[] skips = {AbstractElement.class, CustomElement.class};
        for (Class c: skips) {
            if (c.equals(clazz))
                return true;
        }
        return false;
    }

    public Object decorate(ClassLoader loader, Field field) {
        logger.debug("decorate for " + field.getDeclaringClass().getName() + "." + field.getName());

        if (inSkipList(field.getDeclaringClass())) {
            logger.debug("  skipped for " + field.getDeclaringClass().getName() + "." + field.getName());
            return null;
        }
        if (!(CustomElementHelper.isDecoratableElement(field)
                || CustomElementHelper.isDecoratableElementList(field))) {
            return decorator.decorate(loader, field);
        }

        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        if (CustomElementHelper.isDecoratableElement(field)) {
            WebElement webElement = proxyForLocator(loader, locator);
            return CustomElementHelper.createLazyProxyCustomElement(webElement, field.getType(), parent);
        } else  {
            return proxyForCustomListLocator(loader, locator, field , parent);
        }
    }



    private WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        logger.debug("proxyForLocator : " + locator);
        InvocationHandler handler = new LocatingElementHandler(locator);

        WebElement proxy;
        proxy = (WebElement) Proxy.newProxyInstance(
                loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);

        logger.debug("proxyForLocator complete : " + locator);
        return proxy;
    }

    private List<?> proxyForCustomListLocator(ClassLoader loader, ElementLocator locator, Field field, Element parent) {
        logger.debug("proxyForCustomListLocator for " + field.getName() + " and its locator is: " + locator);
        Class<?> clazz = CustomElementHelper.getElementTypeFromListField(field);
        InvocationHandler handler = new LocatingCustomElementListHandler(locator, clazz, parent );

        List<?> proxy;
        proxy = (List<?>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);
        logger.debug("proxyForCustomListLocator complete for " + field.getName() + " and its locator is: " + locator);
        return proxy;
    }

}

