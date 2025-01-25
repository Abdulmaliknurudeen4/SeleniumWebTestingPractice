package org.example;

import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class SubmitOrderSample extends BaseTest {
    public static void main(String[] args) {
        String username = "abdulmaliknurudeen4@gmail.com";
        String password = "Password360444#";
        String productName = "IPHONE 13 PRO";

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        driver.manage().window().fullscreen();


        ProductCatalogue productCatalogue = landingPage.loginApplication(username, password);
        productCatalogue.addProductToCart(productName);


        CartPage cartPage = productCatalogue.gotoCart();
        boolean findProduct = cartPage.findProduct(productName);
        assertTrue(findProduct);


        CheckOutPage checkOutPage = cartPage.checkOut();
        checkOutPage.fillCountryDetails("Niger");
        checkOutPage.pickCountryAtIndex(1);


        OrderConfirmationPage orderConfirmationPage = checkOutPage.checkOut();
        String confirmationText = orderConfirmationPage.getConfirmationText();
        Assert.assertTrue(confirmationText.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();
    }
}
