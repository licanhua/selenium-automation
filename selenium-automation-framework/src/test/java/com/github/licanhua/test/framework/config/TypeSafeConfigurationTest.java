package com.github.licanhua.test.framework.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.openqa.selenium.Beta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.*;

/**
 * @author Canhua Li
 */
public class TypeSafeConfigurationTest {
    private Description context;
    private TypeSafeConfiguration configuration;
    @Before
    public void setup() {
         context = Description.createTestDescription(TypeSafeConfigurationTest.class, "testValueExistings");
         configuration = new TypeSafeConfiguration(context);
    }
    @Test
    public void testGetValueButExistings() {

        assertThat(configuration.getString("string"), equalTo("string"));
        assertThat(configuration.getStringList("strings").size(), equalTo(2));
        assertThat(configuration.getBoolean("boolean"), equalTo(false));
        assertThat(configuration.getLong("long"), equalTo(10L));
        assertThat(configuration.getInt("int"), equalTo(10));
        assertThat(configuration.getDouble("double"), closeTo( 10.11, 0.01));
        assertThat((double)configuration.getFloat("float"), closeTo( 11.11, 0.01));
    }

    @Test
    public void testGetDefault() {
        Configuration configuration = new ConfigurationProxy(this.context, this.configuration);
        assertThat(configuration.getString("stringAA", "AA"), equalTo("AA"));
        assertThat(configuration.getStringList("stringsBB", null), equalTo(null));
        assertThat(configuration.getBoolean("booleanAA", true), equalTo(true));
        assertThat(configuration.getLong("longAA",12), equalTo(12L));
        assertThat(configuration.getInt("intAA", 15), equalTo(15));
        assertThat(configuration.getDouble("doubleAA", 10.11), closeTo( 10.11, 0.01));
        assertThat((double)configuration.getFloat("floatAA", 10.11f), closeTo( 10.11, 0.01));
    }


    @Test(expected = ConfigurationException.Missing.class)
    public void testGetValueButMissing() {
        assertThat(configuration.getString("xxxxxxstring"), equalTo("string"));
    }


    @Test(expected = ConfigurationException.ConvertFail.class)
    public void testGetValueButConvertError() {
        assertThat(configuration.getString("hello"), equalTo("string"));
    }


    @Test
    public void jsonOverwrite() {
        Assert.assertThat(configuration.getInt("overwrite"), equalTo(1111));
    }

    @Test
    public void testJSONString() {
        Assert.assertThat(configuration.getString("hello.x"), equalTo("1"));
        Assert.assertThat(configuration.getString("hello.z", "111"), equalTo("111"));
        Assert.assertThat(configuration.getInt("hello.x"), equalTo(1));
        Assert.assertThat(configuration.getString("browserName"), equalTo("chrome"));
    }
}
