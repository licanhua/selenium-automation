Welcome to Selenium Automation Framework!
===================
**Selenium automation framework** is not only a java automation framework, but also the best practice of using Selenium.
I used selenium for years. A lot of reason to love him, but a lot of reason to hate him. This integrates a lots of selenium good practice to framework and provide you with reusable component. I listed a lot of **A new way** here. If you are impressed by any of them, please just down it and try it. Buy me a beer if you love it.

This framework provides:
 - A new way to describe and organize you pages.
 - A new way you don't need to validate elements' presence when creating a page.
 - A new way to run test against different browser like IE, Chrome, Firefox and even Selenium Grid
 - A new way to use Select, Checkbox and RadioButton.
 - A new way to handle navigation.
  ...

A new way to describe and organize your pages.
-------------
Do you follow PageObjects pattern? if yes, you already have a good start. Can you help to describe home www.expedia.com? If we describe it in nature language, it should like this:

    A HomePage includes a header, a navigation page, a search component, Deals, and footer.
    - header provides account management, language ...
    - navigation page includes flight, hotel, car ...
    - search includes pickup location, dropoff location ...
    - deals includes ...

In the old way, because java doesn't support multiple inheritance, we may describe it like this:

    class HeaderPage {
	    @Findby(id="account")
	    WebElement accountManagement;
	}

	class NavigationPage extends HeaderPage {
		@FindBy(id="flight")
		WebElement flight;
	}
	class HomePage extends NavigationPage {
	}

As a workaround, we may describe it like this:

    class HeaderPage{}
    class NavigationPage{}
    class HomePage{}
But in order to verify if all WebElement is present in homepage, we need to verify all Page separately:

    HeaderPage header = new HeadPage();
    NavigationPage nav = new NavigationPage();
    HomePage homePage = new HomePage();
    verify(header);
    verify(nav);
    verify(homePage);

And from HomePage itself, it's hard to know which Pages are subPages in HomePage.

This framework provides a nature way to organize and describe your Pages.

**New Code Sample**

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
Do you see the difference? Bingo, Framework introduced Container class, and now you can use **Header** and **Navigation** just the same way as **WebElement**. Navigation and Header will be a reusable component and be used by more pages. If you need it, just define it in your page and add FindBy just like a WebElement, framework will help you do the left.

A new way you don't need to validate elements' presence when creating a page.
-------------
If your HomePage only have one mandatory nav WebElement, Have you written this code:

	public class HomePage{
		@FindBy(id="wizard-theme-wrapper")
		WebElement nav;

		@FindBy(id="footer")
		WebElement footer;

	   public boolean isDisplayed() {
		    try {
			    return nav.isDisplayed();
			} catch (final NoSuchElementException e) {
				return false;
			} catch (final StaleElementReferenceException e)
			{
				return false;
			}
		}

		public doSearch() {
			nav.toCarSearchPage();
		}
	}

    public class HomePageTest {
       @Test
       public void homePageTest () {
    		HomePage page = PageFactory.initElements(driver, HomePage.class);
    		Wait<WebDriver> wait = new WebDriverWait(webDriver, 100);
    		wait.until(new ExpectedCondition<Boolean>() {
    			public Boolean apply(final WebDriver webDriver) {
    				return homePage.isDisplayed();
    			}
    		});

    		page.doSearch();
    	}
    }

You are not the only one. I wrote tons of this kind of test cases before. Now you don't need to do it any more.

**New Code Sample**

    @AutoValidation
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

    public class HomePageTest {
       @Test
       public void homePageTest () {
           HomePage page = new HomePage();
           page.doSearch();
       }
    }

Very simple, right? Just add @AutoValidation to your test Page class, and add @OptionalElement to skip the Element you don't want to auto validate its presence. Even more, you can remove @AutoValidation, by default, the framework helps you verify that all elements should exists when you **new HomePage()**.

A new way to run test against different browser like IE, Chrome, Firefox and even Selenium Grid
-------------
Do you want to make your test case run against IE, Chrome, Firefox and Selenium Grid without any modification? Don’t assume that driver will be an instance of FireFoxDriver or InternetExplorerDriver only. maybe It’s quite easy for you to create a small framework around selenium. Now it's ready for you to use.
Just change the configuration in **config/automation.ini** to the browser you need. we support Firefox, Chrome, IE to run locally and any browser Remotely.

    browserName=chrome
    waitDurationInSeconds=60
    remoteWebDriverAddress=

If you use mvn to do the test, you can even changed the behaviour dynamically by **mvn test -DbrowserName=firefox**.
If you need run against RemoteWebDriver like Selenium Grid, all you  need to do is define a remoteWebDriverAddress. framework would do the left work for you. eg:

    browserName=chrome
    waitDurationInSeconds=60
    remoteWebDriverAddress=http://localhost:4444/wd/hub

If you want to talk to Remote WebDriver and want different DesiredCapabilities, just defined a config file in config/${browserName}.ini. Framework would load this file automatically and negotiate with remote webdriver. Here is an example for firefox

    config/browser/firefox.ini
        browserName=firefox
        version=
        platform=WINDOWS
        javascriptEnabled=true
        cssSelectorsEnabled=true

A new way to handle Ajax pages
-------------
How many times we use FluentWait to wait for a ajax page in our test cases. Do you ever use org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory. now framework makes it very simple and you need to do nothing. By default, all WebElement are initialized with AjaxElementLocatorFactory. If you are using ajaxElement like ajaxElement.click() and the element doesn't exist, it would wait for this element until it timeouts.

    public class HomePage {
        @FindBy(id="ajax")
        @OptionalElement
        WebElement ajaxElement;

        public void doSomething() {
            ajaxElement.click();
        }
    }

