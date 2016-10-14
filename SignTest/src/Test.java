import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;


public class Test {
  public static void main(String[] args) throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(1024);
    KeyPair keyPair = kpg.genKeyPair();

    byte[] data = "test".getBytes("UTF8");

    Signature sig = Signature.getInstance("SHA1withRSA");
    sig.initSign(keyPair.getPrivate());
    sig.update(data);
    byte[] signatureBytes = sig.sign();
    System.out.println("Singature:" + signatureBytes);

    sig.initVerify(keyPair.getPublic());
    sig.update(data);

    System.out.println(sig.verify(signatureBytes));
  }
}