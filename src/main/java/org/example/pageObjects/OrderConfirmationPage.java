package org.example.pageObjects;

import org.example.abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage extends AbstractComponents {
    private final WebDriver webDriver;
    @FindBy(css = ".hero-primary")
    WebElement confirmMessageTxt;

    public OrderConfirmationPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getConfirmationText() {
        return confirmMessageTxt.getText();
    }
}
