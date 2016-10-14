/**
 * Midterm Exam
 * Implementation part
 * @author Tan Pham
 * **************************************************************************************************************************
 * This class create a person object that own a pair of keys and have the following abilities:
 * 1) encrypt a message using another person public key
 * 2) sign an encrypted (ciphertext) message using his own private key to get a signature for the message.
 * 3) verify an encrypted message along with a signature to verify authentication and integrity
 * 4) decrypt an encrypted message with his own private key.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;

import javax.crypto.*;

public class Person {
	private String name = "";
	private PrivateKey privateKey = null;
	private PublicKey publicKey = null;

	public Person(String n) throws NoSuchAlgorithmException{
		setName(n);
		generateKey();
	}

	/**
	 * generate key pair 
	 * @throws NoSuchAlgorithmException
	 */
	private void generateKey() throws NoSuchAlgorithmException{
		KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		setPrivateKey(keyPair.getPrivate());
		setPublicKey(keyPair.getPublic());
	}

	/**
	 * Sign the text file with secret key
	 * @param filename : name of the file to be sign
	 * @param sfn : name of the file to store the signature
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public byte[] sign(String filename, String sfn) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		byte[] text = readFile(filename);
		Signature instance = Signature.getInstance("SHA1withRSA");
		instance.initSign(privateKey);
		instance.update(text);
		byte[] signature = instance.sign();
		//write the signature to files
		try {
			Path signFile = Paths.get(sfn);
			Files.write(signFile, signature);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return signature;
	}

	/**
	 * Verify if the signature and the cipher are from the right person
	 * @param signature : name of the signature file
	 * @param filename : name of the file that was signed
	 * @param key : the person public key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public boolean verify(String signature, String filename , PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		byte[] signedText = readFile(signature);
		byte[] cipherText = readFile(filename);
		Signature instance = Signature.getInstance("SHA1withRSA");
		instance.initVerify(key);
		instance.update(cipherText);
		return instance.verify(signedText);
	}


	/**
	 * Encrypt the message to the receiver
	 * 
	 * @param filename : the file to encrypt
	 * @param cipherFileName : name for the file to store the cipher text
	 * @param key : the public key of the receiver
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] encrypt(String filename, String cipherFileName, PublicKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] text = readFile(filename);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		byte[] cipherText = null;
		for(int i = 0; i <= text.length/117; i++){
			byte[] temp = Arrays.copyOfRange(text, i*117, i*117 + 117);
			byte[] en_temp = cipher.doFinal(temp);
			try {
				outputStream.write(en_temp);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		cipherText = outputStream.toByteArray();
		//write the cipher text to files
		try {
			Path cipherFile = Paths.get(cipherFileName);
			Files.write(cipherFile, cipherText);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cipherText;
	}


	/**
	 * Decrypt the message received
	 * 
	 * @param cipherFile : file contains the encrypted message
	 * @param recoveredFile : name of the file will store the recovered message
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decrypt(String cipherFile, String recoveredFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] cipherText = readFile(cipherFile);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] text = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		for(int i = 0; i < cipherText.length/128; i++){
			byte[] temp = Arrays.copyOfRange(cipherText, i*128, i*128 + 128);
			byte[] de_temp = cipher.doFinal(temp);
			try {
				outputStream.write(de_temp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		text = outputStream.toByteArray();
		
		//write the recovered data to file
		try {
			Path decrypt = Paths.get(recoveredFile);
			Files.write(decrypt, text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
	}

	/**
	 * Read in the file input
	 * @param filename : name of the file to be read
	 * @return : byte array of the file
	 */
	private static byte[] readFile(String filename){
		byte[] plaintext = null;
		try {
			final File inFile = new File(filename);
			final InputStream insputStream = new FileInputStream(inFile);
			final long fileSize = inFile.length();
			plaintext = new byte[(int) fileSize];
			insputStream.read(plaintext);
			insputStream.close();
		} catch (final IOException e) {
			System.err.println("Error reading/writing file.");
			e.printStackTrace();
		}
		return plaintext;
	}
	/**
	 * @return the privateKey
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * @param privateKey the privateKey to set
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * @return the publicKey
	 */
	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * @param publicKey the publicKey to set
	 */
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
