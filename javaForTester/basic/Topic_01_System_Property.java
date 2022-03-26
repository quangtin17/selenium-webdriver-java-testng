package basic;

import java.io.File;

public class Topic_01_System_Property {
	public static void main(String[] args) {
		String projectPath = System.getProperty("user.dir");
		String osName = System.getProperty("os.name");
		System.out.println(projectPath);
		System.out.println(osName);
		
		
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
		System.out.print(kingsman2ImageLocation);
	}

}
