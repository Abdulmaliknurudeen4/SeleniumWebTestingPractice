package org.example;

import org.example.pageObjects.ProductCatalogue;
import org.example.testComponents.BaseTest;
import org.example.testComponents.Retry;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidationExample extends BaseTest {

    @Test(groups = {"errorHandling"}, retryAnalyzer = Retry.class)
    public  void main() throws IOException {
        String username = "abdulmalrudeen4@gmail.com";
        String password = "Password360444#";
        String productName = "IPHONE 13 PRO";

        ProductCatalogue productCatalogue = launchApplication()
                .loginApplication(username, password);
        Assert.assertEquals(landingPage.getErrorMessage(),
                "Incorrect email or password.");

    }
}
