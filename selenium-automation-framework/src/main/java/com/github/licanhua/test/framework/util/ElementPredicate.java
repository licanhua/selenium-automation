package com.github.licanhua.test.framework.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Canned {@link ElementPredicate}s which are generally useful within webdriver
 * tests.
 * @author Canhua Li
 */
public class ElementPredicate {
    /**
     * An predicate for checking that an element is present on the DOM of a
     * page. This does not necessarily mean that the element is visible.
     *
     * @return  true once it is present
     */
    public static Predicate<WebElement> presenceOfElement() {
        return new Predicate<WebElement>() {
            public boolean apply(WebElement element) {
                try {
                    element.isDisplayed();
                    return true;
                } catch (NoSuchElementException e) {
                    return false;
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            }
        };
    }

    /**
     * An predicate for checking that an element is not present on the DOM of a
     * page. This does not necessarily mean that the element is not visible.
     *
     * @return  true once it is not present
     */
    public static Predicate<WebElement> absentOfElement() {
        return Predicates.not(presenceOfElement());
    }

    /**
     * An predicate for checking that there is at least one element present on a
     * web page.
     *
     * * @return  true once one or more elements is present
     */
    public static Predicate<List<WebElement>> presenceOfAllElements() {
        return new Predicate<List<WebElement>>() {
            public boolean apply(List<WebElement> elements) {
               return elements.size() > 0;
            }
        };
    }

    /**
     * An predicate for checking that all element is not present on the DOM of a
     * page. This does not necessarily mean that the element is not visible.
     *
     * @return  true once it is not present
     */
    public static Predicate<List<WebElement>> absentOfAllElement() {
        return Predicates.not(presenceOfAllElements());
    }

    /**
     * An predicate for checking that an element is visible present on the DOM.
     *
     * @return  true once it is visible and present
     */
    public static Predicate<WebElement>  elementToBeDisplayed() {
        return  Predicates.not(elementToBeHidden());
    }

    /**
     * An predicate for checking that an element is hidden and
     * present on the DOM.
     *
     * @return  true once it is not visible or but present
     */
    public static Predicate<WebElement> elementToBeHidden() {
        return new Predicate<WebElement>() {
            public boolean apply(WebElement element) {
                return ! element.isDisplayed();
            }
        };
    }

    /**
     * An predicate for checking if the given text is present in the specified
     * element.
     */
    public static Predicate<WebElement> textToBePresentInElement(final String text) {
        return new Predicate<WebElement>() {
            public boolean apply(WebElement element) {
                String elementText = element.getText();
                return elementText.contains(text);
            }
        };
    }

    /**
     * An predicate for checking if the given text is not present in the specified
     * element.
     */
    public static Predicate<WebElement> textToBeNotPresentInElement(final String text) {
        return Predicates.not(textToBePresentInElement(text));
    }

    /**
     * An predicate for checking an element is visible and enabled such that you
     * can click it.
     */
    public static Predicate<WebElement> elementToBeEnabled() {
        return new Predicate<WebElement>() {
            public boolean apply(WebElement element) {
               return element.isEnabled();
            }
        };
    }

    /**
     * An predicate for checking an element is visible but disable such that you
     * can't click it.
     */
    public static Predicate<WebElement> elementToBeDisabled() {
        return Predicates.not(elementToBeEnabled());
    }
}
