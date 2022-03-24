package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Javascript_Executor {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		driver = new ChromeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Javascript_Executor() {
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(2);
		String liveGuruDomain = (String) executeForBrowser("return document.domain;");
		Assert.assertEquals(liveGuruDomain, "live.techpanda.org");
		String liveGuruUrl = (String) executeForBrowser("return document.URL;");
		sleepInSecond(2);
		Assert.assertEquals(liveGuruUrl, "http://live.techpanda.org/");
		hightlightElement("//a[text()='Mobile']");
		clickToElementByJS("//a[text()='Mobile']");
		hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//span[text()='Add to Cart']");
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//span[text()='Add to Cart']");
		sleepInSecond(2);
		Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));
		
		hightlightElement("//a[text()='Customer Service']");
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(2);
		String titleCustomerService = (String) executeForBrowser("return document.title");
		Assert.assertEquals(titleCustomerService, "Customer Service");
		
		scrollToElementOnDown("//input[@title='Sign up for our newsletter']");
		hightlightElement("//input[@title='Sign up for our newsletter']");
		
		sendkeyToElementByJS("//input[@title='Sign up for our newsletter']", "lyhoang1290@gmail.com");
		
		hightlightElement("//span[text()='Subscribe']");
		clickToElementByJS("//span[text()='Subscribe']");
		sleepInSecond(2);
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription."));
		
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		sleepInSecond(2);
		String liveGuruLoginDomain = (String) executeForBrowser("return document.domain;");
		Assert.assertEquals(liveGuruLoginDomain, "demo.guru99.com");
		
	}

	@Test
	public void TC_02_Verify_HTML5_Validation_Message() {
		driver.get("https://login.ubuntu.com/");
		
		String emailTextboxXpath = "//input[@id='id_email']";
		WebElement loginButton = driver.findElement(By.cssSelector("button[data-qa-id='login_button']"));
		
		loginButton.click();
		sleepInSecond(2);
		Assert.assertEquals(getElementValidationMessage(emailTextboxXpath), "Please fill out this field.");
		
		driver.findElement(By.xpath(emailTextboxXpath)).sendKeys("123@123@&^%^^*");
		loginButton.click();
		sleepInSecond(2);
		
		if (driver.toString().contains("ChromeDriver")) {
			Assert.assertEquals(getElementValidationMessage(emailTextboxXpath), "A part following '@' should not contain the symbol '@'");
		} else if (driver.toString().contains("FirefoxDriver")) {
			Assert.assertEquals(getElementValidationMessage(emailTextboxXpath), "Please enter an email address.");
		}
	}

	@Test
	public void TC_03_Verify_HTML5_Validation_Message() {
		navigateToUrlByJS("https://automationfc.github.io/html5/index.html");
		sleepInSecond(2);
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(2);
		 
		if (driver.toString().contains("ChromeDriver")) {
			Assert.assertEquals(getElementValidationMessage("//input[@id='fname']"), "Please fill out this field.");
		} else if (driver.toString().contains("FirefoxDriver")){
			Assert.assertEquals(getElementValidationMessage("//input[@id='fname"), "Please fill out this field.");
		}
		
		sendkeyToElementByJS("//input[@id='fname']", "Quang Tin");
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(2);
		
		
		if (driver.toString().contains("ChromeDriver")) {
			Assert.assertEquals(getElementValidationMessage("//input[@id='pass']"), "Please fill out this field.");
		} else if (driver.toString().contains("FirefoxDriver")){
			Assert.assertEquals(getElementValidationMessage("//input[@id='pass']"), "Please fill out this field.");
		}
		
		sendkeyToElementByJS("//input[@id='pass']", "123123");
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(2);
		
		if (driver.toString().contains("ChromeDriver")) {
			Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please fill out this field.");
		} else if (driver.toString().contains("FirefoxDriver")){
			Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please fill out this field.");
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (Boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
		if (status) {
			return true;
		}
		return false;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}