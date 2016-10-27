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

import com.google.common.base.Converter;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Canhua Li
 */
public class AbstractConfiguration implements Configuration {

    public String getString(String key) {
        throw new ConfigurationException.NotImplement("getString is not be implemented");
    }

    public String getString(String key, String defaultValue) {
        return getAndConvert(String.class, key, defaultValue);
    }

    public long getLong(String key) {
        return getAndConvert(Long.class, key);
    }

    public long getLong(String key, long defaultValue) {
        return getAndConvert(Long.class, key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return getAndConvert(Boolean.class, key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getAndConvert(Boolean.class, key, defaultValue);
    }

    public int getInt(String key) {
        return getAndConvert(Integer.class, key);
    }

    public int getInt(String key, int defaultValue) {
        return getAndConvert(Integer.class, key, defaultValue);
    }

    public List<?> getList(String key) {
        throw new ConfigurationException.NotImplement("getList is not be implemented");
    }

    public List<?> getList(String key, List<?> defaultValue) {
        throw new ConfigurationException.NotImplement("getList is not be implemented");
    }

    public double getDouble(String key) {
        return getAndConvert(Double.class, key);
    }

    public double getDouble(String key, double defaultValue) {
        return getAndConvert(Double.class, key, defaultValue);
    }

    public float getFloat(String key) {
        return getAndConvert(Float.class, key);
    }

    public float getFloat(String key, float defaultValue) {
        return getAndConvert(Float.class, key, defaultValue);
    }

    private final static Converter<String, String> stringConvert = new Converter<String, String>() {
        protected String doForward(String s) {
            return s;
        }

        protected String doBackward(String s) {
            return s;
        }
    };

    private final static Converter<String, Boolean> booleanConvert = new Converter<String, Boolean>() {
        protected Boolean doForward(String s) {
            return Boolean.parseBoolean(s);
        }

        protected String doBackward(Boolean b) {
            return String.valueOf(b);
        }
    };



    static final ImmutableMap<Class<?>, Converter<String, ?>> map =
            new ImmutableMap.Builder<Class<?>, Converter<String, ?>>()
                    .put(Integer.class, Ints.stringConverter())
                    .put(Long.class, Longs.stringConverter())
                    .put(Double.class, Doubles.stringConverter())
                    .put(Float.class, Floats.stringConverter())
                    .put(String.class, stringConvert)
                    .put(Boolean.class, booleanConvert)
                    .build();

    private <T> T convert(Class<T> type, String value) {
        T r = null;
        try {
            Converter<String, T> converter = (Converter<String, T>) map.get(type);
            checkState(converter != null, "Can't find converter for class: " + type.getName());
            r = converter.convert(value);
        }
        catch (NumberFormatException e) {
            throw new ConfigurationException.ConvertFail(e.getMessage());
        }
        catch (Throwable t) {
            Throwables.propagate(t);
        }
        return r;
    }

    private <T> T getAndConvert(Class<T> type, String key) {
        checkArgument(!Strings.isNullOrEmpty(key));

        String value = getString(key);
        return convert(type, value);
    }

    private <T> T getAndConvert(Class<T> type, String key, T defaultValue) {
        T r = null;
        try {
            String value = getString(key);
            r = convert(type, value);
        } catch (ConfigurationException.Missing e){
            return defaultValue;
        }
        catch (Throwable t) {
            Throwables.propagate(t);
        }
        return r;
    }
}
