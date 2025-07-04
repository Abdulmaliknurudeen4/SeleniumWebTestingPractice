package org.example;

import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
    public void orderHistoryTest(HashMap<String, String> input) throws IOException {
        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(input.get("email"), input.get("password"));
        OrderPage orderPage = productCatalogue.gotoOrdersPage();
        boolean b = orderPage.verifyOrderDisplay(input.get("product"));
        assertTrue(b);
    }

    @DataProvider
    public Object[][] getData() throws IOException {

        List<HashMap<String, String>> jsonDataToMap
                = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//org//example//data//PurchaseOrder.json");

        return jsonDataToMap.stream()
                .map(map -> new Object[]{map})
                .toArray(Object[][]::new);

    }
}
