package tech;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class Topic_01_Annotation {

	@Test(groups = "user")
	public void Register() {
		System.out.println("Register function");
	}

	@Test(groups = "user")
	public void Login() {
		System.out.println("Login function");
	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Login function");
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("Login function");
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("Login function");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("Login function");
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("Login function");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("Login function");
	}

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Login function");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("Login function");
	}
	
	

}
