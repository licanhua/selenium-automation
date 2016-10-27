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
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import org.apache.log4j.Logger;


/**
 * @author Canhua Li
 */
public final class DefaultWebDriverProvider extends AbstractWebDriverProvider {

    private static final Logger logger = Logger.getLogger(DefaultWebDriverProvider.class.getName());

    @Override
    public WebDriver createWebDriver(AutomationConfig automationConfig, DesiredCapabilities caps) {
        String url = automationConfig.getRemoteWebDriverAddress();

        WebDriver webDriver = null;
        if (Strings.isNullOrEmpty(url)) {
            webDriver = createLocalWebDriver(automationConfig, caps);
        }

        if (webDriver == null)
        {
            webDriver = remoteWebDriverManager.createWebDriver(automationConfig, caps);
        }
        return webDriver;
    }

    // getBrowserName has the same code with consoleIconName in org.openqa.grid.web.utils.BrowserNameUtils.java
    private static String getBrowserName(DesiredCapabilities cap) {
        String browserString = cap.getBrowserName();
        if (browserString == null || "".equals(browserString)) {
            return "missingBrowserName";
        }

        String ret = browserString;

        // Map browser environments to icon names.
        if (browserString.contains("iexplore") || browserString.startsWith("*iehta")) {
            ret = BrowserType.IE;
        } else if (browserString.contains("firefox") || browserString.startsWith("*chrome")) {
            if (cap.getVersion() != null && cap.getVersion().toLowerCase().equals("beta") ||
                    cap.getBrowserName().toLowerCase().contains("beta")) {
                ret = "firefoxbeta";
            } else if (cap.getVersion() != null && cap.getVersion().toLowerCase().equals("aurora") ||
                    cap.getBrowserName().toLowerCase().contains("aurora")) {
                ret = "aurora";
            } else if (cap.getVersion() != null && cap.getVersion().toLowerCase().equals("nightly") ||
                    cap.getBrowserName().toLowerCase().contains("nightly")) {
                ret = "nightly";
            } else {
                ret = BrowserType.FIREFOX;
            }

        } else if (browserString.startsWith("*safari")) {
            ret = BrowserType.SAFARI;
        } else if (browserString.startsWith("*googlechrome")) {
            ret = BrowserType.CHROME;
        } else if (browserString.startsWith("opera")) {
            ret = BrowserType.OPERA;
        } else if (browserString.toLowerCase().contains("edge")) {
            ret = BrowserType.EDGE;
        }

        return ret; //ret.replace(" ", "_");
    }


    public static HashMap<String, AbstractWebDriverProvider> map = Maps.newHashMap();

    final static InternetExplorerDriverProvider ieDriverManager = new InternetExplorerDriverProvider();
    final static RemoteWebDriverProvider remoteWebDriverManager = new RemoteWebDriverProvider();
    final static FirefoxWebDriverProvider firefoxWebDriverManager = new FirefoxWebDriverProvider();
    final static ChromeWebDriverProvider chromeWebDriverManager = new ChromeWebDriverProvider();

    static {
        map.put(BrowserType.FIREFOX, firefoxWebDriverManager);
        map.put(BrowserType.IE, ieDriverManager);
        map.put(BrowserType.EDGE, ieDriverManager);
        map.put(BrowserType.CHROME, chromeWebDriverManager);
    }

    private WebDriver createLocalWebDriver(AutomationConfig automationConfig, DesiredCapabilities desiredCapabilities) {
        String browserName = getBrowserName(desiredCapabilities);

        AbstractWebDriverProvider webDriverProvider = map.get(browserName);
        if (webDriverProvider == null) {
            logger.info("can't find local WebDriver match with " + browserName
                    + "in local provides: " + map.keySet().toString());
            return null;
        }

        logger.info("Try to create local webdriver browser for " + browserName);

        return webDriverProvider.createWebDriver(automationConfig, desiredCapabilities);
    }

    public static class FirefoxWebDriverProvider extends AbstractWebDriverProvider {
        public WebDriver createWebDriver(AutomationConfig automationConfig, DesiredCapabilities desiredCapabilities) {
            return new FirefoxDriver(desiredCapabilities);
        }
    }

    public static class RemoteWebDriverProvider extends AbstractWebDriverProvider {

        public WebDriver createWebDriver(AutomationConfig automationConfig, DesiredCapabilities desiredCapabilities) {
            String remoteAddress = automationConfig.getRemoteWebDriverAddress();

            if (Strings.isNullOrEmpty(remoteAddress)) {
                logger.info("creating RemoteWebDriver with local url");
                return new RemoteWebDriver(desiredCapabilities);
            } else {
                logger.info("creating RemoteWebDriver with remoteAddress " + remoteAddress);
                URL url = null;
                try {
                     url = new URL(remoteAddress);
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Invalid server URL: " + remoteAddress, e);
                }
                return new RemoteWebDriver(url, desiredCapabilities);
            }
        }
    }

    public static class ChromeWebDriverProvider extends AbstractWebDriverProvider {
        public WebDriver createWebDriver(AutomationConfig automationConfig, DesiredCapabilities desiredCapabilities) {
            logger.info("creating ChromeWebDriver");
            return new ChromeDriver(desiredCapabilities);
        }
    }

    public static class InternetExplorerDriverProvider extends AbstractWebDriverProvider {
        public WebDriver createWebDriver(AutomationConfig automationConfig, DesiredCapabilities desiredCapabilities) {
            logger.info("creating InternetExplorerDriver");
            return new InternetExplorerDriver(desiredCapabilities);
        }
    }
}
