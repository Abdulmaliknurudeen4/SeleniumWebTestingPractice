package org.example;

import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class submitOrderSample extends BaseTest {

    @Test
    public  void main() throws IOException {
        String username = "abdulmaliknurudeen4@gmail.com";
        String password = "Password360444#";
        String productName = "IPHONE 13 PRO";

        ProductCatalogue productCatalogue = super.launchApplication()
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
        driver.close();
    }
}
