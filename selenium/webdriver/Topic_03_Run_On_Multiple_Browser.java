package webdriver;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Topic_03_Run_On_Multiple_Browser {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");


	@Test
	public void TC_01_Firefox() {
		// Executable File : chromedriver / geckodriver / edgedriver / IEDriver / .....
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		// Class : FirefoxDriver() / ChromeDriver / EdgeDriver / SafariDriver / ....
		driver = new FirefoxDriver();
		driver.get("https://www.selenium.dev/");
		driver.quit();
	}
	
	@Test
	public void TC_02_Chrome() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		driver = new ChromeDriver();
		driver.get("https://www.selenium.dev/");
		driver.quit();
	}
	
	@Test
	public void TC_03_Edge() {
		System.setProperty("webdriver.edge.driver", projectPath + "/browserDrivers/msedgedriver");
		driver = new EdgeDriver();
		driver.get("https://www.selenium.dev/");
		driver.quit();
	}


}