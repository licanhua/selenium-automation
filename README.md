Welcome to Selenium Automation Framework!
===================
**Selenium Automation Framework** Selenium Automation Framework is not only a test automation framework, but also the best practice of using Selenium for automation testing. It provides nature way describe your web pages, and provides a lot of handy features to release you from the framework development. You can apply a lot of best practices of Selenium testing in one minute.

This framework provides a lot of best practice I did in my SDET career. I hope you can benefit from it and learn from it even if you don't use the framework. The practices includes but not limit to:

 1. A nature way to describe a page and its content.
 2. Automatically validate the presence of all elements when creating a page.
 3. Same testcase can be run against different browser like IE, Chrome, Firefox and even Selenium Grid without modification
 4. Support Ajax element wait internally
 5. @RelativeElement helps you locate relative elements without any code
 6. Easy way to describe tables.
 7. Flexible test data and configuration management
 8. Select, CheckBox are WebElements.
 9. Make use of @Rule annotation.
 10. Best practice of page navigation.
 11. Simple clickSubmit
 12. Snapshot
 13. Don't call findElement(s) directly any more
 14. Avoid fluent wait and sleep to wait for WebElement 
 15. No PageFactory any more.
 16. New test data from HOCON, JSON and propertie files

# Dependency

	<dependency>
		<groupId>com.github.licanhua.test</groupId>
		<artifactId>selenium-automation-framework</artifactId>
		<version>0.1.2</version>
	</dependency>

# Examples
##[Amazon Search Diaper example](https://github.com/licanhua/selenium-automation-showcase/blob/master/AMAZON.md)

##Project scope
This project is very simple:

 - Goto www.amazon.com
 - Select baby department, Input diaper, and click search
 - Extract all product names and their prices, then print them out.

####Goal
 - Follow the nature way to describe the pages.
 - use json file to provide configurations for test case
 - define Pages.java to help navigation for all projects
 - log4j.properties and automation.properties setting file.
 - Use SelectBox
 - load test data from configuration files.
 - startPage

No wait for element, no webdriver, no WebElement validation. framework already provide you. You just need to focus on:

 - Define Pages and describe them as a hierarchy structure.
 - Define Pages.java for navigation
 - Define test data in configuration files
 - Define functions to finish the user operation, page navigation and the business logic.

##[Expedia car search example](https://github.com/licanhua/selenium-automation/tree/master/selenium-automation-example/src/main/java/com/github/licanhua/example/expedia)
This project goes to www.expedia.com, navigate to car search page, then input the search information and finally goes to car search result page.

#### Goal
  - Checkbox and Select Box
  - OptionalElement
  - waitForElementToBeDisplayed and waitForElementToBeAbsent
  - @RelativeElement
####
   document -TBD
   
##[Datatables example](https://github.com/licanhua/selenium-automation/tree/master/selenium-automation-example/src/main/java/com/github/licanhua/example/datatables/)
This example shows how to use **@RelativeElement** annotation. It's very easy to extract data row from a table. You just need to define the page hierarchy: HomePage includes ExampleTable, ExampleTable includes ExampleRow, and ExampleRow includes row items. the data in a row is ready for you  to use.

####
   document -TBD

# Best practices

## A nature way to describe a page and its content.
-------------
Do you follow PageObjects pattern to design your test case? if yes, you already have a good start. PageFactory helps you inject all WebElement objects for you. That's very cool, but it's hard to describe a complex homepage. If we describe a homepage in nature language, it should like this:

    A HomePage includes a header, a navigation page, a lot of components and footer.
    - header provides account management, language ...
    - navigation page includes flight, hotel, car ...
   
This framework provides a nature way to organize and describe your Pages. This framework hide you from PageObjects and PageFactory. You can describe a object with a WebElement, but you can also group a lot of WebElement into a Container. A Page can includes any number of Containers, and a container can includes other containers of any WebElements. This framework helps you create the cascading of Page Objects in a Page no matter its inside is WebElement or a Container or Page. So the framework enables you to describe the homepage like this:

    HomePage 
		    - Header
			    - account management
			    - language
		    - Navigation
			    - car
			    - flight
Here we express the relations in code:

    class Header extends Container{
	    @Findby(id="account")
	    WebElement accountManagement;
	}

	class Navigation extends Container{
		@FindBy(id="flight")
		WebElement flight;
	}
	
	class HomePage extends Page{
		@FindBy(id="head")
		Header head;
		
		@FindBy(id="nav")
		Navigation nav;
	}
	
	class HomePageTest {
		@Test
		public void testHomePage() {
			HomePage homepage = new HomePage();
		}
	}

