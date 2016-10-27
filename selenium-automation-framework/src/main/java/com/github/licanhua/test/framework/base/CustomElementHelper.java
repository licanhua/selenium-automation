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

import com.google.common.base.Predicates;
import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author Canhua Li
 */
public class CustomElementHelper {
    private static Logger logger = Logger.getLogger(CustomElementHelper.class.getName());

    static Class<?> getElementTypeFromListField(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return null;
        }

        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }

        return (Class<?>)((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    static boolean isDecoratableElement(Class<?> clazz) {
        return CustomElement.class.isAssignableFrom(clazz);
    }

    static boolean isDecoratableElement(Field field) {
        return isDecoratableElement(field.getType());
    }
    static boolean isDecoratableElementList(Field field) {
        if (field.getAnnotation(FindBy.class) == null &&
                field.getAnnotation(FindBys.class) == null &&
                field.getAnnotation(FindAll.class) == null) {
            return false;
        }

        Class clazz = getElementTypeFromListField(field);
        if (clazz == null || !isDecoratableElement(clazz))
                return false;

        return true;
    }

    private static boolean isRootCauseOfException(Throwable e, Class clazz) {
        return FluentIterable.from(Throwables.getCausalChain(e))
                .filter(Predicates.instanceOf(clazz))
                .first()
                .isPresent();
    }
    // instantiate the custom element
    static Object  instantiateCustomElement(final WebElement element, final Class<?> clazz, final  Element parent) {
        CustomElement customElement = null;

        // create a instance for subclass of CustomElement
        try {
            logger.info("create a instance for " + clazz.getName());
            customElement= (CustomElement) clazz.newInstance();
        }   catch (Exception e) {
            throw Throwables.propagate(e);
        }

        try {
            logger.debug(clazz.getName() + ".setWrappedElement(" + element + ")");
            customElement.setWrappedElement(element);

            logger.info(clazz.getName() + ".initElements(" + parent.getClass().getName() + ")");
            customElement.initElements(parent);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }


        return customElement;
    }
}
