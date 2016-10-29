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

import com.github.licanhua.test.framework.Global;
import com.github.licanhua.test.framework.annotation.AjaxEnabled;
import com.github.licanhua.test.framework.annotation.AutoValidation;
import com.github.licanhua.test.framework.config.Configuration;
import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriver;

import static com.github.licanhua.test.framework.util.AnnotationHelper.getAnnotationDefault;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Central place to keep all the information for a Element  {@link Element}.
 * @author Canhua Li
 */
public final class ElementContext {
    private boolean ajax;
    private boolean validate;
    EnvironmentContext environmentContext;


    private ElementContext() {}

    public boolean isAjax() {
        return ajax;
    }

    public boolean isValidate() {
        return validate;
    }

    public EnvironmentContext getEnvironmentContext() {
        return environmentContext;
    }

    public WebDriver getWebDriver() {
        return environmentContext.getWebDriverContext().getWebDriver();
    }

    public int getWaitDurationInSeconds() {
        return environmentContext.getWebDriverContext().getWaitDurationInSeconds();
    }

    public int getAjaxTimeoutInSeconds() {
        return environmentContext.getWebDriverContext().getAjaxTimeoutInSeconds();
    }
    public Configuration getConfiguration() {
        return environmentContext.getConfiguration();
    }

    public WebDriverContext getWebDriverContext() { return environmentContext.getWebDriverContext();}

    public static ElementContext createDummyContext(EnvironmentContext environmentContext) {
        return new ElementContextBuilder().withAjax(true).withValidation(true)
                .withEnviromentContext(environmentContext).build();
    }
    public static ElementContext createDummyContext() {
        return createDummyContext(Global.getEnvironmentContext());
    }

    public static ElementContext createFromParentContext(Element parent, Class<?> clazz) {
        checkNotNull(parent);
        checkNotNull(clazz);

        boolean validate = true;
        boolean ajax = true;
        try {
            validate = getAnnotationDefault(AutoValidation.class, "value");
            ajax = getAnnotationDefault(AjaxEnabled.class, "value");
        } catch (Exception e){
            throw Throwables.propagate(e);
        }

        validate = clazz.getAnnotation(
                AutoValidation.class) != null? clazz.getAnnotation(AutoValidation.class).value(): validate;
        ajax = clazz.getAnnotation(
                AjaxEnabled.class) != null? clazz.getAnnotation(AjaxEnabled.class).value(): ajax;

        // same enviroment Context with parent
        return new ElementContextBuilder().withAjax(ajax).withValidation(validate)
                .withEnviromentContext(parent.getElementContext().getEnvironmentContext()).build();
    }

    public static class ElementContextBuilder {
        ElementContext context = new ElementContext();
        public ElementContextBuilder withAjax(boolean ajaxEnabled) {
            context.ajax = ajaxEnabled;
            return this;
        }
        public ElementContextBuilder withValidation(boolean needValidation) {
            context.validate = needValidation;
            return this;
        }
        public ElementContextBuilder withEnviromentContext(EnvironmentContext environmentContext) {
            context.environmentContext = environmentContext;
            return this;
        }

        public ElementContext build(){ return context;}
    }

}
