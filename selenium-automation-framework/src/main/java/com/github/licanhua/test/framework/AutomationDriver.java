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

package com.github.licanhua.test.framework;

import com.github.licanhua.test.framework.base.EnvironmentContext;
import com.github.licanhua.test.framework.config.Configuration;
import com.github.licanhua.test.framework.config.ConfigurationService;
import com.github.licanhua.test.framework.config.TypeSafeConfigurationService;
import com.github.licanhua.test.framework.base.WebDriverContext;
import com.github.licanhua.test.framework.util.PageHelper;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;

import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Canhua Li
 */
public final class AutomationDriver extends TestWatcher {
    private static final Logger logger = Logger.getLogger(AutomationDriver.class.getName());

    private ConfigurationService configurationService;
    private WebDriverProvider webDriverProvider;

    private EnvironmentContext environmentContext;

    public AutomationDriver() {
        this(new TypeSafeConfigurationService());
    }

    public EnvironmentContext getEnvironmentContext() {
        return environmentContext;
    }

    public void setEnvironmentContext(EnvironmentContext environmentContext) {
        this.environmentContext = environmentContext;
    }

    public AutomationDriver(ConfigurationService configurationService) {
        this(configurationService, new DefaultWebDriverProvider());
    }

    public AutomationDriver(ConfigurationService configurationService, WebDriverProvider webDriverProvider) {
        this.configurationService = configurationService;
        this.webDriverProvider = webDriverProvider;
    }

    public AutomationDriver(WebDriverProvider webDriverProvider) {
        this(new TypeSafeConfigurationService(), webDriverProvider);
    }

    Configuration  createConfiguration(Description context) {
        Configuration configuration = configurationService.createConfiguration(context);
        checkState(configuration != null, "fail to create configuration from context");
        return configuration;
    }

    private static void navigateTo(WebDriverContext webDriverContext, Configuration configuration) {
        String startPage = configuration.getString(Const.START_PAGE);
        if (Strings.isNullOrEmpty(startPage)) {
            logger.info("No startPage found in configuration");
        } else {
            logger.info("Auto navigate to startPage: " + startPage);
            webDriverContext.getWebDriver().navigate().to(startPage);
            PageHelper.waitForDocumentReadyState(webDriverContext);
            webDriverContext.getWebDriverProvider().takesScreenshot(webDriverContext);
        }
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);

        String testName;
        if(description.isTest()) {
            testName = description.getTestClass().getName() + "." + description.getMethodName();
        }
        else {
            testName = description.getTestClass().getName();
        }

        logger.info("Test starting: " + testName);

        Configuration configuration = createConfiguration(description);
        WebDriverContext webDriverContext = createWebDriver(testName, configuration);

        environmentContext = new EnvironmentContext.EnvironmentContextBuilder()
                .withWebDriverContext(webDriverContext).withConfiguration(configuration).build();
        Global.setEnviromentContext(environmentContext);

        // navigate to startPage
        navigateTo(webDriverContext, configuration);
    }

    public WebDriver getWebDriver() {
        return environmentContext.getWebDriverContext().getWebDriver();
    }

    public Configuration getConfiguration() {
        return environmentContext.getConfiguration();
    }

    private WebDriverContext createWebDriver(String context, Configuration configuration) {
        WebDriverContext  webDriverContext = webDriverProvider.createWebDriver(context, configuration);
        checkState(webDriverContext != null, "fail to create webDriverContext");
        return webDriverContext;
    }

    @Override
    protected void finished(Description description) {
        try {
            // TBD. Here we only take snapshot for one WebDriver
            webDriverProvider.takesScreenshot(Global.getWebDriverContext());
            super.finished(description);
        } catch (Exception e) {
            Throwables.propagate(e);
        } finally {
            WebDriver webDriver = getWebDriver();

            if (webDriver != null) {
                webDriver.quit();
            }
        }
        logger.info("Test finished: " + description.getDisplayName());
    }
}
