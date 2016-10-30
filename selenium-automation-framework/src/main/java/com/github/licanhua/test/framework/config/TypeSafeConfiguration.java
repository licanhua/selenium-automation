package com.github.licanhua.test.framework.config;

import com.github.licanhua.test.framework.Const;
import com.google.common.base.Preconditions;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;
import org.apache.log4j.Logger;
import org.junit.runner.Description;

import java.util.List;

/**
 * @author Canhua Li
 */
class TypeSafeConfiguration extends AbstractConfiguration {
    private Description testDescription;

    private static final Logger logger = Logger.getLogger(TypeSafeConfiguration.class.getName());
    private static ConfigParseOptions parseOptions = ConfigParseOptions.defaults();
    private static ConfigResolveOptions resolveOptions = ConfigResolveOptions.defaults()
            .setUseSystemEnvironment(false);

    private Config config;
    private static Config createConfig(String prefix, String next, Config config) {
        logger.debug("Load config(.properties, .json and .config): " + prefix + next);
        if (config == null)
            return ConfigFactory.load(prefix + next);
        return ConfigFactory.load(prefix + next, parseOptions, resolveOptions).withFallback(config);
    }

    private static Config createConfig(String context) {
        String path = Const.CONF_DIR;
        String[] names = context.split("\\.");

        Preconditions.checkArgument(names.length>0);


        Config config = createConfig(path, "automation", null);
        config = createConfig(path, names[0], config);

        for (int i=1; i<names.length; i++) {
            path += names[i-1]+".";
            config = createConfig(path, names[i], config);
        }
        return ConfigFactory.systemProperties().withFallback(config);

    }

    @Override
    public String getString(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.strReader(config), key);
    }

    @Override
    public long getLong(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.longReader(config), key);
    }

    @Override
    public boolean getBoolean(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.booleanReader(config), key);
    }

    @Override
    public int getInt(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.intReader(config), key);
    }


    @Override
    public List<String> getStringList(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.stringListReader(config), key);
    }

    @Override
    public double getDouble(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.doubleReader(config), key);
    }


    @Override
    public List<Object> getObjectList(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.objectListReader(config), key);
    }

    @Override
    public Object getObject(String key) {
        return TypeSafeHelper.get(config, TypeSafeHelper.objectReader(config), key);
    }

    TypeSafeConfiguration(Description testDescription) {
        logger.info("Create TypeSafeConfiguration for " + testDescription.getDisplayName());
        this.config = createConfig(testDescription.getTestClass().getCanonicalName());
        this.testDescription = testDescription;
    }

}
