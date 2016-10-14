/**
 * RC4
 * @author Tan, Feng, Han
 *
 */

public class RC4 {
	private int[] S = new int[256];
	private final int keylen;

	/*
	 * construct key
	 */
	public RC4(String key) {
        byte[] keytoByte = key.getBytes(); //convert keyword to byte
		if (keytoByte.length < 1 || keytoByte.length > 256) {
			throw new IllegalArgumentException("key must be between 1 and 256 bytes");
		} else {
			keylen = keytoByte.length;
			for (int i = 0; i < 256; i++) {
				S[i] = i;
			}

			int j = 0;

			for (int i = 0; i < 256; i++) {
				j = (j + S[i] + keytoByte[i % keylen]) % 256;
				int temp = S[i];
				S[i] = S[j];
				S[j] = temp;
			}
		}
	}

	/*
	 * encrypt 
	 */
	public byte[] encrypt(final byte[] input) {	
		
		final byte[] ciphertext = new byte[input.length];

		final int[] S = new int[this.S.length];
		System.arraycopy(this.S, 0, S, 0, S.length);

		int i = 0, j = 0, k, t;
		for (int counter = 0; counter < input.length; counter++) {
			i = (i + 1) & 0xFF;
			j = (j + S[i]) & 0xFF;
			S[i] ^= S[j];
			S[j] ^= S[i];
			S[i] ^= S[j];
			t = (S[i] + S[j]) & 0xFF;
			k = S[t];
			ciphertext[counter] = (byte) (input[counter] ^ k);
		}
		return ciphertext;
		
	}
	
	
	public byte[] decrypt(final byte[] ciphertext) {
		return encrypt(ciphertext);
	}
}