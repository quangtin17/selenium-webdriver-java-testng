package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Locator {
	// Khai báo 1 biến đại diện cho Selenium WebDriver 
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		
		// Khởi tạo browser lên 
		driver = new FirefoxDriver();
		
		// Set thời gian chờ để tìm được element 
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		// Mở 1 trang web 
		driver.get("http://live.techpanda.org/index.php/customer/account/create/");
	}

	@Test
	public void TC_01() {
		// ID
		driver.findElement(By.id("firstname")).sendKeys("quangtin170899@gmail.com");
		sleepInSecond(1);
		
		// Name
		driver.findElement(By.name("lastname")).sendKeys("123456");
		sleepInSecond(1);
		
		// Class
		driver.findElement(By.className("customer-name-middlename")).isDisplayed();
		driver.findElement(By.className("name-middlename")).isDisplayed();
		
		// Tagname
		int inputNumber = driver.findElements(By.tagName("input")).size();
		System.out.println(inputNumber);
		
		// LinkText
		driver.findElement(By.linkText("SEARCH TERMS")).click();
		
		// Partial LinkText
		driver.findElement(By.partialLinkText("ADVANCED")).click();
		
		// CSS
		driver.findElement(By.cssSelector("input[id='name']")).sendKeys("iphone");
		sleepInSecond(1);
		
		driver.findElement(By.cssSelector("input[id='name']")).clear();;
		driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Samsung");
		sleepInSecond(1);
		
		driver.findElement(By.cssSelector("input[name='name']")).clear();;
		driver.findElement(By.cssSelector("#name")).sendKeys("Oppo");
		sleepInSecond(1);
		
		// Xpath
		driver.findElement(By.xpath("//input[@id='description']")).sendKeys("iPhone 13 Promax");
		sleepInSecond(1);
		
		driver.findElement(By.xpath("//input[@id='description']")).clear();
		driver.findElement(By.xpath("//input[@name='description']")).sendKeys("Samsung Note 20 Ultra");
		sleepInSecond(1);
		
		driver.findElement(By.xpath("//input[@name='description']")).clear();
		driver.findElement(By.xpath("//label[text()='Description']/following-sibling::div/input")).sendKeys("Vivo");
		sleepInSecond(1);
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