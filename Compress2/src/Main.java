/**
 * Main Class for compressing project
 * @author Tan Pham
 * Created 02/07/2015
 *  Last modified 03/05/2015
 * ***************************************************************************************************************************************************************
 * 
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;


public class Main {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();


		String filename = "TheBlueAndTheGray.txt";		//name of the text file
		String text = "", bits = "";
		

		//try to read the file
		try {
			text = readFile(filename);
		} catch (IOException e) {
			System.out.println("Read file error!");
		}

		CodingTree tree = new CodingTree(text);			//create a tree from the text
		
		bits = tree.bits;
		
		StringBuilder com = new StringBuilder();		
		
		/*
		 * convert the bits string into text(character)
		 */
		for(int j = 0; j<bits.length();j+=8){
			int c = 0;
			if(j<bits.length()-8){
				for(int i = 0; i<8;i++){
					String temp = "" + bits.charAt(j+i);
					c += Integer.parseInt(temp);
				}
				char ch = (char)c ;				
				com.append(ch);

			}else{
				String end = bits.substring(j, bits.length());
				for(int i = 0; i<end.length();i++){
					String temp = "" + bits.charAt(i);
					c += Integer.parseInt(temp);
				}
				char ch = (char)c;
				com.append(ch);
			}
		}


		writeFile(com.toString(), "compressed.txt");				
		writeFile(tree.codes.toString(), "codes.txt");

		tree.codes.stat();
		
		int oriSize = text.length();
		int comSize = com.toString().length();
		double ratio = 100*((double)comSize/oriSize);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		System.out.println("The original file size (in bytes): " + oriSize);
		System.out.println("The compressed file size (in bytes): " + comSize);
		System.out.println("Compress ratio (%): " + new DecimalFormat("##.00").format(ratio));
		System.out.println("Total run time is: " + totalTime +" miliseconds");


	}


	/**
	 * Write a string text into an output file
	 * @param input
	 * @param filename
	 */
	private static void writeFile(String input, String filename){
		try {

			File file = new File(filename);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(input);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * File reader to read in a text file and create a string which content the text.
	 * @param file
	 * @return 
	 * @throws IOException
	 */
	private static String readFile( String file ) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader( new FileReader (file));
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String         ls = System.getProperty("line.separator");

		while( ( line = reader.readLine() ) != null ) {
			stringBuilder.append( line );
			stringBuilder.append( ls );
		}

		return stringBuilder.toString();
	}
}