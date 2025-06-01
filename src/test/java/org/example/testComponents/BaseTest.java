package org.example.testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.example.pageObjects.LandingPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//org//example//properties//GlobalProperties.properties");
        properties.load(fileInputStream);

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
                : properties.getProperty("browser");

        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();

            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            this.driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,999));
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

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

        //read json to string
        String jsonContent = FileUtils.readFileToString(new File(filePath),
                StandardCharsets.UTF_8);
        // String to HasMap Jackson Databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;
    }

    @BeforeMethod
    public LandingPage launchApplication() throws IOException {
        this.driver = this.initializeDriver();
        this.landingPage = new LandingPage(driver);
        landingPage.goTo();
        driver.manage().window().fullscreen();
        return landingPage;
    }

    @AfterMethod
    public void tearDown() {
        this.driver.close();
    }

    public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String filepath = System.getProperty("user.dir") + "//reports//" + testCaseName + "//" + fileName + ".png";
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(filepath);
        FileUtils.copyFile(screenshotAs, file);
        return filepath;
    }
}
