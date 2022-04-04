package webdriver;

import java.io.File;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Static_and_Explicit_Wait_P4 {
	WebDriver driver;
	// Wait rõ ràng : với các điều kiện / status cụ thể
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	// Cấu hình Upload File
	String separatorChar = File.separator; // dấu "/"
	String imagesLocation = projectPath + separatorChar + "images" + separatorChar;
	// Đặt tên file
	String oneImage = "01.jpg";
	String twoImage = "02.jpg";
	String threeImage = "03.jpg";
	// Đường dẫn đến file
	String oneImageLocation = imagesLocation + oneImage;
	String twoImageLocation = imagesLocation + twoImage;
	String threeImageLocation = imagesLocation + threeImage;

	By startButton = By.cssSelector("div#start>button");
	By loadingIcon = By.cssSelector("div#loading");
	By helloworldText = By.cssSelector("div#finish>h4");

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
		// Wait ngầm định : không có 1 element / điều kiện / status nào rõ ràng - ngầm
		// định cho việc tìm element
		// driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Invisible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		explicitWait = new WebDriverWait(driver, 30);
		driver.findElement(startButton).click();
		// Loading icon biến mất sau 30s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		Assert.assertEquals(driver.findElement(helloworldText).getText(), "Hello World!");
	}

	@Test
	public void TC_02_Visible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		explicitWait = new WebDriverWait(driver, 30);
		driver.findElement(startButton).click();
		// Helloworld text hiển thị sau 30s
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(helloworldText));
		Assert.assertEquals(driver.findElement(helloworldText).getText(), "Hello World!");
	}

	@Test
	public void TC_03_Number() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		explicitWait = new WebDriverWait(driver, 30);
		driver.findElement(startButton).click();
		// Helloworld text element = 1, nghĩa là element có xuất hiện trên web
		// Cách này dùng được, nhưng không hay và dễ hiểu bằng 2 cách ở trên
		explicitWait.until(ExpectedConditions.numberOfElementsToBe(helloworldText, 1));
		Assert.assertEquals(driver.findElement(helloworldText).getText(), "Hello World!");
	}

	@Test
	public void TC_04_telerikPage() {
		explicitWait = new WebDriverWait(driver, 30);
		driver.get(
				"https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		// wait cho Date Picker xuất hiện
		explicitWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#ctl00_ContentPlaceholder1_Panel1")));
		// Element này nó được tìm tại thời điểm mà chưa click lên ngày 11
		WebElement selectedDateText = driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"));
		Assert.assertEquals(selectedDateText.getText(), "No Selected Dates to display.");
		// Wait cho ngày 11 có thể click và sau đó click lên nó
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='11']"))).click();
		// Wait loading icon biến mất
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.xpath("//div[contains(@id,'RadCalendar1')]/div[@class='raDiv']")));
		// Sau khi click vào ngày 11 thì element có text nó được cập nhật lại
		// Nếu như dùng lại element đã được find ở trên rồi thì getText sai
		selectedDateText = driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1"));
		// Verify ngày được update
		Assert.assertEquals(selectedDateText.getText(), "Monday, April 11, 2022");
		// Wait cho ngày được selected thành công (visible)
		WebElement todayselected = explicitWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='11']")));
		// Verify ngày được update
		Assert.assertTrue(todayselected.isDisplayed());
	}

	@Test
	public void TC_05_uploadFile() {
		explicitWait = new WebDriverWait(driver, 60);
		driver.get("https://gofile.io/?t=uploadFiles");
		By uploadFileBy = By.cssSelector("input[type='file']");
		// Load File + Upload
		sleepInSecond(5);
		driver.findElement(uploadFileBy)
				.sendKeys(oneImageLocation + "\n" + twoImageLocation + "\n" + threeImageLocation);
		// Wait cho file được upload thành công trong vòng 60s
		explicitWait.until(
				ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress"))));
		// Wait cho text hiển thị
		WebElement uploadedText = explicitWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));
		Assert.assertTrue(uploadedText.isDisplayed());
		driver.findElement(By.cssSelector("a#rowUploadSuccess-downloadPage")).click();
		// Lấy ra ID của 1 tab / window mà driver đang đứng (active)
		String parentTabID = driver.getWindowHandle();
		switchToTabByID(parentTabID);

		WebElement downloadText01 = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//span[text()='01.jpg']/parent::a/parent::div/following-sibling::div//span[text()='Download']")));
		Assert.assertTrue(downloadText01.isDisplayed());
		WebElement playText01 = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[text()='01.jpg']/parent::a/parent::div/following-sibling::div//span[text()='Play']")));
		Assert.assertTrue(playText01.isDisplayed());
		WebElement downloadText02 = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//span[text()='02.jpg']/parent::a/parent::div/following-sibling::div//span[text()='Download']")));
		Assert.assertTrue(downloadText02.isDisplayed());
		WebElement playText02 = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[text()='02.jpg']/parent::a/parent::div/following-sibling::div//span[text()='Play']")));
		Assert.assertTrue(playText02.isDisplayed());
		WebElement downloadText03 = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//span[text()='03.jpg']/parent::a/parent::div/following-sibling::div//span[text()='Download']")));
		Assert.assertTrue(downloadText03.isDisplayed());
		WebElement playText03 = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[text()='03.jpg']/parent::a/parent::div/following-sibling::div//span[text()='Play']")));
		Assert.assertTrue(playText03.isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	// HÀM NÀY CHỈ ĐÚNG CHO 2 TAB / WINDOWS
	public void switchToTabByID(String expectedID) {
		// Lấy hết tất cả ID của tab / window đang có
		Set<String> allTabIDs = driver.getWindowHandles();
		// Dùng vòng lặp để duyệt qua từng ID một
		for (String id : allTabIDs) { // id : là một biến tạm dùng để duyệt (so sánh)
			// ID nào mà khác với expectedID truyền vào thì switch qua
			if (!id.equals(expectedID)) {
				driver.switchTo().window(id);
				break; // dừng vòng lặp khi đã tìm được giá trị thoả điều kiện
			}
		}
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getCurrentTime() {
		Date date = new Date();
		return date.toString();
	}
}