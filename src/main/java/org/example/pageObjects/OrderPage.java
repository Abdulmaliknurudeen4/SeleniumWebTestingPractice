package org.example.pageObjects;

import org.example.abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrderPage extends AbstractComponents {
    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> cartElements;

    public OrderPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getCartElements() {
        return cartElements;
    }

    public boolean verifyOrderDisplay(String productName) {
        return getCartElements().stream().anyMatch(p -> p.getText()
                .equalsIgnoreCase(productName));
    }

}
