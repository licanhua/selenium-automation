package framework.expedia.pages;

import com.github.licanhua.test.framework.util.PageHelper;

public class Pages {
    public static HomePage homePage() {
        return PageHelper.toPage(HomePage.class);
    }

    public static NavigationPage navigationPage() {
        return PageHelper.toPage(NavigationPage.class);
    }

    public static CarSearchPage carSearchPage() {
        return PageHelper.toPage(CarSearchPage.class);
    }

    public static CarSearchResultPage carSearchResultPage() {
        return PageHelper.toPage(CarSearchResultPage.class);
    }
}
