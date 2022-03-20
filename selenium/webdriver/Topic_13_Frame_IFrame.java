package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Frame_IFrame {
	WebDriver driver;
	Select select;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	// Handle Frame/ IFrame giống nhau 
	@Test
	public void TC_01_Iframe() {
		driver.get("https://kyna.vn/");
		// Switch vào frame / iframe trước rồi mới thao tác lên element thuộc frame / iframe đó 
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.face-content>iframe")));
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText(), "167K likes");
		// Switch iframe
		driver.switchTo().defaultContent();
		driver.switchTo().frame("cs_chat_iframe");
		driver.findElement(By.cssSelector("div.meshim_widget_Widget")).click();
		driver.findElement(By.xpath("//div[@class='editing field profile_field']//label[contains(text(),'Nhập thông tin ')]/following-sibling::input[@placeholder='Nhập tên của bạn']")).sendKeys("thaile");
		driver.findElement(By.xpath("//div[@class='editing field profile_field']//label[contains(text(),'Nhập thông tin ')]/following-sibling::input[@placeholder='Nhập số điện thoại của bạn']")).sendKeys("0978567456");
		select = new Select(driver.findElement(By.xpath("//label[@class='label']/following-sibling::select")));
		select.selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("tesstsdlhaldaldka");
		driver.switchTo().defaultContent();
		driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys("Excel");
		driver.findElement(By.cssSelector("button.search-button")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector("span.menu-heading")).isDisplayed());
		// Verify keyword
		List<WebElement> courseName = driver.findElements(By.cssSelector("div.content>h4"));
		for (WebElement course : courseName) {
			System.out.println(course.getText());
			Assert.assertTrue(course.getText().contains("Excel"));
		}
	}

	@Test
	public void TC_02_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		driver.switchTo().frame("login_page");
		driver.findElement(By.xpath("//input[@name='fldLoginUserId']")).sendKeys("123123123");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//input[@name='fldPassword']")).isDisplayed());
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