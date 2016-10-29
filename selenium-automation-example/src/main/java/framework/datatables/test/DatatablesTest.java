package framework.datatables.test;

import com.github.licanhua.test.framework.AutomationDriver;
import framework.datatables.pages.HomePage;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Canhua Li
 */
public class DatatablesTest {
    @Rule
    public AutomationDriver automationDriver = new AutomationDriver();

    @Test
    public void datatablesTest() {
        automationDriver.getWebDriver().navigate().to("https://datatables.net");

        new HomePage().print();
    }
}
