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

/**
 * @author Canhua Li
 */
import com.github.licanhua.test.framework.WebDriverProvider;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.licanhua.test.framework.Const.DEFAULT_MIN_TIME_OUT_IN_SECONDS;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
public class WebDriverContext {
    WebDriver webDriver;
    int waitDurationInSeconds;
    int ajaxTimeoutInSeconds;
    boolean autoSnapshot;
    String timestamp;
    WebDriverProvider webDriverProvider;

    private void initTimestamp() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMddHHmmss");
        timestamp = sdfTime.format(new Date());
    }

    public int getAjaxTimeoutInSeconds() {
        return ajaxTimeoutInSeconds;
    }

    private WebDriverContext() {
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public boolean isAutoSnapshot() {
        return autoSnapshot;
    }

    public int getWaitDurationInSeconds() {
        return waitDurationInSeconds;
    }

    public String getTimestamp(){return timestamp;}

    public WebDriverProvider getWebDriverProvider() {
        return webDriverProvider;
    }

    public static class WebDriverContextBuilder {
        WebDriverContext webDriverContext = new WebDriverContext();
        public WebDriverContextBuilder withWebDriverProvider(WebDriverProvider webDriverProvider) {
            webDriverContext.webDriverProvider = webDriverProvider;
            return this;
        }

        public WebDriverContextBuilder withWaitDurationInSeconds(int waitDurationInSeconds) {
            webDriverContext.waitDurationInSeconds = Math.max(waitDurationInSeconds,DEFAULT_MIN_TIME_OUT_IN_SECONDS);
            return this;
        }

        public WebDriverContextBuilder withAjaxTimeoutInSeconds(int ajaxTimeoutInSeconds) {
            webDriverContext.ajaxTimeoutInSeconds = Math.max(ajaxTimeoutInSeconds,DEFAULT_MIN_TIME_OUT_IN_SECONDS);
            return this;
        }
        public WebDriverContextBuilder withWebDriver(WebDriver webDriver) {
            webDriverContext.webDriver = webDriver;
            return this;
        }

        public WebDriverContextBuilder withAutoSnapshot(boolean autoSnapshot) {
            webDriverContext.autoSnapshot = autoSnapshot;
            return this;
        }

        public WebDriverContext build() {
            checkNotNull(webDriverContext.webDriver);
            checkNotNull(webDriverContext.webDriverProvider);
            webDriverContext.initTimestamp();
            return webDriverContext;
        }
    }
}
