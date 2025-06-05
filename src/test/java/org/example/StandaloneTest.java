package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class StandaloneTest {
    public static void main(String[] args) {
        //New comments are added for demo
        String username = "abdulmaliknurudeen4@gmail.com";
        String password = "Password360444#";
        String productName = "IPHONE 13 PRO";

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://rahulshettyacademy.com/client/");
        driver.manage().window().fullscreen();


        driver.findElement(By.id("userEmail")).sendKeys(username);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.id("login")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> productCard = driver.findElements(By.cssSelector(".mb-3"));
        WebElement productSelected = productCard.stream().filter(product ->
                        product.findElement(By.tagName("b")).getText().contains(productName))
                .findFirst().orElse(null);

        assert productSelected != null;
        productSelected.findElement(By.cssSelector(".card-body button:last-of-type")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        List<WebElement> cartElements = driver.findElements(By.cssSelector(".cartSection h3"));
        boolean foundProduct = cartElements.stream().anyMatch(p -> p.getText()
                .equalsIgnoreCase(productName));
        assertTrue(foundProduct);

        driver.findElement(By.cssSelector(".totalRow button")).click();

        WebElement autoSuggest = driver.findElement(By.cssSelector("[placeholder*='Country']"));

        Actions a = new Actions(driver);
        a.sendKeys(autoSuggest, "Niger").build().perform();
        //.ta-results
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();
    }
}
