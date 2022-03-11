package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Custom_Radio_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Custom_Radio() {
		driver.get("https://material.angular.io/components/radio/examples");
		// dùng thẻ input : Javascript - click (không quan tâm element ẩn hay hiện), isSelected để verify 
		By winterCheckboxInput = By.cssSelector("input[value='Winter']");
		clickByJavascript(winterCheckboxInput);
		Assert.assertTrue(driver.findElement(winterCheckboxInput).isSelected());
	}

	@Test
	public void TC_02_Custom_Checkbox() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		By checkedCheckboxInput = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		By indeterminateCheckboxInput = By.xpath("//span[text()='Indeterminate']/preceding-sibling::span/input");
		clickByJavascript(checkedCheckboxInput);
		clickByJavascript(indeterminateCheckboxInput);
		Assert.assertTrue(driver.findElement(checkedCheckboxInput).isSelected());
		Assert.assertTrue(driver.findElement(indeterminateCheckboxInput).isSelected());
		
		clickByJavascript(checkedCheckboxInput);
		clickByJavascript(indeterminateCheckboxInput);
		Assert.assertFalse(driver.findElement(checkedCheckboxInput).isSelected());
		Assert.assertFalse(driver.findElement(indeterminateCheckboxInput).isSelected());
	}

	@Test
	public void TC_03_Custom_Radio_GoogleDocs() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		By haiphongCityRadioButton = By.xpath("//div[@aria-label='Hải Phòng']");
		By quangnamCityCheckbox = By.xpath("//div[@aria-label='Quảng Nam']");
		By quangbinhCityCheckbox = By.xpath("//div[@aria-label='Quảng Bình']");
		
		Assert.assertEquals(driver.findElement(haiphongCityRadioButton).getAttribute("aria-checked"), "false");
		Assert.assertEquals(driver.findElement(quangnamCityCheckbox).getAttribute("aria-checked"), "false");
		Assert.assertEquals(driver.findElement(quangbinhCityCheckbox).getAttribute("aria-checked"), "false");
		
		driver.findElement(haiphongCityRadioButton).click();
		driver.findElement(quangnamCityCheckbox).click();
		driver.findElement(quangbinhCityCheckbox).click();
		
		Assert.assertEquals(driver.findElement(quangbinhCityCheckbox).getAttribute("aria-checked"), "true");
		Assert.assertEquals(driver.findElement(quangnamCityCheckbox).getAttribute("aria-checked"), "true");
		Assert.assertEquals(driver.findElement(haiphongCityRadioButton).getAttribute("aria-checked"), "true");
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void clickByJavascript(By by) {
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(by));
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}