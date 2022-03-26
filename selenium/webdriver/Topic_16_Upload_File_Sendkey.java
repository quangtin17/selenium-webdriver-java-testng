package webdriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Upload_File_Sendkey {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String separatorChar = File.separator; // dấu "/"
	String imagesLocation = projectPath + separatorChar + "images" + separatorChar;

	String ironImage = "iron.jpeg";
	String kingsmanImage = "Kingsman_6.jpeg";
	String luffyImage = "luffy.jpg";
	String kingsman2Image = "phan-3-kingsman-the-great-game-se-ra-mat-vao-thang-2-nam-sau-0986a2f3.jpeg";

	String ironImageLocation = imagesLocation + ironImage;
	String kingsmanImageLocation = imagesLocation + kingsmanImage;
	String luffyImageLocation = imagesLocation + luffyImage;
	String kingsman2ImageLocation = imagesLocation + kingsman2Image;

	@BeforeClass
	public void beforeClass() {
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
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Upload_One_File_By_Sendkeys() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		By uploadFileBy = By.cssSelector("input[type='file']");
		// Load file
		driver.findElement(uploadFileBy).sendKeys(ironImageLocation);
		sleepInSecond(2);
		driver.findElement(uploadFileBy).sendKeys(kingsmanImageLocation);
		sleepInSecond(2);
		driver.findElement(uploadFileBy).sendKeys(luffyImageLocation);
		sleepInSecond(2);
		driver.findElement(uploadFileBy).sendKeys(kingsman2ImageLocation);
		sleepInSecond(2);

		// Uploading
		// driver.findElement(By.xpath("//button[@type='submit']")).click();
		List<WebElement> startButtons = driver.findElements(By.xpath("//span[text()='Start']"));
		for (WebElement start : startButtons) {
			start.click();
			sleepInSecond(1);
		}

		// Verify uploaded success
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + ironImage + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + kingsmanImage + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + luffyImage + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + kingsman2Image + "']")).isDisplayed());
	}

	@Test
	public void TC_02_Upload_Multiple_File_By_Sendkeys() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		By uploadFileBy = By.cssSelector("input[type='file']");
		// Load file
		driver.findElement(uploadFileBy).sendKeys(ironImageLocation + "\n" + kingsman2ImageLocation + "\n" + luffyImageLocation + "\n" + kingsmanImageLocation);
		sleepInSecond(2);

		// Uploading
		// driver.findElement(By.xpath("//button[@type='submit']")).click();
		List<WebElement> startButtons = driver.findElements(By.xpath("//span[text()='Start']"));
		for (WebElement start : startButtons) {
			start.click();
			sleepInSecond(1);
		}

		// Verify uploaded success
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + ironImage + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + kingsmanImage + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + luffyImage + "']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + kingsman2Image + "']")).isDisplayed());
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