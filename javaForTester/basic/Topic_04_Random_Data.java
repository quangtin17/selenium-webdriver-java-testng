package basic;

import java.util.Random;

public class Topic_04_Random_Data {
	public static void main(String[] args) {
		
		Random rand = new Random();
		System.out.println(rand.nextInt(99));
		System.out.println("quangtin" + rand.nextInt(999) + "@gmail.com");
	}
}
