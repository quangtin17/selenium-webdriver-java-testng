package tech;

import org.testng.annotations.Test;

public class Topic_03_Priority_Enable_Description {

	@Test
	public void Order_01_View_Product() {

	}
	
	@Test
	public void Order_02_Add_To_Cart() {

	}
	
	@Test(description = "User order any product and add the payment method (Visa / Cheque/...)")
	public void Order_03_Add_Payment_Method() {

	}
	
	@Test(enabled = true)
	public void Order_04_Checkout() {

	}
}
