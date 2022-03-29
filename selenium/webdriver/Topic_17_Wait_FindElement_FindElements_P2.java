package webdriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_FindElement_FindElements_P2 {
	WebDriver driver;
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
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
	}

	public void TC_01_Find_Element() {
		// Có duy nhất 1 element :
		// - Nếu như element xuất hiện ngay --> Thì trả về element đó không cần chờ hết
		// timeout
		// - Nếu như element chưa xuất hiện ngay --> Thì sau 0.5s sẽ tìm lại cho đến khi
		// hết time thì thôi
		// Trả về duy nhất 1 element
		System.out.println("Start Time: " + getCurrentTime());
		driver.findElement(By.xpath("//input[@id='email']"));
		System.out.println("End Time: " + getCurrentTime());
		System.out.println("-----------------------------------");
		// Không có element nào hết :
		// - Tìm đi tìm lại cho đến khi hết timeout -> Sau khi hết timeout thì sẽ đánh
		// fail cả testcase này, không chạy các step còn lại
		// - Throw / Log ra 1 exception (ngoại lệ) : NoSuchElement - không tìm thấy
		// element

		// Có nhiều hơn 1 element : Lấy ra element đầu tiên để thao tác
	}

	@Test
	public void TC_02_Find_Elements() {
		// (1) Có duy nhất 1 element :
		int elementNumber = 0;
		// (2) Có nhiều hơn 1 element :
		// (1) và (2) được hiểu như nhau :
		// - Nếu như element xuất hiện ngay --> Thì trả về element đó không cần chờ hết
		// timeout
		// - Nếu như element chưa xuất hiện ngay --> Thì sau 0.5s sẽ tìm lại cho đến khi
		// hết time thì thôi
		elementNumber = driver.findElements(By.xpath("//input[@id='email']")).size();
		System.out.println("1 element : " + elementNumber);
		elementNumber = driver.findElements(By.xpath("//div[@id='pageFooterChildren']//a[text()]")).size();
		System.out.println("n element : " + elementNumber);
		// Không có element nào hết :
		// - Tìm đi tìm lại cho đến khi hết timeout -> Sau khi hết timeout thì sẽ KHÔNG 
		// đánh fail test case mà vẫn chạy các step tiếp theo
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

	public String getCurrentTime() {
		Date date = new Date();
		return date.toString();
	}
}