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

package com.github.licanhua.test.framework.config;

import com.github.licanhua.test.framework.util.ConfigurationHelper;

import java.util.Properties;

/**
 * @author Canhua Li
 */
public class AutomationConfig {
    String browserName;
    int waitDurationInSeconds;
    String remoteWebDriverAddress;
    boolean autoSnapshot;

    public String getBrowserName() {
        return browserName;
    }

    private AutomationConfig(String browserName, int waitDurationInSeconds, String remoteWebDriverAddress, boolean autoSnapshot) {
        this.browserName = browserName;
        this.waitDurationInSeconds = waitDurationInSeconds;
        this.remoteWebDriverAddress = remoteWebDriverAddress;
        this.autoSnapshot = autoSnapshot;
    }

    public int getWaitDurationInSeconds() {
        return waitDurationInSeconds;
    }

    public boolean isAutoSnapshot() {
        return autoSnapshot;
    }

    public void setAutoSnapshot(boolean autoSnapshot) {
        this.autoSnapshot = autoSnapshot;
    }

    public String getRemoteWebDriverAddress() {
        return remoteWebDriverAddress;
    }

    public final static String AUTOMATION_INI = "config/automation.ini";

    // default values
    public final static String DEDAULT_BROWSER_NAME = "firefox";
    public final static String DEFAULT_WAIT_DURATION_IN_SECONDS = "10";
    public final static String DEFAULT_REMOTE_WEB_DRIVER_ADDRESS = "";
    public final static String DEFAULT_AUTOSNAPSHOT = "false";
    // keys
    public final static String BROWSER_NAME = "browserName";
    public final static String WAIT_DURATION_IN_SECONDS = "waitDurationInSeconds";
    public final static String REMOTE_WEB_DRIVER_ADDRESS = "remoteWebDriverAddress";
    public final static String AUTO_SNAPSHOT = "autoSnapshot";

    public static AutomationConfig getAutomationConfig() {
        Properties properties = ConfigurationHelper.load(AUTOMATION_INI);
        String browserName = properties.getProperty(BROWSER_NAME, DEDAULT_BROWSER_NAME);
        int waitDurationInSeconds = Integer.parseInt(properties.getProperty(WAIT_DURATION_IN_SECONDS, DEFAULT_WAIT_DURATION_IN_SECONDS));
        String remoteWebDriverAddress = properties.getProperty(REMOTE_WEB_DRIVER_ADDRESS, DEFAULT_REMOTE_WEB_DRIVER_ADDRESS);
        boolean autoSnapshot = Boolean.valueOf(properties.getProperty(AUTO_SNAPSHOT, DEFAULT_AUTOSNAPSHOT));

        return new AutomationConfig(browserName, waitDurationInSeconds, remoteWebDriverAddress, autoSnapshot);
    }
}
