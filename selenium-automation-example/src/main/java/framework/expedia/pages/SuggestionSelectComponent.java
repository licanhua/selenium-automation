package framework.expedia.pages;

import com.github.licanhua.test.framework.Container;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

// id=display-group-results
public class SuggestionSelectComponent extends Container{
    private static Logger logger = Logger.getLogger(SuggestionSelectComponent.class.getName());

    @FindBy(xpath=".//a[starts-with(@id, 'aria-option-')]")
    List<WebElement> selections;

    public void select(int index) {
        if (selections.size() > index)
            selections.get(index).click();
        else
            throw new RuntimeException("index is out of boundary, index= " + index);
    }
}
