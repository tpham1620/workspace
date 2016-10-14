/**
 * RC4
 * @author Tan, Feng, Han
 *
 */


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


public class CTR {
	private byte[] input = null;

	public CTR(final byte[] theInput){
		input = theInput;
	}

	public byte[] encrypt() {
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyGen.generateKey());
            cipherText = cipher.doFinal(input);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

}
