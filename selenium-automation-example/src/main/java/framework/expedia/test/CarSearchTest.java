package framework.expedia.test;

import com.github.licanhua.test.framework.AutomationDriver;
import framework.expedia.pages.Pages;
import org.junit.Rule;
import org.junit.Test;

public class CarSearchTest {
    @Rule
    public AutomationDriver automationDriver = new AutomationDriver();

    @Test
    public void carSearchTest() {
        automationDriver.getWebDriver().navigate().to("https://www.expedia.com");
        Pages.navigationPage().toCarPage();
        Pages.carSearchPage().doSearch();
        Pages.carSearchResultPage();

    }
}
