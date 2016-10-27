package framework.expedia.pages;

import com.github.licanhua.test.framework.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CarSearchResultPage extends Page {
    @FindBy(id="search-results")
    CarSearchResultComponent carSearchResultComponent;
}
