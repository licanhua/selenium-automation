package com.github.licanhua.test.framework.util;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Canhua Li
 */
public class ConfigurationHelper {

    private static Logger logger = Logger.getLogger(ConfigurationHelper.class.getName());
    /**
     * Looks up resources named 'name' in the classpath and in the class package.
     * resource in classpath overwrites resource in class package
     * The name is assumed to be absolute and use "/"  for package segment separation with an
     * optional leading "/" . Thus, the following names refer to the same resource:
     *
     * /config/automation.properties
     * config/automation.properties
     *
     */

    public static Properties load(String name) {
        logger.info("load properties for " + name);

        if (name.startsWith("/")) {
            name = name.substring(1);
        }

        Properties r = new Properties();
        Properties classpathResource = new Properties();

        //
        logger.debug("  Load property from class");
        try {
            InputStream inputStream = ConfigurationHelper.class.getClass().getResourceAsStream("/"+name);
            if (inputStream != null)
                r.load(inputStream);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        logger.debug(r.toString());

        logger.debug("  Load property from classpath");
        try {
            InputStream inputStream =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
            if (inputStream != null)
                classpathResource.load(inputStream);
        } catch (Exception e) {
            logger.debug(e.toString());
        }

        logger.debug(classpathResource.toString());

        // overwrite with classpath property file
        for (Object key: classpathResource.keySet()) {
            r.put(key, classpathResource.get(key));
        }

        logger.debug("  Load properties from environment");
        // overwrite with  environment
        Map map = System.getenv();
        for (Object key: r.keySet()) {
            if (map.get(key) != null)
                r.put(key, map.get(key));
        }
        // overwrite
        return r;
    }
}
