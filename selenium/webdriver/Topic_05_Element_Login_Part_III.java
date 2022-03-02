package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_05_Element_Login_Part_III {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firstName, lastName, middleName, fullName, emailAddress, password;
	
	// Khai báo biến dùng chung 
	By inputEmail = By.xpath("//input[@title='Email Address']");
	By inputPassword = By.xpath("//input[@title='Password']");
	By btnMyAccount = By.xpath("//div[@class='footer-container']//a[text()='My Account']");
	By bntLogin = By.xpath("//button[@title='Login']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		firstName = "Truong";
		middleName = "Quang";
		lastName = "Teo";
		fullName = firstName + " " + middleName + " " + lastName;
		emailAddress = "quangtin" + getRandomNumber() + "@hotmail.net";
		password = "123123123";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(btnMyAccount).click();
	}
	
	@Test
	public void TC_01_Login_With_Empty_Email_And_Password() {
		driver.findElement(inputEmail).sendKeys("");
		driver.findElement(inputPassword).sendKeys("");
		driver.findElement(bntLogin).click();
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
	}

	@Test
	public void TC_02_Login_With_Invalid_Email() {
		driver.findElement(inputEmail).sendKeys("121312@1231.12312321");
		driver.findElement(inputPassword).sendKeys("123456");
		driver.findElement(bntLogin).click();
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Login_With_Password_Lessthan_6_Characters() {
		driver.findElement(inputEmail).sendKeys("quangtin170899@gmail.com");
		driver.findElement(inputPassword).sendKeys("123");
		driver.findElement(bntLogin).click();
		Assert.assertEquals(driver.findElement(By.className("validation-advice")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	@Test
	public void TC_04_Create_New_Account() {
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.findElement(By.xpath("//input[@title='First Name']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@title='Middle Name/Initial']")).sendKeys(middleName);
		driver.findElement(By.xpath("//input[@title='Last Name']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@title='Email Address']")).sendKeys(emailAddress);
		driver.findElement(By.xpath("//input[@title='Password']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@title='Confirm Password']")).sendKeys("123123123");
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page-title']//h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='welcome-msg']//strong")).getText(), "Hello, "+ fullName + "!");
		String contactInformation = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(emailAddress));
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']/a")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='page-title']//img")).isDisplayed());
	}
	
	@Test
	public void TC_05_Login_With_Incorrect_Email_Password() {
		driver.findElement(inputEmail).sendKeys("quangtin17@gmail.com");
		driver.findElement(inputPassword).sendKeys("12121212");
		driver.findElement(bntLogin).click();
		Assert.assertEquals(driver.findElement(By.xpath("//span[contains(text(),'Invalid login')]")).getText(), "Invalid login or password.");
	}
	
	@Test
	public void TC_06_Register_With_Invalid_Phone_Number() {
		driver.findElement(inputEmail).sendKeys(emailAddress);
		driver.findElement(inputPassword).sendKeys(password);
		driver.findElement(bntLogin).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page-title']//h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='welcome-msg']//strong")).getText(), "Hello, "+ fullName + "!");
		String contactInformation = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(emailAddress));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(999);
	}
}