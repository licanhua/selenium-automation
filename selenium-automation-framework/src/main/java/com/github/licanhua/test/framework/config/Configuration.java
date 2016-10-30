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

import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

/**
 * @author Canhua Li
 */
public interface Configuration {
    String getString(String key);
    String getString(String key, String defaultValue);

    long getLong(String key);
    long getLong(String key, long defaultValue);

    boolean getBoolean(String key);
    boolean getBoolean(String key, boolean defaultValue);

    int getInt(String key);
    int getInt(String key, int defaultValue);

    double getDouble(String key);
    double getDouble(String key, double defaultValue);

    float getFloat(String key);
    float getFloat(String key, float defaultValue);

    List<String> getStringList(String key);
    List<String> getStringList(String key, List<String> defaultValue);

    Object getObject(String key);
    Object getObject(String key, Object defaultValue);

    List<Object> getObjectList(String key);
    List<Object> getObjectList(String key, List<Object> defaultValue);

    DesiredCapabilities getDesiredCapabilities(String browserName);
}
