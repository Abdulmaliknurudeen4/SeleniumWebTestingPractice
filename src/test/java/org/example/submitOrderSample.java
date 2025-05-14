package org.example;

import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class submitOrderSample extends BaseTest {

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public  void orderTest(String email, String password, String productName) throws IOException {


        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(email, password);
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

    @Test(dataProvider = "getData",dependsOnMethods = {"orderTest"})
    public void orderHistoryTest(String email, String password, String productName) throws IOException {
        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(email, password);
        OrderPage orderPage = productCatalogue.gotoOrdersPage();
        boolean b = orderPage.verifyOrderDisplay(productName);
        assertTrue(b);
    }

    @DataProvider
    public Object[][] getData(){
        //user name, password, and product set set
       return new Object[][] {
               {"abdulmaliknurudeen4@gmail.com", "Password360444#", "IPHONE 13 PRO"},
               {"abdulmaliknurudeen5@gmail.com","Jadam73737##", "ADIDAS ORIGINAL"}
       };
    }
}
