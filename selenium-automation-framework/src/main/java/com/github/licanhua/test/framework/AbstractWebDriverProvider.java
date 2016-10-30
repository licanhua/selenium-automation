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

import com.github.licanhua.test.framework.config.AutomationConfig;
import com.github.licanhua.test.framework.base.WebDriverContext;
import com.github.licanhua.test.framework.config.Configuration;
import com.github.licanhua.test.framework.util.ConfigurationHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author Canhua Li
 */
public abstract class AbstractWebDriverProvider implements WebDriverProvider {
    private static String TEST_OUT = "./testOutput/";
    private static String driverTimeStamp;
    private String testName;
    private int snapCount = 0;

    static {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMddHHmmss");
        driverTimeStamp = sdfTime.format(new Date());
    }

    private static final Logger logger = Logger.getLogger(AbstractWebDriverProvider.class.getName());

    public WebDriverContext createWebDriver(String context, Configuration configuration) {
        logger.info("create webDriver for " + context);
        testName = context;

        AutomationConfig automationConfig = AutomationConfig.createAutomationConfig(configuration);

        DesiredCapabilities desiredCapabilities = configuration.getDesiredCapabilities(automationConfig.getBrowserName());
        logger.info("creating WebDriver: " + desiredCapabilities.toString());
        WebDriver webDriver = createWebDriver( automationConfig, desiredCapabilities);
        logger.info("WebDriver is created");

        return new WebDriverContext.WebDriverContextBuilder().withWebDriver(webDriver)
                .withWebDriverProvider(this)
                .withWaitDurationInSeconds(automationConfig.getWaitDurationInSeconds())
                .withAutoSnapshot(automationConfig.isAutoSnapshot())
                .withAjaxTimeoutInSeconds(automationConfig.getAjaxTimeoutInSeconds()).build();
    }

    public void takesScreenshot(WebDriverContext webDriverContext) {
        if (!webDriverContext.isAutoSnapshot()) {
            logger.debug("autoSnapshot is disabled. skip the snapshot");
            return;
        }
        String path = TEST_OUT + driverTimeStamp + "/" + testName + "/" + snapCount++;
        logger.info("Snapshot to : " + path);
        WebDriver webDriver = webDriverContext.getWebDriver();
        File screenshotAs =((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshotAs, new File(path + "-screenshot.png"));
            FileUtils.writeStringToFile(new File(path + "-source.html"),  webDriver.getPageSource(), Charset.defaultCharset(), false);
        } catch (IOException e) {
            logger.error("Fail to save snapshot", e);
        }
    }

    public abstract WebDriver createWebDriver(AutomationConfig automationConfig, DesiredCapabilities desiredCapabilities);


}
