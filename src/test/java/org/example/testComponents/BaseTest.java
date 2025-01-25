package org.example.testComponents;

import org.example.pageObjects.LandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;

    public WebDriver initializeDriver() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(STR."\{System.getProperty("user.dir")}//src//test//java//org//example//properties//GlobalProperties.properties");
        properties.load(fileInputStream);
        String browserName = properties.getProperty("browser");
        if (browserName.equalsIgnoreCase("chrome")) {
            this.driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            this.driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            this.driver = new EdgeDriver();
        } else {
            this.driver = new ChromeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().fullscreen();
        return driver;
    }

    public LandingPage launchApplication() throws IOException {
        WebDriver webDriver = this.initializeDriver();
        LandingPage landingPage = new LandingPage(webDriver);
        landingPage.goTo();
        driver.manage().window().fullscreen();
        return landingPage;
    }
}