After you defined **Header** and **Navigation**, you could treat them as **WebElement**. When you need the homepage, just **new HomePage()**. Framework would inject nav, header for you and everything is ready to use. No need to think about PageObject and PageFactory any more.

Automatically validate the presence of elements when creating a page.
-------------
By default, when you create a Page, framework automatically help you validate the presence of its content. Like below example, when you `HomePage page = new HomePage()`, framework helps you wait until **nav** is existing or timeout. When you use the page object, nav and all other elements belongs to nav in in HomePage is ready for you to use except footer because footer is marked optional. framework validate all elements unless you annotate the element with `@OptionalElement` or you annotate a class with `@AutoValidation(false)`. Exception would be thrown if validation fails.

    public class HomePage extends Page {
        @FindBy(id="wizard-theme-wrapper")
        Navigation nav;

        @OptionalElement
		@FindBy(id="footer")
		WebElement footer;
        public void doSearch() {
            nav.toCarSearchPage();
        }
    }

    

Same testcase can be run against different browser like IE, Chrome, Firefox and even Selenium Grid without modification
-------------
Don’t assume that driver will be an instance of FireFoxDriver or InternetExplorerDriver only. maybe It’s quite easy for you to create a small framework around selenium to support any browser. Now it's ready for you to use.
Just change the configuration in **config/automation.properties** to the browser you need. we support Firefox, Chrome, IE to run locally and any browser with a remoteWebDriverAddress.

    browserName=chrome
    remoteWebDriverAddress=

System properties take priority over configuration file. If you use mvn to do the test, you can change the behaviour dynamically by **-DbrowserName=firefox -DRemoteWebDriver =http://localhost:4444/wd/hub**.

If you want to talk to Remote WebDriver and want different DesiredCapabilities, just defined a config file in `config/browser/${browserName}.properties`. Framework would load this file automatically and negotiate with remote webdriver. Here is an example for firefox.

    config/browser/firefox.properties
        browserName=firefox
        version=
        platform=WINDOWS
        javascriptEnabled=true
        cssSelectorsEnabled=true

Support Ajax element wait internally
-------------
How many times we use FluentWait to wait for a ajax element is ready for you in our test cases. Do you ever use `org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory`. Now framework makes it very simple and you need to do nothing. By default, all WebElement are initialized with `AjaxElementLocatorFactory`. If you are using ajaxElement like ajaxElement.click() and the element doesn't exist, it would wait the presence of this element until it timeouts.

    public class HomePage {
        @FindBy(id="ajax")
        @OptionalElement
        WebElement ajaxElement;

        public void doSomething() {
            ajaxElement.click();
        }
    }

If you want to use implicit wait and fail immediately if no element is present, you just add `@AjaxEnabled(false)` to your class.

    @AjaxEnabled(false)
    public class HomePage {
    }

Select, CheckBox are WebElements.
-------------
You don't need do special handle for Select and CheckBox anymore. SelectBox and CheckBox is ready for you to use just like a normal WebElement.

    public class DemoPage{
        @FindBy(id="selectdemo")
        SelectBox selectElement;

        @FindBy(id="checkboxDemo")
        CheckBox checkBoxElement;

        public void selectByValue(String value) {
            selectElement.selectByValue(value);
        }

        public void check() {
            checkBoxElement.check();
        }
    }

Make use of @Rule annotation.
-------------
If you never use @Rule, I suggest you google it. It's worth your time to learn it and use it in your test framework. Now write automation is very easy, just add 

    @Rule
    public AutomationDriver driver = new AutomationDriver();

in your test cases, Selenium automation framework is automatically launched for you. 

	class DemoTest {
		@Rule
		public AutomationDriver driver = new AutomationDriver();

		@Test
		public void demoTest() {
			HomePage homePage = new HomePage();
			homePage.toCarSearchPage();
		}
	}

**You only need annotate the AutomationDriver with @Rule, automation framework would help you handle all the dirt work**

Best practice of page navigation.
-------------
If you have tens of Pages, It would be a nightmare to navigate between them. A very good practice is never new Page() in your test cases. And always define a Pages.java to handle all navigation.
This framework provide PageHelper to help you create new Pages and navigate between them. Only thing you need to know is which Page class you are navigate to. The new created page is ready for you to use, and it includes all the features framework supports like auto validation, ajax element wait, ...

    public class Pages {
        public static HomePage homePage() {
            return PageHelper.toPage(HomePage.class);
        }

        public static CheckoutPage checkoutPage() {
            return PageHelper.toPage(CheckoutPage.class);
        }

        public static CarSearchPage carSearchPage() {
            return PageHelper.toPage(CarSearchPage.class);
        }

        public static CarSearchResultPage carSearchResultPage() {
            return PageHelper.toPage(CarSearchResultPage.class);
        }

    }

    public class NavigationTest {
            @Test
            public void navigationTest() {
                Pages.homePage().toCarSearchPage();
                Pages.carSearchPage().searchCar("Seattle", "SFO");
                Pages.searchResultPage().selectFirst();
                Pages.checkoutPage().checkout();
            }
    }

