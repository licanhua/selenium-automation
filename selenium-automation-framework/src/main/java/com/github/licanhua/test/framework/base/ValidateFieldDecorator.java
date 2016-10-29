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

import com.github.licanhua.test.framework.annotation.OptionalElement;
import com.github.licanhua.test.framework.util.PageHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Canhua Li
 */
public class ValidateFieldDecorator implements FieldDecorator {
    private Element parent;
    private static final Logger logger = Logger.getLogger(ValidateFieldDecorator.class.getName());

    static class ValidateException extends RuntimeException {
        public ValidateException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    private FieldDecorator fieldDecorator;
    boolean validateField;

    public ValidateFieldDecorator(FieldDecorator fieldDecorator, Element parent, boolean validateField) {
        checkNotNull(fieldDecorator);

        this.fieldDecorator = fieldDecorator;
        this.validateField = validateField;
        this.parent = parent;
    }

    private boolean isOptionalElement(Field field) {
        return field.getAnnotation(OptionalElement.class) != null;
    }

    private void validateElement(final WebElement element, String name) {
        logger.info("AutoValidate element is present of " + name);
        if (CustomElement.class.isAssignableFrom(element.getClass())) {
            ((CustomElement) element).setNextHandler(new ValidationHandler() {
                public void setNextHandler(ValidationHandler handler) {}

                public void validate() {
                    PageHelper.waitForElementToBePresent(element, parent);
                }
            });
            ((CustomElement) element).validate();
        } else {
            PageHelper.waitForElementToBePresent(element, parent);
        }
    }

    public Object decorate(ClassLoader loader, Field field) {
        Object object = fieldDecorator.decorate(loader, field);

        logger.debug("decorate complete for " + field.getName());
        if (object == null || !validateField || isOptionalElement(field))
            return object;

        logger.debug("Try to validate element " + field.getClass() + "." + field.getName());

        if (WebElement.class.isAssignableFrom(field.getType())) {
            WebElement webElement = (WebElement) object;
            validateElement(webElement, field.getName());
        } else if (List.class.isAssignableFrom(field.getType())) {
            List<WebElement> list = (List<WebElement>) object;
            if (list.size() == 0) {
                throw new RuntimeException("List size is 0. Can't find any element for " + field.getName());
            }
        } else {
            throw new RuntimeException("It should not happen");
        }

        logger.debug("Complete the validation for element " + field.getName());
        return object;
    }
}
