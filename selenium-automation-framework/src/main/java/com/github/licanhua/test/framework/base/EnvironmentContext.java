package com.github.licanhua.test.framework.base;

import com.github.licanhua.test.framework.config.Configuration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Canhua Li
 */
public class EnvironmentContext {
    private EnvironmentContext(){}

    private WebDriverContext webDriverContext;
    private Configuration configuration;

    public WebDriverContext getWebDriverContext() {
        return webDriverContext;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static class EnvironmentContextBuilder {
        EnvironmentContext environmentContext = new EnvironmentContext();

        public EnvironmentContextBuilder withConfiguration(Configuration configuration) {
            environmentContext.configuration = configuration;
            return this;
        }

        public EnvironmentContextBuilder withWebDriverContext(WebDriverContext webDriverContext) {
            environmentContext.webDriverContext = webDriverContext;
            return this;
        }
        public EnvironmentContext build() {
            checkNotNull(environmentContext.configuration);
            checkNotNull(environmentContext.webDriverContext);
            return environmentContext;
        }
    }
}