If you want to use implicit wait and fail immediately if no element is present, you just add @AjaxEnabled(false) to your class.

    @AjaxEnabled(false)
    public class HomePage {
        @FindBy(id="ajax")
        @OptionalElement
        WebElement ajaxElement;

        public void doSomething() {
            ajaxElement.click();
        }
    }


A new way you don't need to call findElement(s) any more
-------------
I think we have two scenarios we need call findElement by yourself. One is for things like Select and RadioButton. You may have this code:

    Select foo = new Select(sDriver.findElement(By.id("foo")));
    foo.selectByValue("myValue");

Please use SelectBox and make it OptionalElement if it's dynamically created.

    public class HomePage {
        @FindBy(id="ajax")
        @OptionalElement
        SelectBox select;

        public void doSomething() {
           select.selectByIndex(10);
        }
    }


Another one is find element relative to other element. Please use Container(see details in 'A new way to describe tables.')

A new way to use Select, Checkbox and RadioButton.
-------------
Have you written this test case?

    public class DemoPage{
        @FindBy(id="selectdemo")
        WebElement selectElement;

        @FindBy(id="checkboxDemo")
        WebElement checkBoxElement;

        public void selectByValue(String value) {
            new Select(selectElement).selectByValue(value);
        }

        public void check() {
            if (!checkBoxElement.isSelected()) {
                checkBoxElement.click();
            }
        }
    }

Today you can treat Select and CheckBox just as WebElement, and you don't need to create a new Select() any more.

**New Code Sample**

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

A new way to use @Rule annotation.
-------------
If you never use @Rule, I suggest you google it. It's worth your time to learn it and use it in your test framework.
In the old ways, I always write this kind of test case

    class DemoTest {
        private WebDriver driver;

        @Setup
        public void setup() {
            ProfilesIni allProfiles = new ProfilesIni();
            FirefoxProfile profile = allProfiles.getProfile("selenium");
            WebDriver driver = FirefoxDriver(profile);
        }

        @Test
        public void demoTest() {
            driver.navigate().to("https://www.amazon.com")
            HomePage homePage = new HomePage();
            // homePage.initElements(driver);
            // homePage.waitForSomething();
            homePage.toCarSearchPage();
        }
    }

**New Code Sample**


	class DemoTest {
		@Rule
		public AutomationDriver automationDriver = new AutomationDriver();

		@Test
		public void demoTest() {
		   automationDriver.getWebDriver()..navigate().to("https://www.amazon.com")
			HomePage homePage = new HomePage();
			homePage.toCarSearchPage();
		}
	}

**You only need annotate the AutomationDriver with @Rule, automation framework would help you handle all the dirt work**

A new way to handle navigation.
-------------
If you have tens of Pages, It would be a nightmare to navigate between them. A very good practice is never new Page() in your test cases. I always define a Pages.java to handle all navigation.
This framework provide PageHelper to help you create new Pages and navigate between them. Only thing you need to know is which Page class you are navigate to.

**New Code Sample**

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


A new way to handle snapshot
-------------
Have you ever had this clickSubmit() function?

    public class DemoPage {
        public void clickSubmit() {
               // Take snapshot
                takeSnapshot(elementContext);

                // click the element
                webElement.click();

                // Wait for Page Load Complete
                NewPage newPage = new NewPage();
                newPage.waitFor();
            }
    }

Today it be more simple, clickAndToPage will help you.

**New Code Sample**

    public class DemoPage extends Page{
        public void clickSubmit() {
            clickAndToPage(webElement, NewPage.class);
        }
    }

A new way to not full you test case with Fluent wait and avoid sleep
-------------
We ever avoid Thread.sleep prefer Wait or FluentWait in our test cases, now You don't need to write FluentWait function again and again, and you should have a library to support like below:

    public waitForElement(WebElement webElement) {
        try{
            Wait<WebDriver> wait = new WebDriverWait(webDriver, 100);
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(final WebDriver webDriver) {
                    try {
                        element.isDisplayed
                    } catch (final NoSuchElementException e) {
                        return false;
                    }catch (final StaleElementReferenceException e)
                    {
                        return false;
                    }
                }
            });
        } catch (TimeoutException e){
            TimeoutException te = new TimeoutException(message + " timeout!", e);
            throw te;
        }
    }

Today you can just use the existing function in your Page object. it supports you wait for WebElement and any Element created by framework or Element you extended from CustomElement. Check WaitFunctions.java to see all the wait functions the framework supports.

**New Code Sample**

    private void fillLocationField(WebElement webElement, String location) {
        webElement.clear();
        webElement.sendKeys(location);
        waitForElementToBeDisplayed(suggestionComponent);
        suggestionSelectComponent.select(1);
        waitForElementToBeAbsent(suggestionComponent);
    }

A new way you don't need to carry WebDriver everywhere
-------------
If you have a Selenium test project, if you search WebDriver, how many it includes in your test cases? Do we really need it? This framework is trying to hide WebDriver from you. Framework can deduce webDriver from **Global** space or from parent Page object and inject it into Element. If you really want the WebDriver, just call:
   WebDriver webDriver = this.getElementContext().getWebDriver();

A new way you don't need call PageFactory any more.
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

Now this functionality is implement by framework. You simply define you page like this. no constructor(WebDriver webDriver) any more.

**New Code Sample**

    public class HomePage extends Page {
    }

A new way to describe tables.
-------------
In today's project, tables are created dynamically, and often it creates id like 'aria-option-0', 'aria-option-1', 'aria-option-2'.
see example https://github.com/licanhua/selenium-automation/tree/master/selenium-automation-example/src/main/java/framework/datatables

A new way you don't need to care about configuration. (TDB)
-------------
A new way you don't need to handle URL. (TBD)
-------------