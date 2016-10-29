package framework.expedia.pages;

import com.github.licanhua.test.framework.Container;

import com.github.licanhua.test.framework.annotation.RelativeElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CarSearchResultComponent extends Container{
    private static Logger logger = Logger.getLogger(CarSearchResultComponent.class.getName());

    @RelativeElement
    @FindBy(xpath=".//a[starts-with(@id, 'ember')]")
    List<WebElement> reserveButtons;

    public void reserve(int index) {
        logger.info("Search result item found " + reserveButtons.size());
        for (int i=0; i<reserveButtons.size(); i++) {
            logger.info(reserveButtons.get(i).getText());
        }
        if (reserveButtons.size() > index)
            reserveButtons.get(index).click();
        else
            throw new RuntimeException("index is out of boundary, index= " + index);

    }
}
