package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Actions_P1 {
	WebDriver driver;
	Actions action;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name").toLowerCase();

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Hover_to_Element_Tooltip() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		WebElement yourAgeTextbox = driver.findElement(By.id("age"));
		action.moveToElement(yourAgeTextbox).perform();
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ui-tooltip-content")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
	}

	@Test
	public void TC_02_Hover_to_Element() {
		driver.get("https://www.myntra.com/");
		action.moveToElement(driver.findElement(By.xpath("//div[@class='desktop-navLink']//a[text()='Kids']"))).perform();
		sleepInSecond(2);
		driver.findElement(By.xpath("//a[text()='Home & Bath']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("h1.title-title")).getText(), "Kids Home Bath");
	}

	@Test
	public void TC_03_Select_Multiple_Item() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		// Khai báo và lưu trữ lại tất cả 12 element
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		// 1 --> 4
		// Click and hold vào 1 
		// Hover tới 4
		// Nhả chuột trái ra 
		// Thực thi các câu lệnh 
		action.clickAndHold(allNumbers.get(0)).moveToElement(allNumbers.get(3)).release().perform();
		sleepInSecond(2);
		List<WebElement> allNumberSelectted = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumberSelectted.size(), 4);
	}
	
	@Test
	public void TC_03_Select_Multiple_Item_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		// Khai báo và lưu trữ lại tất cả 12 element
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		Keys controlKey;
		if(osName.contains("win")||osName.contains("nux")){
			controlKey = Keys.CONTROL;
		} else {
			controlKey = Keys.COMMAND;
		}
		// 1 5 11
		// Nhấn phím Ctrl xuống (chưa nhả ra)
		// Click vào 1 5 11
		// Thực thi các câu lệnh 
		// Nhả phím Ctrl ra 
		action.keyDown(controlKey).perform();
		action.click(allNumbers.get(0)).click(allNumbers.get(4)).click(allNumbers.get(10)).perform();
		action.keyUp(controlKey).perform();
		sleepInSecond(2);
		List<WebElement> allNumberSelectted = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumberSelectted.size(), 3);
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