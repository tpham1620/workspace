/**
 * RC4
 * @author Tan, Feng, Han
 *
 */


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


public class CBC {
	private byte[] input = null;

	public CBC(final byte[] theInput){
		input = theInput;
	}

	public byte[] encrypt(){
		byte[] cipherText = null;
		try {
			KeyGenerator keyGen;
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128); //128 bits
			final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  

			cipher.init(Cipher.ENCRYPT_MODE, keyGen.generateKey());
			cipherText = cipher.doFinal(input);  
		} catch (final Exception e) {
			 e.printStackTrace();
		}
		return cipherText;
	}
}

