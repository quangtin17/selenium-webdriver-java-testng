package webdriver;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Random_Popup {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Random_In_DOM_VNK() {
		// Step 1
		driver.get("https://vnk.edu.vn/");
		sleepInSecond(15);
		// Step 2 - Luôn có element trong DOM dù có hiển thị hoặc không 
		if (driver.findElement(By.cssSelector("div#tve_editor")).isDisplayed()) {
			// Close popup trong này 
			driver.findElement(By.cssSelector("div.tcb-icon-display")).click();
			sleepInSecond(2);
			
			// Expected popup ko còn hiển thị nữa 
			Assert.assertFalse(driver.findElement(By.cssSelector("div#tve_editor")).isDisplayed());
		}
		
		
		// Case 1 : Nếu như popup hiển thị thì có thể thao tác với popup rồi close nó đi --> qua step tiếp theo 
		// Case 2 : Nếu như popup không hiển thị thì qua step tiếp theo luôn 
		
		// Step 3 - Click vào trang Liên hệ 
		driver.findElement(By.xpath("//a[@title='Liên hệ']")).click();
		// Step 4 - Verify qua trang Liên hệ thành công 
		Assert.assertTrue(driver.findElement(By.cssSelector("div.title-content>h1")).isDisplayed());
	}

	@Test
	public void TC_02_Random_In_DOM_KMPayer() {
		driver.get("https://www.kmplayer.com/");
		//sleepInSecond(15);
		if (driver.findElement(By.cssSelector("div.pop-conts")).isDisplayed()) {
			jsExecutor.executeScript("arguments[0].click();",driver.findElement(By.cssSelector("area#btn-r")));
			sleepInSecond(2);
			Assert.assertFalse(driver.findElement(By.cssSelector("div.pop-conts")).isDisplayed());
		}
		driver.findElement(By.xpath("//a[contains(text(), 'MOVIEBLOC ')]")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector("section.main_top_banner")).isDisplayed());
	}
	
	@Test
	public void TC_03_Random_Not_In_DOM_DeHieu() {
		driver.get("https://dehieu.vn/");
		// Test để vào được case 1 
		sleepInSecond(10);
		System.out.println("Find popup - Start: " + getCurrentDateTime());
		List<WebElement> popupContent = driver.findElements(By.cssSelector("div.popup-content"));
		System.out.println("Find popup - End: " + getCurrentDateTime());
		
		// Step 2 - Cố tình dùng cách ở 2 testcase trên để làm 
		if (popupContent.size() > 0) {
			System.out.println("Case 1 - Nếu như popup hiển thị thì có thể thao tác với popup rồi close đi --> qua step tiếp theo");
			
		// Thao tác với Popup 
			driver.findElement(By.cssSelector("input#popup-name")).sendKeys("John Wick");
			driver.findElement(By.cssSelector("input#popup-email")).sendKeys("johnwick@gmail.com");
			driver.findElement(By.cssSelector("input#popup-phone")).sendKeys("0978567345");
			sleepInSecond(4);
			
			// Close popup
			driver.findElement(By.cssSelector("button#close-popup")).click();
			sleepInSecond(2);
			
			// Verify popup không hiển thị nữa 
			System.out.println("Verify popup - Start: " + getCurrentDateTime());
			Assert.assertEquals(driver.findElements(By.cssSelector("div.popup-content")).size(), 0);
			System.out.println("Verify popup - End: " + getCurrentDateTime());
		} else {
			//  Nếu như setting cho app vào ngày xxx nào đó sẽ hiện popup để chạy chương trình marketing - có popup 
			// Sau thời gian này thì setting nó không hiển thị popup nữa : mở page ra nó không render cái popup này nữa 
			// không có trong DOM ngay từ đầu luôn 
			System.out.println("Case 2 - Nếu như popup không hiển thị thì qua step tiếp theo luôn"); 
		}
		// Step 3
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		// Step 4
		Assert.assertTrue(driver.findElement(By.cssSelector("input#search-courses")).isDisplayed());
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

	public String getCurrentDateTime() {
		return new Date().toString();
	}
}