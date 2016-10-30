package com.github.licanhua.test.framework.config;

import com.github.licanhua.test.framework.Const;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;

import static com.github.licanhua.test.framework.Const.DEFAULT_TARGET_ENVIRONMENT;
import static com.github.licanhua.test.framework.Const.TARGET_ENVIRONMENT;

/**
 * @author Canhua Li
 */
class ConfigurationProxy implements Configuration {
    private static final Logger logger = Logger.getLogger(ConfigurationProxy.class.getName());
    private Description description;
    private Configuration config;
    private ConfigurationSearchPathsResolver resolver;

    private List<String> generateSearchPaths(String key) {
        String targetEnv = config.getString(Const.TARGET_ENVIRONMENT, Const.DEFAULT_TARGET_ENVIRONMENT);
        String testName = description.getMethodName();
        return resolver.generateSearchPaths(targetEnv, testName, key);
    }
    private <T> T get(Function<String, T> function, String originKey) {
            for (String key: generateSearchPaths(originKey)) {
                logger.debug("Looking for " + originKey + " in " + key);
                try {
                    T r = function.apply(key);
                    logger.debug("Looking for key " + originKey + " success with key: " + key + " Value: " + r.toString());
                    return r;
                } catch (ConfigurationException.Missing e) {
                    logger.debug("Didn't find it in " + key);
                } catch (Exception e) {
                    Throwables.propagate(e);
                }
            }
            throw  new ConfigurationException.Missing("Can't find key in " + originKey);
    }

     private <T> T  get(Function<String, T> function, String path, T defaultValue) {
        try {
            return get(function, path);
        } catch (ConfigurationException.Missing e) {
            logger.info("Can't find " + path + " in configuration, will use default value: " + defaultValue);
            return defaultValue;
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        return defaultValue;
    }

    private Function<String, String> stringFunction = new Function<String, String>() {
        public String apply(String input) {
            return config.getString(input);
        }
    };

    private Function<String, List<String>> stringListFunction = new Function<String, List<String>>() {
        public List<String> apply(String input) {
            return config.getStringList(input);
        }
    };

    private Function<String, Integer> intFunction = new Function<String, Integer>() {
        public Integer apply(String input) {
            return config.getInt(input);
        }
    };

    private Function<String, Long> longFunction = new Function<String, Long>() {
        public Long apply(String input) {
            return config.getLong(input);
        }
    };

    private Function<String, Boolean> booleanFunction = new Function<String, Boolean>() {
        public Boolean apply(String input) {
            return config.getBoolean(input);
        }
    };


    private Function<String, Float> floatFunction = new Function<String, Float>() {
        public Float apply(String input) {
            return config.getFloat(input);
        }
    };

    private Function<String, Double> doubleFunction = new Function<String, Double>() {
        public Double apply(String input) {
            return config.getDouble(input);
        }
    };

    private Function<String, Object> objectFunction = new Function<String, Object>() {
        public Object apply(String input) {
            return config.getObject(input);
        }
    };

    private Function<String, List<Object>> objectListFunction = new Function<String, List<Object>>() {
        public List<Object> apply(String input) {
            return config.getObjectList(input);
        }
    };


    ConfigurationProxy(Description description, Configuration config) {
        this.description = description;
        this.config = config;
        resolver = new ConfigurationSearchPathsResolver();
    }

    public String getString(String key) {
        return get(stringFunction, key);
    }

    public String getString(String key, String defaultValue) {
        return get(stringFunction, key, defaultValue);
    }

    public long getLong(String key) {
        return get(longFunction, key);
    }

    public long getLong(String key, long defaultValue) {
        return get(longFunction, key, defaultValue);
    }

    public int getInt(String key) {
        return get(intFunction, key);
    }

    public int getInt(String key, int defaultValue) {
        return get(intFunction, key, defaultValue);
    }

    public List<Object> getObjectList(String key) {
        return get(objectListFunction, key);
    }

    public List<Object> getObjectList(String key, List<Object> defaultValue) {
        return get(objectListFunction, key, defaultValue);
    }


    public Object getObject(String key) {
        return get(objectFunction, key);
    }

    public Object getObject(String key, Object defaultValue) {
        return get(objectFunction, key, defaultValue);
    }

    public DesiredCapabilities getDesiredCapabilities(String browserName) {
        return config.getDesiredCapabilities(browserName);
    }


    public double getDouble(String key) {
        return get(doubleFunction, key);
    }


    public double getDouble(String key, double defaultValue) {
        return get(doubleFunction, key, defaultValue);
    }


    public float getFloat(String key) {
        return get(floatFunction, key);
    }

    public float getFloat(String key, float defaultValue) {
        return get(floatFunction, key, defaultValue);
    }

    public List<String> getStringList(String key) {
        return get(stringListFunction, key);
    }

    public List<String> getStringList(String key, List<String> defaultValue) {
        return get(stringListFunction, key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return get(booleanFunction, key, defaultValue);
    }


    public boolean getBoolean(String key) {
        return get(booleanFunction, key);
    }
}
