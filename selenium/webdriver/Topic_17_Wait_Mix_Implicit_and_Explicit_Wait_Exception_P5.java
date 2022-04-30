package webdriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Mix_Implicit_and_Explicit_Wait_Exception_P5 {
	WebDriver driver;
	// Wait rõ ràng : với các điều kiện / status cụ thể
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.startsWith("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else if (osName.startsWith("Mac")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver_linux");
		}
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Element_Found() {
		driver.get("https://www.facebook.com/");
		By emailIDBy = By.id("email");

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		System.out.println("Start implicit = " + getCurrentTime());
		driver.findElement(emailIDBy).isDisplayed();
		System.out.println("End implicit = " + getCurrentTime());

		explicitWait = new WebDriverWait(driver, 15);
		System.out.println("Start explicitWait = " + getCurrentTime());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(emailIDBy));
		System.out.println("End explicitWait = " + getCurrentTime());
	}

	@Test
	public void TC_02_Element_Not_Found() {
		driver.get("https://www.facebook.com/");
		By emailIDBy = By.id("vietnam");
		
		// 1 - Implicit < Explicit 
		// 2 - Implicit = Explicit 
		// 3 - Implicit > Explicit
		// => Implicit sẽ không bị ảnh hưởng bởi bất kì 1 loại wait nào khác lên nó, nếu kh set thì mặc định bằng 0
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		System.out.println("Start implicit = " + getCurrentTime());
		driver.findElement(emailIDBy).isDisplayed();
		System.out.println("End implicit = " + getCurrentTime());

		explicitWait = new WebDriverWait(driver, 15);
		System.out.println("Start explicitWait = " + getCurrentTime());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(emailIDBy));
		System.out.println("End explicitWait = " + getCurrentTime());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public String getCurrentTime() {
		Date date = new Date();
		return date.toString();
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}