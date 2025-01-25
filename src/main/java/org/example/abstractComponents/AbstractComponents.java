package org.example.abstractComponents;

import org.example.pageObjects.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractComponents {
    private final WebDriver webDriver;

    @FindBy(css = "[routerlink*='cart']")
    WebElement gotoCartBtn;

    public AbstractComponents(WebDriver driver) {
        this.webDriver = driver;
    }

    public void waitForElementToAppear(By byPath) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(byPath));
    }

    public void waitForElementToAppear(WebElement byPath) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(byPath));
    }

    public void waitForElementToDisappear(WebElement byPath) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(byPath));
    }

    public CartPage gotoCart(){
        gotoCartBtn.click();
       return new CartPage(webDriver);
    }
}