Do you see the above test cases is very simple? framework does the validation and you handle the navigation.

Simple clickSubmit
-------------
Have you ever written clickSubmit() function like this before?

        public void clickSubmit() {
               // Take snapshot
               // click the element
               // create new page
               // wait for new page is ready
               // validate the new page
            }


Today it be more simple, clickAndToPage will help you do everything mentioned above .

     clickAndToPage(webElement, NewPage.class);

Snapshot
------------
Just define `autoSnapshot=true`in any configuration file. and use clickAndToPage to click WebElement, snapshot is already taken for you.

There are two kinds of snapshot:
1, the page source of webpage.  saved as 1-source.html
2, the screen snapshot.  saved as 1-screenshot.png
They are created in testOutput like
testOutput\20161030121459\com.github.licanhua.example.datatables.test.DatatablesTest.datatablesTest
Test time and test case is as part of the directory.

Don't call findElement(s) directly any more
-------------
Why do we need call findElement(s) by ourselves? Maybe because of the following two reason: 

 One is for things like Select and RadioButton. Selenium don't support it you have to:

    Select foo = new Select(sDriver.findElement(By.id("foo")));
    foo.selectByValue("myValue");

Please use SelectBox

    public class HomePage {
        @FindBy(id="ajax")
        SelectBox select;

        public void doSomething() {
           select.selectByIndex(10);
        }
    }


Another one is find element relative to other element. Please use Container


Avoid fluent wait and sleep to wait for WebElement 
-------------
One of the good practice is use FluentWait other than Thread.sleep. I want to say avoid fluent too. Ajax element is supported by framework and ajax element is suppose to be ready for use when you create pages.

If you still think it's not enough,  try to use wait functions from [WaitFunctions.java](https://github.com/licanhua/selenium-automation/blob/master/selenium-automation-framework/src/main/java/com/github/licanhua/test/framework/base/WaitFunctions.java).
Mark the element as `@OptionalElement`to skip the auto validation.

	public class CarSearchPage extends Page {
	    @OptionalElement
	    @FindBy(className="display-group-results")
	    SuggestionSelectComponent suggestionSelectComponent;
    
	    private void fillLocationField(WebElement webElement, String location) {
	        webElement.clear();
	        webElement.sendKeys(location);
	        waitForElementToBeDisplayed(suggestionComponent);
	        suggestionSelectComponent.select(1);
	        waitForElementToBeAbsent(suggestionComponent);
	    }
	}


Use startPage configuration other than WebDriver navigate
-------------
Because selenium need webDrive to navigate and locate element. If you have a Selenium test project, search WebDriver, how many times it occurs? Do we really need it? 
This framework is hiding WebDriver from you. **startPage** is provided to help you get to the first page. define startPage in any configuration file like below.
 
    startPage=https://datatables.net

 During the start up, framework would automatically navigate you to startpage and waitForPageLoadComplete. If you really want the WebDriver, just call:

	WebDriver webDriver = this.getElementContext().getWebDriver();

No PageFactory any more.
-------------
I don't know how you make PageFactory to initialize a page object. In old days, I always define a parent Page object and it look like this:

    public class PageBase {
        public PageBase(WebDriver webDriver) {
            initElements();
        }

        public void initElements() {
             PageFactory.initElements(driver, ...);
        }
    }

    public class HomePage extends PageBase {
        public HomePage(WebDriver webDriver) {
            super(webDriver);
        }
    }

Now this functionality is implement by framework. You simply define you page like this. 

    public class HomePage extends Page {
    }


@RelativeElement helps you locate relative elements without code
-------------
By default, all @FindBy is search in all the webpage. If we describe the context like Table and Row, we need relative search. just put **@RelativeElement** in your relative element. framework would help you change the SearchContext to it's parent(Here position and office in ExampleRow has change SearchContext to By.className=sorting_1) 

	public class ExampleRow extends Container {
		@RelativeElement
		@FindBy(xpath="./../td[2]")
		WebElement postition;

		@RelativeElement
		@FindBy(xpath = "./../td[4]")
		WebElement office;
	}

	public class ExampleTable extends Container {
		@FindBy(className="sorting_1")
		List<ExampleRow> rows;
	}	



