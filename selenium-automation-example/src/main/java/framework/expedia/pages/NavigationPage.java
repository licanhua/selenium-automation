package framework.expedia.pages;

import com.github.licanhua.test.framework.Page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationPage extends Page {
    private static Logger logger = Logger.getLogger(NavigationPage.class.getName());

    @FindBy(id="primary-header-home")
    WebElement home;

    @FindBy(id="primary-header-package")
    WebElement bundleDeal;

    @FindBy(id="primary-header-hotel")
    WebElement hotels;

    @FindBy(id="primary-header-car")
    WebElement car;


    public void toCarPage() {
        logger.info("navigate to CarSearchPage");
        clickAndToPage(car, CarSearchPage.class);
    }

    public void toHomePage() {
        logger.info("navigate to HomePage");
        this.clickAndToPage(home, HomePage.class);
    }
}
