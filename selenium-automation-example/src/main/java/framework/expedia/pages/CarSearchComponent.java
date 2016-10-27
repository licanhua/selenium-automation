package framework.expedia.pages;

import com.github.licanhua.test.framework.Container;
import com.github.licanhua.test.framework.annotation.OptionalElement;
import com.github.licanhua.test.framework.base.SelectBox;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CarSearchComponent extends Container{
    private static Logger logger = Logger.getLogger(CarSearchComponent.class.getName());


    @FindBy(id="car-pickup")
    WebElement pickupLocation;

    @FindBy(id="car-dropoff")
    WebElement dropoffLocation;

    @FindBy(id="car-pickup-date")
    WebElement pickupDate;

    @FindBy(id="car-dropoff-date")
    WebElement dropoffDate;

    @FindBy(id="car-pickup-time")
    SelectBox pickupTime;

    @FindBy(id="car-dropoff-time")
    SelectBox dropoffTime;

    @FindBy(id="search-button")
    WebElement searchButton;


    @OptionalElement
    @FindBy(className="display-group-results")
    SuggestionSelectComponent suggestionSelectComponent;

    private void fillLocationField(WebElement webElement, String location) {
        webElement.clear();
        webElement.sendKeys(location);
        waitForElementToBeDisplayed(suggestionSelectComponent);
        suggestionSelectComponent.select(1);
        waitForElementToBeAbsent(suggestionSelectComponent);
    }

    String getFutureDateString(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, days);
        return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
    }
    private void fillDateFields() {
        pickupDate.clear();
        pickupDate.sendKeys(getFutureDateString(60));
        dropoffDate.clear();
        dropoffDate.sendKeys(getFutureDateString(63));
    }
    public void searchCar(String from, String to) {
        logger.info("Search Car");
        logger.info("Fill pickLocation to " + from);

        fillLocationField(pickupLocation, from);

        logger.info("Fill dropoffLocation to " + to);
        fillLocationField(dropoffLocation, to);

        logger.info("Fill pickup/drop date");
        fillDateFields();

        logger.info("Fill pickup/drop time");
        pickupTime.selectByValue("430PM");
        dropoffTime.selectByValue("430AM");

        logger.info("Click on search button");
        clickAndToPage(searchButton, CarSearchResultPage.class);
    }
}
