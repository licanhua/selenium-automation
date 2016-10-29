package framework.datatables.pages;

import com.github.licanhua.test.framework.Container;
import com.github.licanhua.test.framework.annotation.AutoValidation;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Canhua Li
 */

@AutoValidation(false)
public class ExampleTable extends Container {
    @FindBy(className="sorting_1")
    List<ExampleRow> rows;

    public void print() {
        for (ExampleRow row: rows) {
            row.print();
        }
    }
}
