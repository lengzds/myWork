package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class XmlReader {
	public static void main(String[] args) {
		File f = new File("./layout");
		BufferedReader reader = null;
		File m = f.listFiles()[18];
		String name,option;
		// for (File m : f.listFiles()) {
		try {
			reader = new BufferedReader(new FileReader(m));
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {

				for (String s : tempString.split("\"\\s")) {
					System.out.println(s);

					if (s.contains("<") && s.contains("and")) {
						name = s.substring(0, s.indexOf(" and", 3))
								.replace(" ", "").substring(1);
					}else if(s.contains("android:")&&s.contains("dip")){
						option=s.replace("android:", "").replace('"', ' ');
						System.out.println("options :   "+option);
						
					}

				}
				System.out.println("----------------");
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }
	}
}
