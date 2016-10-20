/**
 * TCSS 481 - Computer System Security
 * Midterm Exam
 * Implementation part
 * @author Tan Pham
 * 
 * *****************************************************************************
 * 
 */


import java.security.*;
import javax.crypto.*;

/**
 * 
 * For this scheme, we created 2 different person 
 * Person1 encrypt a binary file with person2 public key and then sign it
 * Person2 will verify the encrypted message and decrypt the cipher text.
 * 
 * To ensure the integrity, we changed the ciphertext and try to decrypt and failed at the check point.
 *
 */
public class EncriptionScheme {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, 
	InvalidKeyException, SignatureException, NoSuchPaddingException, 
	IllegalBlockSizeException, BadPaddingException{

		//the two person that want to communicate secretly.
		Person p1 = new Person("p1");
		Person p2 = new Person("p2");

		//person1 encrypt the message file using person2 public key
		p1.encrypt("file_to_encrypt.bin", "cipher_text.bin", p2.getPublicKey());
		//he then sign the encrypted message with his private key.
		p1.sign("cipher_text.bin", "signature.bin");



		//the person2 receives both the cipher text and the signature (the signed cipher text)
		//he then verify if they were sent by person1 by using person1's public key
		//if the signature is valid, person 2 decrypt the cipher text using his private key.
		boolean check = p2.verify("signature.bin", "cipher_text.bin", p1.getPublicKey());
		if (check) {
			p2.decrypt("cipher_text.bin", "origianl_message.bin");

		}else{
			System.out.println("packets have been interrupted and/or modified by thirth party");
		}
	}
}