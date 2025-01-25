package org.example.pageObjects;

import org.example.abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponents {
    @FindBy(id = "userEmail")
    WebElement userEmailTxt;
    @FindBy(id = "userPassword")
    WebElement userPasswordTxt;
    @FindBy(id = "login")
    WebElement logInBtn;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    private final WebDriver webDriver;

    public LandingPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public ProductCatalogue loginApplication(String username, String password){
        userEmailTxt.sendKeys(username);
        userPasswordTxt.sendKeys(password);
        logInBtn.click();

        return new ProductCatalogue(webDriver);
    }

    public String getErrorMessage(){
        waitForElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public void goTo(){
        webDriver.get("https://rahulshettyacademy.com/client/");
    }
}
