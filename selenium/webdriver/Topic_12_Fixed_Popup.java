package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Fixed_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_ngoaingu24h() {
		driver.get("https://ngoaingu24h.vn/");
		By popupLogin = By.cssSelector("div#modal-login-v1");
		Assert.assertFalse(driver.findElement(popupLogin).isDisplayed());
		driver.findElement(By.cssSelector("button.login_ ")).click();
		Assert.assertTrue(driver.findElement(popupLogin).isDisplayed());
		driver.findElement(By.cssSelector("input#account-input")).sendKeys("quangtin71@gmail.com");
		driver.findElement(By.cssSelector("input#password-input")).sendKeys("170899");
		driver.findElement(By.cssSelector("button.btn-login-v1")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("div#modal-login-v1 div.error-login-panel")).getText(), "Tài khoản không tồn tại!");
		driver.findElement(By.cssSelector("div#modal-login-v1 button.close")).click();
		Assert.assertFalse(driver.findElement(popupLogin).isDisplayed());
	}

	@Test
	public void TC_02_jtexpress() {
		driver.get("https://jtexpress.vn/");
		By popupModal = By.cssSelector("div#home-modal-slider");
		Assert.assertTrue(driver.findElement(popupModal).isDisplayed());
		driver.findElement(By.cssSelector("button.close")).click();
		sleepInSecond(2);
		Assert.assertFalse(driver.findElement(popupModal).isDisplayed());
		driver.findElement(By.cssSelector("input#billcodes")).sendKeys("1212121212121212");
		driver.findElement(By.xpath("//div[@class='tabs-header']//button[text()='Tra cứu vận đơn']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("h5.font-weight-bold")).getText(), "Mã vận đơn: 1212121212121212");
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