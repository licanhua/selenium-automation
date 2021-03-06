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

import org.apache.log4j.Logger;
import org.junit.runner.Description;

/**
 * @author Canhua Li
 */
public abstract class AbstractConfigurationService  implements ConfigurationService{

    private static final Logger logger = Logger.getLogger(AbstractConfigurationService.class.getName());

    Configuration configuration;

    public Configuration createConfiguration(Description testDescription) {
        String context = testDescription.getTestClass().getCanonicalName();
        logger.info("create configuration from context: " + context);
        configuration = createConfigurationFromDescription(testDescription);

        logger.info("configuration: " + context + " is created");
        return configuration;
    }

    protected abstract Configuration createConfigurationFromDescription(Description description);
}
