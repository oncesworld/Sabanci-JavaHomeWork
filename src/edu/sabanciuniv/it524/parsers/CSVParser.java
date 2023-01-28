package edu.sabanciuniv.it524.parsers;

import java.io.BufferedReader;
import java.io.FileReader;


public class CSVParser extends TextFileParser{
	
	private String allFile ="";

	@Override
	public String readFile (String filePath) {
		
		try {
			FileReader reader = new FileReader(filePath);
			BufferedReader bfr = new BufferedReader(reader);
			while(true)
			{
				String line = bfr.readLine();
				if(line==null)
					break;
				allFile +=line + "\n";
			}
			allFile = allFile.replace(",", " ");
			allFile = allFile.replace(";", " ");
			reader.close();
		} catch (Exception e) {
			System.out.println("Hata oluştu");
			e.printStackTrace();
		}
		
		
		return allFile;
	}
}
