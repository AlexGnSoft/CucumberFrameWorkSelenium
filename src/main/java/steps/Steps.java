package steps;

import constacts.Constants;
import helpfiles.PropertiesFile;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import java.time.Duration;

public class Steps {
    public WebDriver driver;
    public LoginPage loginPage;
    public HomePage homePage;

    /**
     * Method creates webDriver and run test on a certain browser, depending on set browser in config.properties.
     */
    @Before(order = 0)
    public void setUp(){
        PropertiesFile propertiesFile = new PropertiesFile();
        switch (propertiesFile.getBrowser()){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", propertiesFile.getDriverPath_Chrome());
                driver = new ChromeDriver();
                driver.get(propertiesFile.getApplicationUrl());
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", propertiesFile.getDriverPath_FireFox());
                driver = new FirefoxDriver();
                driver.get(propertiesFile.getApplicationUrl());
                break;
            default:
                System.out.println("Browser name specified in Config class is not equal to : " + propertiesFile.getBrowser());
        }
        assert driver != null;
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.TimeoutVariables.IMPLICIT_WAIT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constants.TimeoutVariables.PAGE_LOAD_WAIT));
    }

    /**
     * Method quit the whole browser session along with the associated browser windows, tabs and pop-ups.
     */
    @After(order = 0)
    public void tearDown() {
        if(driver != null){
            driver.quit();
        }
    }

    @Given("User is on mail.ru landing page")
    public void user_is_on_mailru_landing_page() {
        loginPage = new LoginPage(driver);
        loginPage.isLandingPageIsDisplayed();
        loginPage.clickOnAcceptButton();
    }

    @Then("User clicks on enter to start login button")
    public void user_clicks_on_enter_to_start_login_button() {
        loginPage = new LoginPage(driver);
        loginPage.clickOnEnterToLoginButton();
    }

    @When("^User enters (.*) and (.*)$")
    public void user_enters_email_and_password(String email, String  password) {
        loginPage = new LoginPage(driver);
        loginPage.enterUserEmail(email);
        loginPage.clickOnEnterPasswordButton();
        loginPage.enterPassword(password);
        loginPage.clickOnEnterButton();
    }

    @Then("User is navigated to the mailbox home page")
    public void user_is_navigated_to_the_mailbox_home_page() {
        homePage = new HomePage(driver);
        homePage.isHomePageIsVisible();
    }

    @And("User sends email more then one time")
    public void user_sends_email_more_then_one_time() {
        homePage = new HomePage(driver);
        homePage.sendEmailMoreThenOneTime(10, 10);
    }

    @Then("Verify messages counters")
    public void verify_messages_counters() {
        homePage.sentMailCounterTest();
    }

    @Then("Save data from each email")
    public void save_Data_From_Each_Email() {
        homePage.saveEmailsData();
    }

    @And("Send collected data to yourself")
    public void send_Collected_Data_To_Yourself() {
            homePage.sendCollectedDataToYourSelf(10);
    }

    @Then("Delete all received mails except the last one")
    public void delete_All_Received_Mails_Except_The_Last_One() {
            homePage.deleteAllReceivedMailsExpectTheLastOne();
    }
}
