package org.example;

import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.assertTrue;

public class submitOrderSample extends BaseTest {

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void orderTest(HashMap<String, String> input) throws IOException {


        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(input.get("email"), input.get("password"));
        productCatalogue.addProductToCart(input.get("product"));


        CartPage cartPage = productCatalogue.gotoCart();
        boolean findProduct = cartPage.findProduct(input.get("product"));
        assertTrue(findProduct);


        CheckOutPage checkOutPage = cartPage.checkOut();
        checkOutPage.fillCountryDetails("Niger");
        checkOutPage.pickCountryAtIndex(1);


        OrderConfirmationPage orderConfirmationPage = checkOutPage.checkOut();
        String confirmationText = orderConfirmationPage.getConfirmationText();
        Assert.assertTrue(confirmationText.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dataProvider = "getData", dependsOnMethods = {"orderTest"})
    public void orderHistoryTest(String email, String password, String productName) throws IOException {
        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(email, password);
        OrderPage orderPage = productCatalogue.gotoOrdersPage();
        boolean b = orderPage.verifyOrderDisplay(productName);
        assertTrue(b);
    }

    @DataProvider
    public Object[][] getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", "abdulmaliknurudeen4@gmail.com");
        map.put("password", "Password360444#");
        map.put("product", "IPHONE 13 PRO");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("email", "abdulmaliknurudeen5@gmail.com");
        map2.put("password", "Jadam73737##");
        map2.put("product", "ADIDAS ORIGINAL");
        //user name, password, and product set set
        return new Object[][]{
                {map},
                {map2}
        };
    }
}
