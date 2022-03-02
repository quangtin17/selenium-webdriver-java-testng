package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Element_Part_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// Set cứng : chỉ chạy được trên máy hiện tại, chuyển qua máy khác cần setting
		// lại đường dẫn
		// System.setProperty("webdriver.gecko.driver", "/Volumes/Code/Fullstack
		// Selenium
		// Java/Software/eclipse/workspace/selenium-webdriver-java-testng/browserDrivers/geckodriver");
		// Set linh động : máy nào cũng chạy được
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01() {
		// Các hàm (function / method) / câu lệnh (command) :
		// Tương tác với Browser --> driver.
		// Tương tác với Element
		// Nếu như element chỉ dùng duy nhất 1 lần thì không cần khai báo biến ngược lại nếu dùng từ 2 lần trở lên thì nên khai báo biến để tái sử 
		WebElement element = driver.findElement(By.xpath(""));
		
		// Xoá dữ liệu trong textbox / textarea / editable dropdown (cho phép edit / nhập liệu) 
		element.clear();
		// Nhập dữ liệu vào trong textbox / textarea / editable dropdown (cho phép edit / nhập liệu) 
		element.sendKeys("");
		// Click 
		element.click();
		
		// Kiểm tra 1 element có hiển thị hay không - sử dụng được cho tất cả loại element 
		element.isDisplayed();
		// --> Mong đợi nó hiển thị 
		Assert.assertTrue(element.isDisplayed());
		// --> Không mong đợi nó hiển thị 
		Assert.assertFalse(element.isDisplayed());
		
		// Kiểm tra 1 element có thể thao tác được hay không / Read-only hoặc disable element 
		element.isEnabled();
		
		// Kiểm tra 1 element đã được chọn hay chưa : Radio Button / CheckBox / 	Dropdown 
		element.isSelected();
		
		// submit = hành động gõ phím enter : chỉ dùng cho thẻ form hoặc element trong thẻ form 
		element.submit();
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