package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Element_Condition_Status_P1 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait expliciWait;

	@BeforeClass
	public void beforeClass() {
		// Set cứng : chỉ chạy được trên máy hiện tại, chuyển qua máy khác cần setting
		// lại đường dẫn
		// System.setProperty("webdriver.gecko.driver", "/Volumes/Code/Fullstack
		// Selenium
		// Java/Software/eclipse/workspace/selenium-webdriver-java-testng/browserDrivers/geckodriver");
		// Set linh động : máy nào cũng chạy được
		// System.setProperty("webdriver.gecko.driver", projectPath +
		// "/browserDrivers/geckodriver");
		// setting chạy trên nhiều hệ điều hành
		if (osName.startsWith("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else if (osName.startsWith("Mac")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver_linux");
		}
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		expliciWait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Visible() {
		// Visible : Có trên UI và có trong DOM/HTML 
		driver.get("https://www.facebook.com/");
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email']")));
		Assert.assertTrue(driver.findElement(By.xpath("//input[@name='email']")).isDisplayed());
	}

	@Test
	public void TC_02_Invisible_In_DOM() {
		// Invisible : Không có trên UI và có trong Dom ( không bắt buộc ) 
		// Kết quả như nhau nhưng thời gian chạy của mỗi case khác nhau 
	
		driver.get("https://www.facebook.com/");
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		// ~1s 
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
		// Không hiển thị -> Pass -> ~1s 
		Assert.assertFalse(driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']")).isDisplayed());
	}
	
	@Test
	public void TC_02_Invisible_Not_In_DOM() {
		// Invisible : Không có trên UI và k có trong Dom ( không bắt buộc ) 
		driver.get("https://www.facebook.com/"); 
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		// Chạy mất 15s 
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
		// Không hiển thị -> Failed -> 20s 
		Assert.assertFalse(driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']")).isDisplayed());
	}

	@Test
	public void TC_03_Presence() {
		driver.get("https://www.facebook.com/");
		// Presence : Có trong DOM/HTML và có trên UI -> Pass 
		expliciWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='email']")));
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		// Presence : Có trong DOM/HTML và không có trên UI -> Pass 
		expliciWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}
	
	@Test
	public void TC_04_Staleness() {
		driver.get("https://www.facebook.com/");
		// Bật Pop-up register lên 
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		sleepInSecond(2);
		// Tại thời điểm này element đang có trong DOM, nhưng không hiển thị trên UI 
		WebElement confirmationEmailAddressTextbox = driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']"));
		// Đóng Pop-up register
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		// Wait cho confirmationEmailAddressTextbox k còn trong DOM nữa 
		// Vì 1 lí do nào đó mình cần wait cho nó không còn tồn tại trong DOM nữa 
		expliciWait.until(ExpectedConditions.stalenessOf(confirmationEmailAddressTextbox));
		
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