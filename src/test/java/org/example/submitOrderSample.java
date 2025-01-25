package org.example;

import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class submitOrderSample extends BaseTest {
    String username = "abdulmaliknurudeen4@gmail.com";
    String password = "Password360444#";
    String productName = "IPHONE 13 PRO";

    @Test
    public  void orderTest() throws IOException {


        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(username, password);
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
    }

    @Test(dependsOnMethods = {"orderTest"})
    public void orderHistoryTest() throws IOException {
        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(username, password);
        OrderPage orderPage = productCatalogue.gotoOrdersPage();
        boolean b = orderPage.verifyOrderDisplay(productName);
        assertTrue(b);
    }
}
