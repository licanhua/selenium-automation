package com.github.licanhua.test.framework.config;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Canhua Li
 */
class TypeSafeHelper {
    private static final Logger logger = Logger.getLogger(TypeSafeHelper.class.getName());

    static Function<String, String> strReader(final Config config) {
        return new Function<String, String>() {
            public String apply(String input) {
                return config.getString(input);
            }
        };
    }

    static Function<String, Integer> intReader(final Config config) {
        return new Function<String, Integer>() {
            public Integer apply(String input) {
                return config.getInt(input);
            }
        };
    }

    static Function<String, Long> longReader(final Config config) {
        return new Function<String, Long>() {
            public Long apply(String input) {
                return config.getLong(input);
            }
        };
    }

    static Function<String, Double> doubleReader(final Config config) {
        return new Function<String, Double>() {
            public Double apply(String input) {
                return config.getDouble(input);
            }
        };
    }

    static Function<String, Boolean> booleanReader(final Config config) {
        return new Function<String, Boolean>() {
            public Boolean apply(String input) {
                return config.getBoolean(input);
            }
        };
    }

    static Function<String, List<String>> stringListReader(final Config config) {
        return new Function<String, List<String>>() {
            public List<String> apply(String input) {
                return config.getStringList(input);
            }
        };
    }

    static Function<String, Object> objectReader(final Config config) {
        return new Function<String, Object>() {
            public Object apply(String input) {
                return config.getAnyRef(input);
            }
        };
    }

    @SuppressWarnings("unchecked")
    static Function<String, List<Object>> objectListReader(final Config config) {
        return new Function<String, List<Object>>() {
            public List<Object> apply(String input) {
                return (List <Object>) config.getAnyRefList(input);
            }
        };
    }

    static <T> T get(Config config, Function<String, T> function, String path) {
        try {
            if (config.hasPath(path))
                return function.apply(path);
            else
                throw new ConfigurationException.Missing("Can't find " + path);
        } catch (ConfigException.Missing e) {
            throw new ConfigurationException.Missing(e.getMessage());
        } catch (ConfigException.WrongType e) {
            throw new ConfigurationException.ConvertFail(e.getMessage());
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        throw new ConfigurationException.Missing("Can't find" + path);
    }

    static <T> T  get(Config config, Function<String, T> function, String path, T defaultValue) {
        try {
            return get(config, function, path);
        } catch (ConfigurationException.Missing e) {
            logger.info("Can't find " + path + " in configuration, will use default value");
            return defaultValue;
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        return defaultValue;
    }
}
