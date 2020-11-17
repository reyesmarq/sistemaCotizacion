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

    

    /*public String hashPassword(String password)
    {
        String passwordToHash = password;
        String generatedPassword=null;
        try
        {
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes=md.digest();
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<bytes.length;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff)+0x100,32).substring(1));    
            }
            generatedPassword=sb.toString();
            //System.out.println(generatedPassword);
            return generatedPassword;
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public String cipher(String pass)
    {
        String word="";
        for(int i=0;i<pass.length();i++)
        {
            char code=Character.toLowerCase(pass.charAt(i));
            
            switch(code)
            {
                case 'e':
                    word+="!";
                    break;
                case 't':
                    word+="@";
                    break;
                case 'a':
                    word+="#";
                    break;
                case 'o':
                    word+="$";
                    break;
                case 'i':
                    word+="%";
                    break;
                case 'n':
                    word+="^";
                    break;
                case 's':
                    word+="&";
                    break;
                case 'r':
                    word+="*";
                    break;
                default:
                    word+=pass.charAt(i); 
                    break;
            }
        }
        
        return word;
    }
    
    
    public String deCipher(String pass)
    {
        String word="";
        for(int i=0;i<pass.length();i++)
        {
            char code=Character.toLowerCase(pass.charAt(i));
            
            switch(code)
            {
                case '!':
                    word+="e";
                    break;
                case '@':
                    word+="t";
                    break;
                case '#':
                    word+="a";
                    break;
                case '$':
                    word+="o";
                    break;
                case '%':
                    word+="i";
                    break;
                case '^':
                    word+="n";
                    break;
                case '&':
                    word+="s";
                    break;
                case '*':
                    word+="r";
                    break;
                default:
                    word+=pass.charAt(i); 
                    break;
            }
        }
        
        return word;
    }*/
    
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
