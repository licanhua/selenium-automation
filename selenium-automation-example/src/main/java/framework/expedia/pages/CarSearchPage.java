package framework.expedia.pages;

import com.github.licanhua.test.framework.Page;
import com.github.licanhua.test.framework.annotation.OptionalElement;
import org.openqa.selenium.support.FindBy;

public class CarSearchPage extends Page {
    @FindBy(id="wizard-theme-wrapper")
    CarSearchComponent carSearchComponent;

    public void doSearch() {
        carSearchComponent.searchCar("DEN","SAN");
    }
}
