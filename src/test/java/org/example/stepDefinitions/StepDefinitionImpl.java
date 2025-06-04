package org.example.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pageObjects.*;
import org.example.testComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class StepDefinitionImpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public CartPage cartPage;
    public OrderConfirmationPage orderConfirmationPage;

    @Given("I landed on Ecommerce Page")
    public void I_landed_on_Ecommerce_Page() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_password(String username, String password) {
        productCatalogue = landingPage.loginApplication(username, password);
    }

    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String productName) {
        List<WebElement> products = productCatalogue.getProducts();
        productCatalogue.addProductToCart(productName);
    }

    @When("^Checkout (.+) and submit the order$")
    public void check_out_submit_order(String productName) {
        cartPage = productCatalogue.gotoCart();
        boolean findProduct = cartPage.findProduct(productName);
        assertTrue(findProduct);


        CheckOutPage checkOutPage = cartPage.checkOut();
        checkOutPage.fillCountryDetails("Niger");
        checkOutPage.pickCountryAtIndex(1);


        orderConfirmationPage = checkOutPage.checkOut();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_confirmationPage(String confirmationMessage) {
        String confirmationText = orderConfirmationPage.getConfirmationText();
        Assert.assertTrue(confirmationText.equalsIgnoreCase(confirmationMessage));
        driver.close();
    }

    @Then("{string} message is displayed")
    public void incorrect_email_password_confirmation_shown(String message) {
        Assert.assertEquals(landingPage.getErrorMessage(),
                message);
        driver.close();
    }
}
