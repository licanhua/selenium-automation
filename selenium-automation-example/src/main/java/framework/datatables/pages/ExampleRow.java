package framework.datatables.pages;

import com.github.licanhua.test.framework.Container;
import com.github.licanhua.test.framework.annotation.AutoValidation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Canhua Li
 */
@AutoValidation(false)
public class ExampleRow extends Container {
    @FindBy(xpath="./../td[2]")
    WebElement postition;

    @FindBy(xpath = "./../td[4]")
    WebElement office;

    public void print() {
        System.out.print("Position " + postition.getText() + " office" + office);
    }
}
