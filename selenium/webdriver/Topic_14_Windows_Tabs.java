package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Windows_Tabs {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Only_Two_Window_Or_Tab() {
		// Page A : Parent
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		// Lấy ra ID của 1 tab / window mà driver đang đứng (active) 
		String parentTabID = driver.getWindowHandle();
		System.out.println("Parent tab ID = " + parentTabID);
		
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(3);
		switchToTabByID(parentTabID);
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
		String googleTabID = driver.getWindowHandle();
		switchToTabByID(googleTabID);
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
		
		// Click vào facebook link 
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
	}

	@Test
	public void TC_02_Greater_Than_One_Window_Or_Tab() {
		// Page A : Parent
				driver.get("https://automationfc.github.io/basic-form/index.html");
				
				driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
				sleepInSecond(2);
				// Switch Google Tab
				switchToTabByTitle("Google");
				Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
				// Switch Parent Tab
				switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
				Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
				
				// Click vào facebook link 
				driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
				sleepInSecond(2);
				switchToTabByTitle("Facebook");
	}

	@Test
	public void TC_03_Greater_Than_One_Window_Or_Tab() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		
		// Click vào button Add to Compare của sản phẩm (Samsung) 
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Samsung Galaxy has been added to comparison list.");
		
		// Click vào button Add to Compare của sản phẩm (Sony) 
		driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
		
		// Click vào compare button 
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		
		switchToTabByTitle("Products Comparison List - Magento Commerce");
		sleepInSecond(2);
		
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		
		Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "COMPARE PRODUCTS");
		driver.findElement(By.xpath("//button[@title='Close Window']")).click();
		sleepInSecond(2);
		
		switchToTabByTitle("Mobile");
		sleepInSecond(2);

		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		
	}
	
	@Test
	public void TC_04_Greater_Than_One_Window_Or_Tab() {
		driver.get("https://dictionary.cambridge.org/vi/");
		driver.findElement(By.cssSelector("span.cdo-login-button")).click();
		sleepInSecond(2);
		switchToTabByTitle("Login");
		sleepInSecond(2);
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//input[@placeholder='Email *']/following-sibling::span")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@placeholder='Password *']/following-sibling::span")).isDisplayed());
		driver.findElement(By.xpath("//input[@placeholder='Email *']")).sendKeys("automationfc.com@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder='Password *']")).sendKeys("Automation000***");
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
		sleepInSecond(5);
		switchToTabByTitle("Cambridge Dictionary | Từ điển tiếng Anh, Bản dịch & Từ điển từ đồng nghĩa");
		Assert.assertEquals(driver.findElement(By.cssSelector("span.cdo-username")).getText(), "Automation FC");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	// HÀM NÀY CHỈ ĐÚNG CHO 2 TAB / WINDOWS 
	public void switchToTabByID(String expectedID) {
		//  Lấy hết tất cả ID của tab / window đang có 
		Set<String> allTabIDs = driver.getWindowHandles();
		// Dùng vòng lặp để duyệt qua từng ID một 
		for (String id : allTabIDs) { // id : là một biến tạm dùng để duyệt (so sánh) 
			// ID nào mà khác với expectedID truyền vào thì switch qua 
			if (!id.equals(expectedID)) {
				driver.switchTo().window(id);
				break; // dừng vòng lặp khi đã tìm được giá trị thoả điều kiện 
			}
		}
	}
	
	
	// Đúng cho tất cả trường hợp : 2 hoặc nhiều hơn 2 đều đúng 
	public void switchToTabByTitle(String expectedTitle) {
		Set<String> allTabIDs = driver.getWindowHandles();
		for (String id : allTabIDs) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if(actualTitle.equals(expectedTitle)) {
				break;
			}
		}
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}