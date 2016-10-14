/**
 * Main Class
 * @author Tan Pham
 * Created 01/10/2015
 * Last edit 02/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */


import java.io.BufferedReader;
import java.io.FileReader;


public class Main {

	public static void main(String[] args) {

		CoffeeShop cfs = new CoffeeShop();

		String fileName="test.txt";
		try{

			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			String line;

			while ((line = bufferReader.readLine()) != null)   {
				System.out.println("Processing Input: " + line);
				if(line.contains("S")){
					switch(line.substring(0, 3)){
					case "CSS": {
						cfs.offer(new CSSStudent(line));
						break;
					}
					case "CES": {
						cfs.offer(new CESStudent(line));
						break;
					}case "ITS": {
						cfs.offer(new ITSStudent(line));
						break;
					}
					}

				}else {
					cfs.poll();
				}
				System.out.println(cfs.toString());	
			}

			bufferReader.close();
		}catch(Exception e){
			System.out.println("Error while reading file line by line:\n" + e.getMessage());                      
		}

	}

}
