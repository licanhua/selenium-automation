package framework.expedia.pages;

import com.github.licanhua.test.framework.Page;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.FindBy;

public class CarSearchPage extends Page {
    private static Logger logger = Logger.getLogger(CarSearchPage.class.getName());

    @FindBy(id="wizard-theme-wrapper")
    CarSearchComponent carSearchComponent;

    public void doSearch() {
        logger.info("Search car from DEN to SAN");
        carSearchComponent.searchCar("DEN","SAN");
    }
}