Easy way to describe tables.
-------------
In today's project, tables are created dynamically, and often it creates id like 'aria-option-0', 'aria-option-1', 'aria-option-2'. we can make use of @RelativeElement above to achieve this
see [Examples](https://github.com/licanhua/selenium-automation/tree/master/selenium-automation-example/src/main/java/com/github/licanhua/example/datatables/test)


Flexible test data and configuration management
--------------
Framework provides a lot of configuration functions to help you provide test data to test cases. The same get command like **Configuration#getString(key)** would result in different value depends on target environment, test case name, and configuration. This allow us to put prod and integration test data in the same configuration file and configuration service load different data for you depends on test case name, target environment and configuration files.

This framework also supports flexible test data source, framework supports **HOCON**, **JSON** and **properties** format. and even support to extend data source from database to any network resource(Just need implement a ConfigService).

How can we make our test run against different platform and different environment but with different test data without code change. framework introduces two kind of scecrets here:

 - package name of the test cases is used to load the configuration in override mode, if the test case is **com.github.licanhua.test.amazon.HomePageTest#testSearch()**. the following configuration under config/ would be loaded in sequence:
 
 
	automation.properties
	com.conf
	com.json
	com.properties
	com.github.conf
	com.github.json
	com.github.properties
	...
	com.github.licanhua.test.amazon.HomePageTest.conf
	com.github.licanhua.test.amazon.HomePageTest.json
	com.github.licanhua.test.amazon.HomePageTest.properties
	systemproperties
	
If the same key defined in more than two files, the late loaded would overwrite the former. systemproperties has the highest priority. If you run with **-Dbrowsername=firefox**, the final result would be firefox.

 - The same cofiguration.getString(key) would get different value depends on testMethodName, targetEnvironment, Search priority and configuration files.

Before we go forward, let see the following definition:

 1. **testMethodName**: the current running method name like testSearch in above example.
 2. **targetEnvironment**: it's like prod, integration, dev, test   
 3. **Search priority**: for the same key, different value will be got from Configuration. If we search for a key, the following search order will be execute until a value if find.
 
 
		 ${testMethodName}.${targetEnvironment}.${key}
		 ${testMethodName}.{key}
		 ${targetEnvironment}.${key}
		 ${key}
 
 
 4. **Configuration** Files: we make use of package name to load the configuration files. automation.properties has the lowest priority, but System Properties(same as System.getProperties()) has the highest priority. in this way, it allow us to change the parameters dynamically. For example, we can run the same test cases against firefox and chrome by:
 	
	mvn test -DbrowserName=firefox
	mvn test -DbrowserName=chrome
    
If we have a test com.licanhua.Test.demo(). testMethodName is demo. and three configuration will be loaded. I assume all are json files.

	com.json
	com.licanhua.json
	com.licanhua.Test.json

If com.licanhua.Test.json is:

	{
		demo: {
			prod: {
				"userName": "demoInProd"
			}, test: {
				"userName": "testInProd"
			}
		},
		prod: {
			"userName": "demo"
		},
		"userName": "test",
		"password": "got it"
	}

If we call getString("password") from demo method, and targetEnv is prod, we will search password in the following order and finally get '**got it'**

	demo.prod.password
	demo.password
	prod.password
	password

if we search userName, demo.prod.userName is matched and be returned with **demoInProd**

	
New test data from HOCON, JSON and propertie files
--------------
Framework use [typesafe](https://github.com/typesafehub/config) as configuration reader, so it supports HOCON, JSON and properties. You should define the configuration with ext name with one of them from: **.conf**, **.json** or **.properties**

Separate the test data from test code by configuration service.
--------------
Any time you can get the configuration by getConfiguration()

	public HomePage extens Page {
		public void loginAsGuest() {
			Configuration conf = getConfiguration();
			String name = conf.getString("guestName");
			String password =conf.getString("guestPassword");
			loginAs(name, password);
		}
	}

Advance Features (TBD)
----------------
A lot of advance features is available to use but still lack of document. they includes:
 - drive more than two same type of browser in testing
 - Integrate with Selenium Grid
 - Custom configuration service. 

Thanks
-------------
Thanks to my wife and my mother, they helped to take care of the kids and let me have two weeks of time to make this tool ready to use.

Author
--------------
Canhua Li 

Email: licanhua@live.com

Linkedin: https://www.linkedin.com/in/licanhua

Lovely link: https://licanhua.github.io/selenium-automation/

github: https://github.com/licanhua

