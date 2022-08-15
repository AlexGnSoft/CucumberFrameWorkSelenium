package pageObjects;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public WebDriver driver;
    private static final Logger log = LogManager.getLogger(LoginPage.class.getName());

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@class='ph-login svelte-1hiqrvn']")
    WebElement enterToLogInBtn;

    @FindBy(xpath = "//input[@placeholder='Account name']")
    WebElement userNameField;

    @FindBy(xpath = "//span[text()='Enter password']")
    WebElement enterPasswordBtn;

    @FindBy(xpath = "//input[@name='password']")
    WebElement passwordNameField;

    @FindBy(xpath = "//button[@type='submit']/span")
    WebElement enterBtn;

    @FindBy(xpath = "//div[@class='grid__main-col svelte-2y66pa']")
    WebElement landingPage;

    @FindBy(xpath = "//a[@class='cmpboxbtn cmpboxbtnyes cmptxt_btn_yes']")
    WebElement acceptButton;

    @FindBy(xpath = "//iframe[@class='ag-popup__frame__layout__iframe']")
    WebElement signInIFrame;

    public void isLandingPageIsDisplayed(){
        landingPage.isDisplayed();
    }

    public void clickOnEnterToLoginButton(){
        enterToLogInBtn.click();
    }

    public void clickOnEnterButton(){
        enterBtn.click();
    }

    public void clickOnEnterPasswordButton(){
        enterPasswordBtn.click();
    }

    public void clickOnAcceptButton(){
        acceptButton.click();
    }

    /**
     * Method is used to enter email
     */
    public void enterUserEmail(String userEmail){
        HomePage.wait(3000);
        driver.switchTo().frame(signInIFrame);  //switch to iframe to enter user credentials
        userNameField.clear();
        userNameField.sendKeys(userEmail);
        log.log(Level.INFO, "enterUserEmail method");
    }

    /**
     * Method is used to enter password
     */
    public void enterPassword(String password){
        passwordNameField.clear();
        passwordNameField.sendKeys(password);
        log.log(Level.INFO, "enterPassword method");
    }
}

