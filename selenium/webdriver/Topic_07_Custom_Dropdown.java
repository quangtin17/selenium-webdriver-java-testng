package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_07_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		driver = new ChromeDriver();
		
		// Wait cho các trạng thái của element : visible / presence / invisible / staleness 
		explicitWait = new WebDriverWait(driver, 15);
		
		jsExecutor = (JavascriptExecutor) driver;
		// Wait cho việc tìm element (findElement) 
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_JQuery() {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdownList("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "5");
		selectItemInCustomDropdownList("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "15");
		selectItemInCustomDropdownList("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "19");
		selectItemInCustomDropdownList("span#number-button>span.ui-selectmenu-icon", "ul#number-menu div", "3");
	}

	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInCustomDropdownList("i.dropdown.icon", "div.item>span.text", "Stevie Feliciano");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Stevie Feliciano");
		selectItemInCustomDropdownList("i.dropdown.icon", "div.item>span.text", "Justen Kitsune");	
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Justen Kitsune");
	}

	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdownList("span.caret", "ul.dropdown-menu a", "Third Option");
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Third Option");
		selectItemInCustomDropdownList("span.caret", "ul.dropdown-menu a", "First Option");
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "First Option");
	}

	@Test
	public void TC_04_Angular_Select() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		selectItemInCustomDropdownList("ng-select[bindvalue='provinceCode'] span.ng-arrow-wrapper", "span.ng-option-label.ng-star-inserted", "Tỉnh Quảng Ngãi");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='provinceCode'] span.ng-value-label.ng-star-inserted")).getText(), "Tỉnh Quảng Ngãi");
		selectItemInCustomDropdownList("ng-select[bindvalue='districtCode'] span.ng-arrow-wrapper", "span.ng-option-label.ng-star-inserted", "Huyện Tư Nghĩa");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='districtCode'] span.ng-value-label.ng-star-inserted")).getText(), "Huyện Tư Nghĩa");
		selectItemInCustomDropdownList("ng-select[bindvalue='wardCode'] span.ng-arrow-wrapper", "span.ng-option-label.ng-star-inserted", "Xã Nghĩa Thắng");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='wardCode'] span.ng-value-label.ng-star-inserted")).getText(), "Xã Nghĩa Thắng");
	}
	
	@Test
	public void TC_04_Angular_Enter() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		enterToCustomDropdownList("ng-select[bindvalue='provinceCode'] input[role='combobox']", "span.ng-option-label.ng-star-inserted", "Tỉnh Quảng Ngãi");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='provinceCode'] span.ng-value-label.ng-star-inserted")).getText(), "Tỉnh Quảng Ngãi");
		enterToCustomDropdownList("ng-select[bindvalue='districtCode'] input[role='combobox']", "span.ng-option-label.ng-star-inserted", "Huyện Tư Nghĩa");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='districtCode'] span.ng-value-label.ng-star-inserted")).getText(), "Huyện Tư Nghĩa");
		enterToCustomDropdownList("ng-select[bindvalue='wardCode'] input[role='combobox']", "span.ng-option-label.ng-star-inserted", "Xã Nghĩa Thắng");
		Assert.assertEquals(driver.findElement(By.cssSelector("ng-select[bindvalue='wardCode'] span.ng-value-label.ng-star-inserted")).getText(), "Xã Nghĩa Thắng");
	}
	
	@Test
	public void TC_05_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		enterToCustomDropdownList("input.search", "div[role='option']", "Belize");
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void selectItemInCustomDropdownList(String parentLocator, String childLocator, String expectedTextItem) {
//		Step 1 : Click vào 1 element cho nó xổ hết ra các item 
		driver.findElement(By.cssSelector(parentLocator)).click();
		sleepInSecond(2);
//		Step 2 : Chờ cho các item load hết ra thành công 
//		Lưu ý 1 : Locator chứa hết tất cả các item
//		Lưu ý 2 : Locator phải đến node cuối cùng chứa text 
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
//		Step 3 : Tìm item cần chọn 
		
		// Lấy hết tất cả các element (item) ra sau đó duyệt qua từng item 
		List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));
		// Duyệt qua từng item getText của item ra 
		for (WebElement item : allItems) {
			String actualText = item.getText();
			System.out.println("Actual Text = " + actualText);
		// Nếu text = text mình mong muốn (item cần click vào) 
			if (actualText.equals(expectedTextItem)) {
//				- B1 : Nếu item cần chọn nằm trong vùng nhìn thấy thì không cần scroll xuống tìm tiếp 
//				- B2 : Nếu item cần chọn nằm ở dưới thì scroll xuống đến item đó 
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);
				// Step 4 : Click vào item đó 
				item.click();
				sleepInSecond(2);
				// Thoát khỏi vòng lặp không kiểm tra element tiếp theo 
				break;
			}
		}	
	}
	
	public void enterToCustomDropdownList(String parentLocator, String childLocator, String expectedTextItem) {
//		Step 1 : Phải lấy đến thẻ input / textbox để sendky vào  
		driver.findElement(By.cssSelector(parentLocator)).sendKeys(expectedTextItem);
		sleepInSecond(2);
//		Step 2 : Chờ cho các item load hết ra thành công 
//		Lưu ý 1 : Locator chứa hết tất cả các item
//		Lưu ý 2 : Locator phải đến node cuối cùng chứa text 
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
//		Step 3 : Tìm item cần chọn 
		
		// Lấy hết tất cả các element (item) ra sau đó duyệt qua từng item 
		List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));
		// Duyệt qua từng item getText của item ra 
		for (WebElement item : allItems) {
			String actualText = item.getText();
			System.out.println("Actual Text = " + actualText);
		// Nếu text = text mình mong muốn (item cần click vào) 
			if (actualText.equals(expectedTextItem)) {
//				- B1 : Nếu item cần chọn nằm trong vùng nhìn thấy thì không cần scroll xuống tìm tiếp 
//				- B2 : Nếu item cần chọn nằm ở dưới thì scroll xuống đến item đó 
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);
				// Step 4 : Click vào item đó 
				item.click();
				sleepInSecond(2);
				// Thoát khỏi vòng lặp không kiểm tra element tiếp theo 
				break;
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
}