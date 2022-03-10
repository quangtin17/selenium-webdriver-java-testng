package webdriver;

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

public class Topic_09_Button_Default_Radio_Checkbox {
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
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.cssSelector("ul.popup-login-tab>li.popup-login-tab-login")).click();
		By loginButton = By.cssSelector("button.fhs-btn-login");
		String loginButtonColor = driver.findElement(loginButton).getCssValue("background");
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123123123");
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());
		Assert.assertEquals(loginButtonColor, "rgba(0, 0, 0, 0) linear-gradient(90deg, rgb(224, 224, 224) 0%, rgb(224, 224, 224) 100%) repeat scroll 0% 0%");	
		driver.navigate().refresh();
		driver.findElement(By.cssSelector("ul.popup-login-tab>li.popup-login-tab-login")).click();
		
		// Remove disable attribute
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled')",driver.findElement(loginButton));
		driver.findElement(loginButton).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Số điện thoại/Email']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Mật khẩu']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
		
	}

	@Test
	public void TC_02_Default_Radio() {
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		// Default - thẻ input
		By petrolRadio = By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input");
		Assert.assertFalse(driver.findElement(petrolRadio).isSelected());
		driver.findElement(petrolRadio).click();
		Assert.assertTrue(driver.findElement(petrolRadio).isSelected());
		
	}

	@Test
	public void TC_03_Default_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		By dualZoneAir = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
		By rainSensor = By.xpath("//label[text()='Rain sensor']/preceding-sibling::input");
		By rearSideAirbags = By.xpath("//label[text()='Rear side airbags']/preceding-sibling::input");
		if(!driver.findElement(rearSideAirbags).isSelected()) {
			driver.findElement(rearSideAirbags).click();
		}
		Assert.assertFalse(driver.findElement(dualZoneAir).isSelected());
		driver.findElement(dualZoneAir).click();
		Assert.assertTrue(driver.findElement(dualZoneAir).isSelected());
		driver.findElement(dualZoneAir).click();
		Assert.assertFalse(driver.findElement(dualZoneAir).isSelected());
		Assert.assertFalse(driver.findElement(rainSensor).isSelected());
		driver.findElement(rainSensor).click();
		Assert.assertTrue(driver.findElement(rainSensor).isSelected());
	}

	@Test
	public void TC_04_Multiple_Checkbox() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
		// Action - Select
		for (WebElement checkbox : checkboxes) {
			if(!checkbox.isSelected()) {
				checkbox.click();
			}
		}
		// Verify - Selected
		for (WebElement checkbox : checkboxes) {
			Assert.assertTrue(checkbox.isSelected());
		}
		// Action - Deselect
		for (WebElement checkbox : checkboxes) {
			if(checkbox.isSelected()) {
				checkbox.click();
			}
		}
		// Verify - Deselect 
		for (WebElement checkbox : checkboxes) {
			Assert.assertFalse(checkbox.isSelected());
		}
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