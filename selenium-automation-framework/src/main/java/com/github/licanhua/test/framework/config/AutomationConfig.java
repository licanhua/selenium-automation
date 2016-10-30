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

import com.github.licanhua.test.framework.Const;

/**
 * @author Canhua Li
 */
public class AutomationConfig {
    private String browserName;
    private int waitDurationInSeconds;
    private int ajaxTimeoutInSeconds;
    private String remoteWebDriverAddress;
    private boolean autoSnapshot;

    public String getBrowserName() {
        return browserName;
    }

    private AutomationConfig(String browserName, int waitDurationInSeconds, int ajaxTimeoutInSeconds,String remoteWebDriverAddress, boolean autoSnapshot) {
        this.browserName = browserName;
        this.waitDurationInSeconds = waitDurationInSeconds;
        this.remoteWebDriverAddress = remoteWebDriverAddress;
        this.autoSnapshot = autoSnapshot;
        this.ajaxTimeoutInSeconds = ajaxTimeoutInSeconds;
    }

    public int getAjaxTimeoutInSeconds() {
        return ajaxTimeoutInSeconds;
    }

    public int getWaitDurationInSeconds() {
        return waitDurationInSeconds;
    }

    public boolean isAutoSnapshot() {
        return autoSnapshot;
    }

    public String getRemoteWebDriverAddress() {
        return remoteWebDriverAddress;
    }

    public static AutomationConfig createAutomationConfig(Configuration configuration) {
        String browserName = configuration.getString(Const.BROWSER_NAME, Const.DEDAULT_BROWSER_NAME);
        int waitDurationInSeconds = configuration.getInt(Const.WAIT_DURATION_IN_SECONDS, Const.DEFAULT_WAIT_DURATION_IN_SECONDS);
        int ajaxTimeoutInSeconds = configuration.getInt(Const.AJAX_TIMEOUT_IN_SECONDS, Const.DEFAULT_AJAX_TIMEOUT_IN_SECONDS);
        String remoteWebDriverAddress = configuration.getString(Const.REMOTE_WEB_DRIVER_ADDRESS, Const.DEFAULT_REMOTE_WEB_DRIVER_ADDRESS);
        boolean autoSnapshot = configuration.getBoolean(Const.AUTO_SNAPSHOT, Const.DEFAULT_AUTO_SNAPSHOT);

        return new AutomationConfig(browserName, waitDurationInSeconds, ajaxTimeoutInSeconds, remoteWebDriverAddress, autoSnapshot);
    }
}
