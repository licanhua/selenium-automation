package com.github.licanhua.test.framework.config;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 *
 */
public class AbstractConfigurationTest {
    private AbstractConfiguration keyEqualToValueConfiguration = new AbstractConfiguration(){
        @Override
        public String getString(String key) {
            return key;
        }
    };

    private AbstractConfiguration abstractConfiguration = new AbstractConfiguration();

    private AbstractConfiguration missExceptionConfiguration = new AbstractConfiguration(){
        @Override
        public String getString(String key) {
            throw new ConfigurationException.Missing("");
        }
    };

    @Test(expected=ConfigurationException.Missing.class)
    public void testGetLongMissingException() {
        missExceptionConfiguration.getLong("abc");
    }

    @Test(expected=ConfigurationException.Missing.class)
    public void testGetDoubleMissingException() {
        missExceptionConfiguration.getDouble("0.123F");
    }


    @Test(expected=ConfigurationException.ConvertFail.class)
    public void testGetIntConvertException() {
        keyEqualToValueConfiguration.getInt("abc");
    }

    @Test(expected=ConfigurationException.ConvertFail.class)
    public void testGetFloatConvertException() {
        keyEqualToValueConfiguration.getFloat("0.123L");
    }

    @Test(expected=ConfigurationException.NotImplement.class)
    public void testGetListNotImplementException() {
        abstractConfiguration.getStringList("0.123F");
    }

    @Test
    public void testValueExistsAndValid() {
        assertEquals(keyEqualToValueConfiguration.getString("1"), "1");
        assertEquals(keyEqualToValueConfiguration.getString("1", "12"), "1");
        assertEquals(keyEqualToValueConfiguration.getBoolean("1", true), false);

        assertEquals(keyEqualToValueConfiguration.getBoolean("True"), true);
        assertEquals(keyEqualToValueConfiguration.getBoolean("false"), false);
        assertEquals(keyEqualToValueConfiguration.getBoolean("True", false), true);

        assertEquals(keyEqualToValueConfiguration.getInt("123"), 123);
        assertEquals(keyEqualToValueConfiguration.getInt("-123", 1), -123);

        assertEquals(keyEqualToValueConfiguration.getLong("123"), 123);
        assertEquals(keyEqualToValueConfiguration.getLong("-123", 1), -123);

        assertEquals(keyEqualToValueConfiguration.getDouble("123"), 123, 0.00001);
        assertEquals(keyEqualToValueConfiguration.getDouble("-123", 1), -123, 0.00001);

        assertEquals(keyEqualToValueConfiguration.getFloat("123"), 123, 0.00001);
        assertEquals(keyEqualToValueConfiguration.getFloat("-123", 1), -123, 0.00001);
    }

    @Test
    public void testDefault() {
        assertEquals(missExceptionConfiguration.getBoolean("True", false), false);
        assertEquals(missExceptionConfiguration.getInt("123", 1), 1);
        assertEquals(missExceptionConfiguration.getLong("123", 1), 1);
        assertEquals(missExceptionConfiguration.getDouble("123", 1), 1, 0.00001);
        assertEquals(missExceptionConfiguration.getFloat("123", 1), 1, 0.00001);
    }
}
