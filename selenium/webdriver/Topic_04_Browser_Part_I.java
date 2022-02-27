package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Browser_Part_I {
	WebDriver driver;
	WebElement element;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Browser() {
		// Các hàm / method để thao tác với Browser là thông qua biến driver 
		
		// Mở ra 1 Url 
		driver.get("https://www.selenium.dev/");
		// Đóng trình duyệt 
		driver.close(); // đóng tab hiện tại 
		driver.quit(); // đóng cả trình duyệt 
		// Tìm 1 elenment trên page --> Trả về data type là WebElement 
		 WebElement emailTextbox = driver.findElement(By.id("email"));
		// Tìm nhiều hơn 1 element trên page --> Trả về data type là Lis<WebElement>
		List<WebElement> links =  driver.findElements(By.xpath("//a"));
		
		// Trả về Url của page hiện tại 
		String homePageUrl = driver.getCurrentUrl();
		System.out.println(homePageUrl);
		
		// Verify dữ liệu này đúng như mong đợi 
		Assert.assertEquals(homePageUrl, "https://www.youtube.com/?&ab_channel=Nhiychi");
		// Cách viết khác : 
		// Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/?&ab_channel=Nhiychi");
		
		// get source code của page hiện tại : dùng để verify tương đối 1 giá trị nào đó có trong trang 
		driver.getPageSource();
		
		// lấy ra title của page hiện tại 
		driver.getTitle();
		
		// Bài WebDriver API - Windows / Tabs : sẽ học kĩ 
		// Trả về 1 ID của tab hiện tại (1)
		String sighUpTabID = driver.getWindowHandle(); 
		// Trả về ID của tất cả các tab đang có (n) 
		Set<String> allTabID = driver.getWindowHandles();
		
		// Bài Xử lý cookie sẽ học về phần này 
		driver.manage().getCookies();
		// Lấy log của browser ra (Framework) 
		driver.manage().logs();
		// Time của việc tìm element : findElement, findElements
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Time page được load xong 
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		// Time để 1 đoạn async script được thực thi thành công 
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		// Fullscreen browser
		driver.manage().window().fullscreen();
		//
		driver.manage().window().maximize();
		// Lấy ra vị trí của browser so với độ phân giải màn hình 
		Point browserPosition = driver.manage().window().getPosition();
		// Set vị trí của browser tại điểm 0 x 250 
		driver.manage().window().setPosition(new Point(0, 250));
		// Lấy ra chiều rộng / chiều cao của browser 
		Dimension browserSize = driver.manage().window().getSize();
		// Set browser mở với kích thước nào 
		driver.manage().window().setSize(new Dimension(1366, 768));
		
		// Quay lại trang trước đó 
		driver.navigate().back();
		// Chuyển tiếp tới trang trước đó 
		driver.navigate().forward(); 
		// Tải lại trang 
		driver.navigate().refresh();
		// 
		driver.navigate().to("https://selenium-release.storage.googleapis.com/index.html?path=3.141/");
		// WebDriver API - Alert / Authentication Alert
		driver.switchTo().alert();
		// WebDriver API - Frame / Iframe
		driver.switchTo().frame(1);
		
		// WebDriver API - Windows / Tabs
		driver.switchTo().window("");
	}

	@Test
	public void TC_02_Element() {
		// Các hàm / method để thao tác với  là thông qua biến element 
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