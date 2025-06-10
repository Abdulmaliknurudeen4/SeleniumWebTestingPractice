package org.example.remoteExecution;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RSATest {

	
	
	@Test
	public void HomePageCheck() throws MalformedURLException, URISyntaxException {
		DesiredCapabilities caps = new DesiredCapabilities();
		
		//caps.setPlatform("");
//		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		
		
		WebDriver driver = new RemoteWebDriver(new URI("http://172.22.112.1:4444").toURL(), caps);
		driver.get("http://rahulshettyacademy.com");
		System.out.println(driver.getTitle());
		driver.close();
		
	
		
	}
}
