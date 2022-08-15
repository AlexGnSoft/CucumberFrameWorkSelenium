package pageObjects;

import constacts.Constants;
import dev.failsafe.internal.util.Assert;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.RandomDataGenerator;
import java.util.*;

public class HomePage {
    public WebDriver driver;
    private static final Logger log = LogManager.getLogger(HomePage.class.getName());

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='sidebar-folders js-tooltip-direction_right']")
    WebElement homePage;

    @FindBy(xpath = "//span[text()='Compose']")
    WebElement sentNewEmailBtn;

    @FindBy(xpath = "//span[@class='badge__text']")
    WebElement sentMailCounter;

    @FindBy(xpath = "//span[@class='ph-notify svelte-syffyg']")
    WebElement newMailCounter;

    @FindBy(xpath = "//div[text()='Messages to myself']")
    WebElement sentMessagesBtn;

    @FindBy(xpath = "//span[@class='ll-sj__normal']")
    List<WebElement> theme;

    @FindBy(xpath = "//span[@class='ll-sp__normal']")
    List<WebElement> body;

    @FindBy(xpath = "//span[text()='Select all']")
    WebElement selectAllBtn;

    @FindBy(xpath = " //div[contains(@class,'heckbox__box checkbox')]")
    List<WebElement> listOfCheckBoxes;

    @FindBy(xpath = "//span[text()='Delete']")
    WebElement deleteBtn;

    @FindBy(xpath = "//div[@class='layer__footer']//span[text()='Empty']")
    WebElement emptyBtn;

    @FindBy(xpath = " //span[text()='No messages in the folder']")
    WebElement noMessageInFolder;

    /**
     * Method is used to verify that home page is fully displayed.
     */
    public void isHomePageIsVisible() {
        wait(3000);
        homePage.isDisplayed();
        log.log(Level.INFO, "isHomePageIsVisible method");
    }

    /**
     * Method is used to enter user recipient's email, randomly created subject and body
     * @strLength - sets length of the randomly created subject and body of the email
     */
    public void fillInToSubjectsBodyFieldsAndClickSend(int strLength) {
        RandomDataGenerator dataGenerator = new RandomDataGenerator();

        //Click on sent new email button
        sentNewEmailBtn.click();
        wait(2000);

        //Enter email
        WebElement to = driver.findElement(By.xpath("//div[@data-name='to']//input[@class='container--H9L5q size_s--3_M-_']"));
        to.sendKeys(Constants.CredentialsVariables.email);

        //Enter theme
        WebElement subject = driver.findElement(By.xpath("//input[@name='Subject']"));
        subject.sendKeys(dataGenerator.randomString(strLength));

        //Enter body of the email
        WebElement emailBodyField = driver.findElement(By.xpath("//div[@role='textbox']/div[1]"));
        emailBodyField.sendKeys(dataGenerator.randomString(strLength));

        //Click on Send button
        WebElement sendBtn = driver.findElement(By.xpath("//button[@data-test-id='send']"));
        sendBtn.click();
        wait(1000);

        //Click on close button
        WebElement closeBtn = driver.findElement(By.xpath("//span[@tabindex='1000']"));
        closeBtn.click();
        log.log(Level.INFO, "fillInToSubjectsBodyFieldsAndClickSend method");
    }

    /**
     * Method is used to send email a certain number of times
     * @qtyOfEmail - sets q-ty of emails to be sent
     * @strLength - sets length of the randomly created subject and body of the email
     */
    public void sendEmailMoreThenOneTime(int qtyOfEmail, int strLength){
        //Clear all sent emails from previous test run
        sentMessagesBtn.click();
        selectAllBtn.click();
        deleteBtn.click();
        emptyBtn.click();
        for (int i = 0; i < qtyOfEmail; i++) {
            fillInToSubjectsBodyFieldsAndClickSend(strLength);
            }
        log.log(Level.INFO, "sendEmailMoreThenOneTime method");
        }

    /**
     * Method is used to verify number of sent and received emails
     */
    public void sentMailCounterTest() {
        String sentMailText = sentMailCounter.getText();
        String newMailText = newMailCounter.getText();
        wait(2000);
        Assert.isTrue(sentMailText.equals(newMailText), "Number of sent and received emails isn't equal");
        log.log(Level.INFO, "sentMailCounterTest method");
    }

    /**
     * Method is used to save Subject (theme) and Body of sent emails
     */
    public Map<String, String> saveEmailsData() {
        //Preconditions: click on sent message button, to see a list of sent emails
        sentMessagesBtn.click();

        //Save theme and body to Map
        Map<String, String> emailsData = new HashMap<>();
        for (int i = 0; i < theme.size(); i++) {
            for (int j = 0; j < body.size(); j++) {
                String eachTheme = theme.get(i).getText();
                String eachBody = body.get(i).getText();
                emailsData.put(eachTheme, eachBody);
            }
        }
        log.log(Level.INFO, "saveEmailsData method");
        return emailsData;
    }

    /**
     * Method is used to get from Map (created by saveEmailsData method) keys, values
     * and send collected data to yourself.
     * The number of times emails is sent equal to number of values stored in the Map
     */
    public void sendCollectedDataToYourSelf(int strLength) {
        //Create a loop to go through Map and get every key and value
        for (Map.Entry<String, String> stringEntry : saveEmailsData().entrySet()) {
            String theme = stringEntry.getKey();
            String body = stringEntry.getValue().replace(" -- Oleksanr Gnuskin Sent from Mail.ru", ""); //replace default text

            //Click on sent new email button
            sentNewEmailBtn.click();
            wait(2000);

            //Enter email
            WebElement to = driver.findElement(By.xpath("//div[@data-name='to']//input[@class='container--H9L5q size_s--3_M-_']"));
            to.sendKeys(Constants.CredentialsVariables.email);

            //Enter customized theme
            WebElement subject = driver.findElement(By.xpath("//input[@name='Subject']"));
            subject.sendKeys("Received mail on theme " + theme);

            //Enter customized body
            WebElement emailBodyField = driver.findElement(By.xpath("//div[@role='textbox']/div[1]"));
            emailBodyField.sendKeys(body + ". It contains "+ countLettersInString(body) + " letters and " + countDigitsInString(body) + " numbers.");

            //Click on Send button
            WebElement sendBtn = driver.findElement(By.xpath("//button[@data-test-id='send']"));
            sendBtn.click();
            wait(1000);

            //Click on close button
            WebElement closeBtn = driver.findElement(By.xpath("//span[@tabindex='1000']"));
            closeBtn.click();
        }
        //Click on sent email button to start a loop
        sentMessagesBtn.click();
        log.log(Level.INFO, "sendCollectedDataToYourSelf method");
    }

    /**
     * Method is used to count letter is a string
     */
    public int countLettersInString(String string) {
        char[] chars = string.toCharArray();
        int numberOfLetters = 0;

        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(chars[i])) {
                numberOfLetters++;
            }
        }
        log.log(Level.INFO, "countLettersInString method");
        return numberOfLetters;
    }

    /**
     * Method is used to count digits is a string
     */
    public int countDigitsInString(String string) {
        char[] chars = string.toCharArray();
        int numberOfDigits = 0;

        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(chars[i])) {
                numberOfDigits++;
            }
        }
        log.log(Level.INFO, "countDigitsInString method");
        return numberOfDigits;
    }

    /**
     * Method suspends the current thread for the specified amount of time.
     * Optionally, could be moved to a specific Global Pages class.
     */
    public static void wait(int waitTime){
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is used to delete all sent email expert the last one
     */
    public void deleteAllReceivedMailsExpectTheLastOne() {
        sentMessagesBtn.click();
        selectAllBtn.click();
        listOfCheckBoxes.get(0).click();
        deleteBtn.click();
        wait(2000);
        String sentMailText = sentMailCounter.getText();
        String newMailText = newMailCounter.getText();
        Assert.isTrue(sentMailText.equals(newMailText),
                "Number of sent and received emails isn't equal. Number of sentEmails is " + sentMailText +
                        ", while number of new emails + " + newMailText + ".");

        log.log(Level.INFO, "deleteAllReceivedMailsExpectTheLastOne method");
    }
}


