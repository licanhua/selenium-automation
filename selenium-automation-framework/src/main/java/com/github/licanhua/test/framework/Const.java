package com.github.licanhua.test.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

/**
 * @author Canhua Li
 */
public class Const {

    // Used by AutomationConfig
    /**
    * {@code waitDurationInSeconds}: how many seconds to timeout when call any waitForXX functions
    * in {@link com.github.licanhua.test.framework.base.WaitFunctions}
    * {@code waitDurationInSeconds} should be big than {@code ajaxTimeoutInSeconds}
    * because it will call multiple findElement(s) until waitDurationInSeconds timeout.
     * <p>
    * @{code ajaxTimeoutInSeconds}: How many seconds a {@link org.openqa.selenium.WebElement#findElement(By)} or
    * {@link org.openqa.selenium.WebElement#findElements(By)} timeout. When you call any {@link org.openqa.selenium.WebElement} function
    * a findElements is twigged and Selenium will throw {@link NoSuchElementException} if ajaxTimeoutInSeconds reached.
    * ajaxTimeoutInSeconds should less than waitDurationInSeconds
    */
    public final static String AUTOMATION_INI = "config/automation.ini";
    // default values
    public final static String DEDAULT_BROWSER_NAME = "firefox";
    public final static String DEFAULT_AJAX_TIMEOUT_IN_SECONDS = "10";
    public final static String DEFAULT_WAIT_DURATION_IN_SECONDS = "30";
    public final static String DEFAULT_REMOTE_WEB_DRIVER_ADDRESS = "";
    public final static String DEFAULT_AUTOSNAPSHOT = "false";
    public final static int DEFAULT_MIN_TIME_OUT_IN_SECONDS = 1;
    // keys
    public final static String BROWSER_NAME = "browserName";
    public final static String WAIT_DURATION_IN_SECONDS = "waitDurationInSeconds";
    public final static String AJAX_TIMEOUT_IN_SECONDS = "ajaxTimeoutInSeconds";
    public final static String REMOTE_WEB_DRIVER_ADDRESS = "remoteWebDriverAddress";
    public final static String AUTO_SNAPSHOT = "autoSnapshot";
    // End Used by AutomationConfig
}
