package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Alert_Authentication_Alert {
	WebDriver driver;
	Alert alert;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
	}

	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Ok");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
	}

	@Test
	public void TC_03_Promt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys("quangtin");
		alert.accept();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: quangtin");
	}
	
	@Test
	public void TC_04_Promt_Alert_I() {
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.example>p")).getText(), "Congratulations! You must have the proper credentials.");
	}
	
	@Test
	public void TC_04_Promt_Alert_II() {
		String username = "admin";
		String password = "admin";
		
		driver.get("https://the-internet.herokuapp.com/");
		String basicAuthenLink = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		driver.get(getAuthenticateLink(basicAuthenLink, username, password));
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'You must have the proper')]")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public String getAuthenticateLink(String url, String username, String password) {
		String[] links = url.split("//");
		url = links[0] + "//" + username + ":" + password + "@" + links[1];
		return url;
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}