package org.example.pageObjects;

import org.example.abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponents {
    private final WebDriver webDriver;
    @FindBy(css = ".cartSection h3")
    List<WebElement> cartElements;
    @FindBy(css = ".totalRow button")
    WebElement checkOutBtn;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getCartElements() {
        return cartElements;
    }

    public boolean findProduct(String productName) {
        return getCartElements().stream().anyMatch(p -> p.getText()
                .equalsIgnoreCase(productName));
    }

    public CheckOutPage checkOut() {
        checkOutBtn.click();
        return new CheckOutPage(webDriver);
    }
}
