Welcome to Selenium Automation Framework!
===================
**Selenium automation framework** is not only a java automation framework, but also the best practice of using Selenium.
I used selenium for years. A lot of reason to love him, but a lot of reason to hate him. This integrates a lots of selenium good practice to framework and provide you with reusable component. I listed a lot of **A new way** here. If you are impressed by any of them, please just down it and try the example. Buy me a beer if you love it.

This framework provides:
 - A new way to describe and organize you pages.
 - A new way you don't need to validate any page WebElement.
 - A new way to use Select, Checkbox and RadioButton.
 - A new way to use @Rule annotation.
 - A new way to handle navigation.
 - A new way to not full you test case with Fluent wait and avoid sleep
 - A new way to describe tables.
 - A new way you don't need to care about snapshot
 - A new way you don't need to handle WebDriver
 - A new way you don't need to care about configuration. (TDB)
 - A new way you don't need to handle URL. (TBD)

A new way like nature language to describe and organize you pages.
-------------
Are you using PageObjects pattern? if yes, you already have a good start. Can you help to describe home www.expedia.com? If we describe it in nature language, it should like this: 

    A HomePage includes a header, a navigation page, a search component, Deals, and footer.
    - header provides account management, language ...
    - navigation page includes flight, hotel, car ...
    - search includes pickup location, dropoff location ...
    - deals includes ...

In the old way, because java doesn't support multiple inheritance, we may describe it like this:

    class HeaderPage {
	    @Findby(id="xx")
	    WebElement accountManagement;
	}
	
	class NavigationPage extends HeaderPage {
		@FindBy("yy")
		WebElement flight;
	}
	class HomePage extends NavigationPage {
	}
As a workaround, we may describe it like this:

    class HeaderPage{}
    class NavigationPage{}
    class HomePage{}
In order to verify homepage only, we:

    HeaderPage header = new HeadPage();
    NavigationPage nav = new NavigationPage();
    HomePage homePage = new HomePage();
    verify(header);
    verify(nav);
    verify(homePage);

A new way you don't need to validate any page WebElement.
-------------

A new way to use Select, Checkbox and RadioButton.
-------------

A new way to use @Rule annotation.
-------------

A new way to handle navigation.
-------------

A new way to not full you test case with Fluent wait and avoid sleep
-------------
A new way to describe tables.
-------------

A new way you don't need to care about snapshot
-------------

A new way you don't need to handle WebDriver
-------------

A new way you don't need to care about configuration. (TDB)
-------------

A new way you don't need to handle URL. (TBD)
-------------
