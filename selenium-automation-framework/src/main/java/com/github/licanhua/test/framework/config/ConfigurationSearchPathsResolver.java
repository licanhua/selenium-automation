/*
 *
 *  * Copyright (C) 2016 The Selenium Automation Framework Authors
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  * in compliance with the License. You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *  *
 *  * Created by canhua li (licanhua@live.com)
 *  *
 *
 */

package com.github.licanhua.test.framework.config;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.junit.runner.Description;

import java.util.List;

import static com.github.licanhua.test.framework.Const.DEFAULT_TARGET_ENVIRONMENT;
import static com.github.licanhua.test.framework.Const.TARGET_ENVIRONMENT;

/**
 * Any key search will be performed in 4 namespace. Below is the search priority
 * <p>
 * ${testMethodName}.${targetEnvironment}.${key}
 * ${testMethodName}.{key}
 * ${targetEnvironment}.${key}
 * ${key}
 *</p>
 * @author Canhua Li
 */
class ConfigurationSearchPathsResolver {
    private String generateSearchKey(String targetEnv, String testMethodName, String key) {
        StringBuilder sb = new StringBuilder();
        if (!Strings.isNullOrEmpty(testMethodName)) {
            sb.append(testMethodName);
            sb.append(".");
        }
        if (!Strings.isNullOrEmpty(targetEnv)) {
            sb.append(targetEnv);
            sb.append(".");
        }
        sb.append(key);

        return sb.toString();
    }

    private void addToPath(String targetEnv, String testMethodName, String key, List<String> paths) {
        String newKey = generateSearchKey(targetEnv, testMethodName, key);
        if (!paths.contains(newKey)) {
            paths.add(newKey);
        }
    }

    List<String> generateSearchPaths(String targetEnv, String testMethodName, String key) {
        List<String> paths = Lists.newArrayList();

        // name.env.key
        addToPath(targetEnv, testMethodName, key, paths);
        //name.key
        addToPath(null, testMethodName, key, paths);
        //env.key
        addToPath(targetEnv, null, key, paths);
        //key
        paths.add(key);

        return paths;

    }
}
