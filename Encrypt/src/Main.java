/**
 * TCSS 481 - Computer System Security
 * Homework 3
 * 
 * Tan Pham, Han Wang, Feng Yang
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interface
 * @author Tan, Feng, Han
 *
 */
public class Main {
	
	public static void main(String args[]) throws Exception {
        
        byte[] text = null;
       // String test = "1234567890";
        
        /*
         * read in the file and convert to byte
         */
        try {
            final String filename = "The Bible - King James.txt";
            final File inFile = new File(filename);
            final InputStream insputStream = new FileInputStream(inFile);
            final long fileSize = inFile.length();
            System.out.println("File name: " + filename);
            System.out.format("File size: %,d bytes", fileSize);
            text = new byte[(int) fileSize];
            insputStream.read(text);
            insputStream.close();
        } catch (final IOException e) {
            System.err.println("Error reading/writing file.");
            e.printStackTrace();
        }




		
		System.out.println("\n\nEncryption running time:");
		
		//RC4 
		RC4 rc4 = new RC4("encription key");
        long startTime = System.currentTimeMillis();    
        rc4.encrypt(text); //encryption    
        long endTime = System.currentTimeMillis();
        System.out.println("- RC4: " + (endTime - startTime) + " ms.");

        //CBC
        CBC cbc = new CBC(text);
        startTime = System.currentTimeMillis();    
        cbc.encrypt(); //encryption    
        endTime = System.currentTimeMillis();
        System.out.println("- CBC: " + (endTime - startTime) + " ms.");
        
        //CTR
        CTR ctr = new CTR(text);
        startTime = System.currentTimeMillis();
        ctr.encrypt(); //encryption    
        endTime = System.currentTimeMillis();
        System.out.println("- CTR: " + (endTime - startTime) + " ms.");

        
    }
}
