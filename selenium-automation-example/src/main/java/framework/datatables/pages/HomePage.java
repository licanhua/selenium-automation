package framework.datatables.pages;

import com.github.licanhua.test.framework.Page;
import com.github.licanhua.test.framework.annotation.AjaxEnabled;
import com.github.licanhua.test.framework.annotation.AutoValidation;
import org.openqa.selenium.support.FindBy;

/**
 * @author Canhua Li
 */

@AutoValidation(false)
public class HomePage extends Page{
    @FindBy(id="example")
    ExampleTable exampleTable;

    public void print() {
        exampleTable.print();
    }
}
