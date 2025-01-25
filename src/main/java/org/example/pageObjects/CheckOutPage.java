package org.example.pageObjects;

import org.example.abstractComponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckOutPage extends AbstractComponents {
    private final WebDriver webDriver;
    @FindBy(css = "[placeholder*='Country']")
    WebElement autoSuggestDropDown;
    @FindBy(xpath = "(//button[contains(@class,'ta-item')])")
    List<WebElement> suggestedCountry;
    @FindBy(css = ".action__submit")
    WebElement checkOutBtn;
    By suggestions = By.cssSelector(".ta-results");

    public CheckOutPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void fillCountryDetails(String country) {
        Actions a = new Actions(webDriver);
        a.sendKeys(autoSuggestDropDown, country).build().perform();
        this.waitForElementToAppear(suggestions);
    }

    public void pickCountryAtIndex(int i) {
        if (suggestedCountry.size() < i) {
            //invalid pick
            suggestedCountry.getFirst().click();
        }
        suggestedCountry.get(i - 1).click();
    }

    public OrderConfirmationPage checkOut() {
        checkOutBtn.click();
        return new OrderConfirmationPage(webDriver);
    }
}
