package org.example;

import org.apache.commons.io.FileUtils;
import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
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
    public void orderHistoryTest(String email, String password, String productName) throws IOException {
        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(email, password);
        OrderPage orderPage = productCatalogue.gotoOrdersPage();
        boolean b = orderPage.verifyOrderDisplay(productName);
        assertTrue(b);
    }

    @DataProvider
    public Object[][] getData() throws IOException {

        List<HashMap<String, String>> jsonDataToMap
                = getJsonDataToMap(STR."\{System.getProperty("user.dir")}//src//test//java//org//example//data//PurchaseOrder.json");

        return jsonDataToMap.stream()
                .map(map -> new Object[]{map})
                .toArray(Object[][]::new);
    }

    public String getScreenShot(String testCaseName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(STR."\{System.getProperty("user.dir")}//reports//"+testCaseName+"//PurchaseOrder.png");
        FileUtils.copyFile(screenshotAs, file);
        return STR."\{System.getProperty("user.dir")}//reports//"+testCaseName+"//PurchaseOrder.png";
    }
}
