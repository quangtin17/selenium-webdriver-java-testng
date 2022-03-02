package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_05_Element_Register_Part_III {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	// Khai báo biến dùng chung 
	By fullNameTextboxBy = By.id("txtFirstname");
	By emailTextboxBy = By.id("txtEmail");
	By confirmEmailTextboxBy = By.id("txtCEmail");
	By passwordTextboxBy = By.id("txtPassword");
	By confirmPasswordTextboxBy = By.id("txtCPassword");
	By phoneTextboxBy = By.id("txtPhone");
	By registerButtonBy = By.xpath("//form[@id='frmLogin'//button[text()='ĐĂNG KÝ']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
	}
	
	@Test
	public void TC_01_Register_With_Empty_Data() {
		driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();	
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtFirstname-error']")).getText(), "Vui lòng nhập họ tên");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtEmail-error']")).getText(), "Vui lòng nhập email");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCEmail-error']")).getText(), "Vui lòng nhập lại địa chỉ email");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPassword-error']")).getText(), "Vui lòng nhập mật khẩu");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(), "Vui lòng nhập lại mật khẩu");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPhone-error']")).getText(), "Vui lòng nhập số điện thoại.");
	}

	@Test
	public void TC_02_Register_With_Invalid_Email() {
		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(emailTextboxBy).sendKeys("quasah@iwqeio@asdia");
		driver.findElement(confirmEmailTextboxBy).sendKeys("quasah@iwqeio@asdia");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0978567456");
		driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtEmail-error']")).getText(), "Vui lòng nhập email hợp lệ");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCEmail-error']")).getText(), "Vui lòng nhập email hợp lệ");
	}

	@Test
	public void TC_03_Register_With_Incorrect_Confirm_Email() {
		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("Truong Quang Tin");
		driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("quangtin17089999@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0978567456");
		driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCEmail-error']")).getText(), "Email nhập lại không đúng");
	}
	
	@Test
	public void TC_04_Register_With_Password_Lessthan_6_Characters() {
		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("Truong Quang Tin");
		driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0978567456");
		driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPassword-error']")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
	}
	
	@Test
	public void TC_05_Register_With_Incorrect_Confirm_Password() {
		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("Truong Quang Tin");
		driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0978567456");
		driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(), "Mật khẩu bạn nhập không khớp");
	}
	
	@Test
	public void TC_06_Register_With_Invalid_Phone_Number() {
		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("Truong Quang Tin");
		driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("quangtin170899@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0978567456232323");
		driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPhone-error']")).getText(), "Số điện thoại phải từ 10-11 số.");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).clear();
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("123456");
		driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPhone-error']")).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019");
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