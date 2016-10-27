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
import com.github.licanhua.test.framework.base.WebDriverContext;

import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Canhua Li
 */
public class Global {
    private static final Logger logger = Logger.getLogger(Global.class.getName());

    private Global() {};

    static EnvironmentContext environmentContext = null;


    public static WebDriverContext getWebDriverContext() {
        checkNotNull(environmentContext);
        return environmentContext.getWebDriverContext();
    }

    public static void setEnviromentContext(EnvironmentContext context) {
        if (environmentContext != null) {
           logger.error("Environment context is overwritten!");
        }
        environmentContext = context;
    }

    public static Configuration getConfiguration() {
        return environmentContext.getConfiguration();
    }

    public static EnvironmentContext getEnvironmentContext() {
        checkNotNull(environmentContext);
        return environmentContext;
    }
}
