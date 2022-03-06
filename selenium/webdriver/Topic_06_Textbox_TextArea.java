package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Textbox_TextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firstName, lastName, editFirstName, editLastName;
	
	By FirstNameText = By.id("personal_txtEmpFirstName");
	By LastNameTextbox = By.id("personal_txtEmpLastName");
	By EmployeeIDTextbox = By.id("personal_txtEmployeeId");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		driver = new ChromeDriver();
		
		firstName = "quang";
		lastName = "tin";
		editFirstName = "Le";
		editLastName = "Hoang";
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.findElement(By.cssSelector("div>input#txtUsername")).sendKeys("Admin");
		driver.findElement(By.cssSelector("div>input#txtPassword")).sendKeys("admin123");
		driver.findElement(By.cssSelector("div>#btnLogin")).click();
		sleepInSecond(5);
		driver.findElement(By.xpath("//b[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(lastName);
		
		String employeeID = driver.findElement(By.id("employeeId")).getAttribute("value");
		driver.findElement(By.id("btnSave")).click();
		sleepInSecond(3);
		// Verify FirstName LastName EmployeeID bị disable 
		Assert.assertFalse(driver.findElement(FirstNameText).isEnabled()); 
		Assert.assertFalse(driver.findElement(LastNameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(EmployeeIDTextbox).isEnabled());
		
		Assert.assertEquals(driver.findElement(FirstNameText).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(LastNameTextbox).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(EmployeeIDTextbox).getAttribute("value"), employeeID);
		
		driver.findElement(By.id("btnSave")).click();
		
		Assert.assertTrue(driver.findElement(FirstNameText).isEnabled());
		Assert.assertTrue(driver.findElement(LastNameTextbox).isEnabled());
		
		driver.findElement(FirstNameText).clear();
		driver.findElement(FirstNameText).sendKeys(editFirstName);
		driver.findElement(LastNameTextbox).clear();
		driver.findElement(LastNameTextbox).sendKeys(editLastName);
		driver.findElement(By.id("btnSave")).click();
		sleepInSecond(3);
		
		Assert.assertFalse(driver.findElement(FirstNameText).isEnabled()); 
		Assert.assertFalse(driver.findElement(LastNameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(EmployeeIDTextbox).isEnabled());
		
		Assert.assertEquals(driver.findElement(FirstNameText).getAttribute("value"), editFirstName);
		Assert.assertEquals(driver.findElement(LastNameTextbox).getAttribute("value"), editLastName);
		
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		driver.findElement(By.xpath("//p[@id='listActions']/input[@value='Add']")).click();
		
		String PassportID = "710592014";
		String comments = "Lorem Ipsum\nis simply\ndummy text of the\nprinting and typesetting\nindustry.";
		
		driver.findElement(By.cssSelector("input#immigration_number")).sendKeys(PassportID);
		driver.findElement(By.cssSelector("textarea#immigration_comments")).sendKeys(comments);
		
		driver.findElement(By.id("btnSave")).click();
		sleepInSecond(5);
		driver.findElement(By.xpath("//td[@class='document']/a[text()='Passport']")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("input#immigration_number")).getAttribute("value"), PassportID);
		Assert.assertEquals(driver.findElement(By.cssSelector("textarea#immigration_comments")).getAttribute("value"), comments);
		
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
}