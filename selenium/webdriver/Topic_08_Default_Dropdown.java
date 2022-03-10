package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Default_Dropdown {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Select select;
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Actions action;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		// Khởi tạo sau khi driver này được sinh ra 
		// JavascriptExecutor / WebDriverWait/ Actions/...
//		jsExecutor = (JavascriptExecutor) driver;
//		explicitWait = new WebDriverWait(driver, 30);
//		action = new Actions(driver);
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Node() {
		// Khởi tạo khi sử dụng (element xuất hiện) 
		// Khởi tạo select để thao tác với element (country dropdown) 
		driver.get("https://www.rode.com/wheretobuy");
		select = new Select(driver.findElement(By.id("where_country")));
		Assert.assertFalse(select.isMultiple());
		select.selectByVisibleText("Vietnam");
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");
		driver.findElement(By.id("search_loc_submit")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.search_results_count>div.result_count>span")).getText(), "33");
		List<WebElement> storeName = driver.findElements(By.cssSelector("div#search_results div.store_name"));
		Assert.assertEquals(storeName.size(), 33);
		
		for (WebElement store : storeName) {
			System.out.println(store.getText());
		}
	}

	@Test
	public void TC_02_NopCommerce() {
		driver.get("https://demo.nopcommerce.com/register");
		driver.findElement(By.xpath("//a[text()='Register']")).click();
		driver.findElement(By.cssSelector("input#gender-male")).click();
		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("John");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("Wick");
		driver.findElement(By.cssSelector("input#Email")).sendKeys("Johnwick@gmail.com");
		
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']")));
		select.selectByVisibleText("1");
		Assert.assertEquals(select.getOptions().size(), 32);
		
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']")));
		select.selectByVisibleText("May");
		Assert.assertEquals(select.getOptions().size(), 13);
		
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']")));
		select.selectByVisibleText("1980");
		Assert.assertEquals(select.getOptions().size(), 112);
		
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123123123");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("123123123");
		driver.findElement(By.id("register-button")).click();
		sleepInSecond(3);
		driver.findElement(By.className("result")).getText();
		Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");
		driver.findElement(By.xpath("//div[@class='header-links']//a[text()='My account']")).click();
		
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "1");
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "May");
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "1980");
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