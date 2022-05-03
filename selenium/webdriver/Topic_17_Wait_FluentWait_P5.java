package webdriver;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_FluentWait_P5 {
	WebDriver driver;
	WebDriverWait explicitWait;
	FluentWait<WebDriver> fluentWait;

	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

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
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public void TC_01_Introduce_FluentWait() {
		// Find Element với tổng thời gian là 15s
		// Tần số lặp lại nếu tìm không thấy là 1s/1 lần
		fluentWait = new FluentWait<WebDriver>(driver);
		// Tổng thời gian cho điều kiện
		fluentWait.withTimeout(Duration.ofSeconds(15))
				// Tần số mỗi 1s sẽ check 1 lần ( Thời gian lặp lại để tìm điều kiện nếu như
				// chưa thoả mãn )
				.pollingEvery(Duration.ofSeconds(1))
				// Nếu gặp exception là find không thấy element sẽ bỏ qua
				.ignoring(NoSuchElementException.class)
				// Điều kiện
				.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.xpath("//input[@name='btnI-fail']"));
					}
				});
	}

	@Test
	public void TC_02() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		fluentWait = new FluentWait<WebDriver>(driver);
		driver.findElement(By.cssSelector("div#start>button")).click();
		// Sau khi bấm thì loading icon xuất hiện và mất đi sau 5s
		// Icon loading biến mất = Helloworld text xuất hiện

		fluentWait.withTimeout(Duration.ofSeconds(6)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class).until(new Function<WebDriver, Boolean>() {

					public Boolean apply(WebDriver driver) {
						// TODO Auto-generated method stub
						return driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed();
					}
				});
	}

	@Test
	public void TC_03() {

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