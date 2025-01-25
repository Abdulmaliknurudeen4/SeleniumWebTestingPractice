package org.example.pageObjects;

import org.example.abstractComponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogue extends AbstractComponents {

    private final WebDriver webDriver;
    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement animateDarkScreen;

    @FindBy(css = "[routerlink*='cart']")
    WebElement gotoCartBtn;


    By productsBy = By.cssSelector(".mb-3");
    By addToCartBtn = By.cssSelector(".card-body button:last-of-type");
    By toastPopUp = By.cssSelector("#toast-container");

    public ProductCatalogue(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getProducts() {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String name) {
        return getProducts().stream().filter(product ->
                        product.findElement(By.tagName("b")).getText().contains(name))
                .findFirst().orElse(null);
    }

    public void addProductToCart(String productName) {
        WebElement SelectedProduct = getProductByName(productName);
        SelectedProduct.findElement(addToCartBtn).click();
        this.waitForElementToAppear(toastPopUp);
        this.waitForElementToDisappear(animateDarkScreen);
    }

}
