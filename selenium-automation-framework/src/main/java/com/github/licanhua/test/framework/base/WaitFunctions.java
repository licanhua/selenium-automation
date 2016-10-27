package com.github.licanhua.test.framework.base;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author Canhua Li
 */
public interface WaitFunctions {
    /**
     * wait for until document.readyState is complete.
     *
     */
    void waitForDocumentReadyState();

    /**
     * wait for until an element is displayed on the DOM of a page.
     *
     * @param element to be waited
     */
    public void waitForElementToBeDisplayed(WebElement element);

    /**
     * wait for until an element is hidden on the DOM of a page.
     *
     * @param element to be waited
     */
    public void waitForElementToBeHidden(WebElement element);

    /**
     * wait for until an element is present on the DOM of a page.
     *
     * @param element to be waited
     */
    public void waitForElementToBePresent(WebElement element);

    /**
     * wait for until an element is not present on the DOM of a
     * page. This does not necessarily mean that the element is not visible.
     *
     * @param element to be waited
     */
    public void waitForElementToBeAbsent(WebElement element);

    /**
     * wait for until an element is visible and enabled such that you
     * can click it.
     * @param element to be waited
     */
    public void waitForElementToBeEnabled(WebElement element);

    /**
     * wait for until an element is visible but disabled such that you
     * can't click it.
     * @param element to be waited
     */
    public void waitForElementToBeDisabled(WebElement element);

    /**
     * wait until at least one element present on a web page.
     *
     * @param elements to be waited
     */
    public void waitForPresenceOfAllElements(List<WebElement> elements);

    /**
     * wait until at no element present on a web page.
     *
     * @param elements to be waited
     */
    public void waitForAbsentOfAllElements(List<WebElement> elements);

    /**
     * wait until if the given text is present in the specified element.
     *
     * @param element to be waited
     * @param text to be waited
     */
    public void waitForTextToBePresentInElement(WebElement element, String text);
}
