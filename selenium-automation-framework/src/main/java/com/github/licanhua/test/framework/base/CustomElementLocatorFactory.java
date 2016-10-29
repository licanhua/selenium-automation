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

import com.github.licanhua.test.framework.annotation.RelativeElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Canhua Li
 */
public class CustomElementLocatorFactory implements ElementLocatorFactory {
    private static Logger logger = Logger.getLogger(CustomElementLocatorFactory.class.getName());

    private Element parent;

    public CustomElementLocatorFactory(Element parent) {
        this.parent = parent;
    }

    private ElementLocator createLocator(SearchContext searchContext, Field field) {
        ElementContext context = parent.getElementContext();
        if (context.isAjax()) {
            logger.debug("AjaxElementLocator is created for " + field.getName()
                    + " with AjaxTimeoutInSeconds setting: " + context.getAjaxTimeoutInSeconds());
            return new AjaxElementLocator(searchContext, field, context.getAjaxTimeoutInSeconds());
        } else {
            logger.debug("DefaultElementLocator is created for " + field.getName());
            return new DefaultElementLocator(searchContext, field);
        }
    }

    private boolean isCustomElement(Class c) {
        return CustomElement.class.isAssignableFrom(c);
    }

    public ElementLocator createLocator(Field field) {
        logger.debug("create ElementLocator for " + field.getName() + ", and it's type is " + field.getType().getName());

        // For all CustomElement, searchContext will be the parent itself.
        SearchContext searchContext = parent.getElementContext().getWebDriver();

        // Only Subclass like Container from CustomElement can act as parent searchContext.
        // All elements in a Page, WebDriver will be it's parent, and act as the searchContext.
        // only RelativeElement annotated field will change search context.
        if (isCustomElement(parent.getClass()) && isRelativeElement(field)) {
            searchContext = (CustomElement) parent;
            logger.info("RelativeElement is found and " + field.getName() + " search context has changed to " + searchContext.getClass().getName());
        }

        ElementLocator locator = createLocator((SearchContext) searchContext, field);
        logger.debug("locator created: " + locator);
        return locator;
    }

    private boolean isRelativeElement(Field field) {
        return field.getAnnotation(RelativeElement.class) != null;
    }
}
