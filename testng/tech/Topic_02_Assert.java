package tech;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_02_Assert {
	@Test
	public void TC_01() {
		// Thư viện Assert : Kiểm tra tính đúng đắn của dữ liệu
		// Mong đợi nó đúng / sai / đầu vào đầu ra như nhau
		// Bằng null / khác null....
		String addressCity = "Ho Chi Minh";
		// Kiểm tra 1 điều kiện mong đợi luôn luôn đúng
		Assert.assertTrue(addressCity.equals("Ho Chi Minh"));
		Assert.assertTrue(addressCity.contains("Chi Minh"));

		// Kiểm tra 1 điều kiện mong đợi luôn luôn sai
		Assert.assertFalse(addressCity.equals("Ha Noi"));
		Assert.assertFalse(addressCity.contains("Da Nang"));
		
		// Kiểm tra dữ liệu đầu vào và đầu ra như nhau 
		Assert.assertEquals(addressCity, "Ho Chi Minh");
		
		// Kiểm tra dữ liệu mong đợi là null / not null (đã khởi tạo) 
		Object fullname = null;
		Assert.assertNull(fullname);
		
		fullname = "Truong Quang Tin";
		Assert.assertNotNull(fullname);
	}

}
