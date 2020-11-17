/*
 * Nombre de Clase: EncrypPass
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package utilidades;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author dguevara
 */
public class EncrypPass {
    
    public static String algo="AES";
    
    String ff="MySecretPass";
    public byte[] keyValue=new byte[]{ 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
            //{'T','h','e','B','e','s','t','S','e','c','r','e','t'};
    
    public Key generateKey() throws Exception
    {
        Key key= new SecretKeySpec(keyValue,algo);
        return key;
    }
    
    public String encrypt(String msg) throws Exception
    {
        Key key=generateKey();
        Cipher c=Cipher.getInstance(algo);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(msg.getBytes());
        String encryptedValue=new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
    
    
    public String decrypt(String msg) throws Exception
    {
        Key key=generateKey();
        Cipher c=Cipher.getInstance(algo);
        c.init(Cipher.DECRYPT_MODE,key);
        
        byte[] decordedValue=new BASE64Decoder().decodeBuffer(msg);
        byte[] decValue=c.doFinal(decordedValue);
        String decryptedValue=new String(decValue);
        return decryptedValue;
    }
}
