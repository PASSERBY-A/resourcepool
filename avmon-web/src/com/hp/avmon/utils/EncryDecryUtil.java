package com.hp.avmon.utils;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryDecryUtil {
    private static final EncryDecryUtil instance = new EncryDecryUtil();
    /** 加密运算key*/
    private static final String AES_KEY = "aa11";
    private EncryDecryUtil() {
    	
    }

    public static EncryDecryUtil getInstance() {
        return instance;   
    }

    /**
     * 运算key
     * 
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     */
//    private Key initKeyForAES(String key) throws NoSuchAlgorithmException {   
//        if (null == key || key.length() == 0) {
//            throw new NullPointerException("key not is null");
//        }
//        SecretKeySpec key2 = null;
//        try {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128, new SecureRandom(key.getBytes()));
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            key2 = new SecretKeySpec(enCodeFormat, "AES");
//        } catch (NoSuchAlgorithmException ex) {
//            throw new NoSuchAlgorithmException();
//        }
//        
//        return key2;   
//    }   
       
    /**
     * 加密
     * 
     * @param content
     * @param key
     * @return
     */
    public String encryptAES(String content){   
        byte[] aa=content.getBytes();
        //System.out.println("B="+line);
        String s="";

        for(int i=0;i<aa.length;i++){
            byte b=aa[i];
            byte c=(byte) (b-i);
            s+=str2HexStr((char) c);
        }
        return s;
//        try{   
//            SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(AES_KEY);   
//            Cipher cipher = Cipher.getInstance("AES");// create secret continer 
//            byte[] byteContent = content.getBytes("utf-8");   
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// init
//            byte[] result = cipher.doFinal(byteContent);   
//            
//            return asHex(result); // encrypt   
//        }   
//        catch (Exception e){   
//            e.printStackTrace();   
//        }   
//        
//        return null;   
    }   
   
    /**
     * 解密
     * 
     * @param content
     * @param key
     * @return
     */
    public String decryptAES(String content) {
        byte[] aa=content.getBytes();
        //System.out.println("B="+line);
        String s="";
        int n=content.length()/2;
        for(int i=0;i<n;i++){
            String s1=content.substring(i*2,i*2+2);
            byte b=Byte.decode("0x"+s1);
            byte bb[]={(byte) (b+i)};
            s+=new String(bb);
        }

        return s;
//    	if (content != null) {
//            try{   
//                SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(AES_KEY);   
//                Cipher cipher = Cipher.getInstance("AES");// create secret continer   
//                cipher.init(Cipher.DECRYPT_MODE, secretKey);// init   
//                byte[] result = cipher.doFinal(asBytes(content));   
//                
//                return new String(result); // encrypt  
//            }   
//            catch (Exception e){   
//                e.printStackTrace();   
//            } 
//    	}
//  
//        return null;   
    }
       
    public String asHex(byte buf[]){   
        StringBuffer strbuf = new StringBuffer(buf.length * 2);   
        int i;   
        for (i = 0; i < buf.length; i++){   
            if (((int) buf[i] & 0xff) < 0x10)   
                strbuf.append("0");   
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));   
        }   
        
        return strbuf.toString();   
    }   
       
    /**
     * 
     * @param hexStr
     * @return
     */
    public byte[] asBytes(String hexStr) {   
        if (hexStr.length() < 1)   
            return null;   
        byte[] result = new byte[hexStr.length() / 2];   
        for (int i = 0; i < hexStr.length() / 2; i++){   
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);   
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),16);   
            result[i] = (byte) (high * 16 + low);   
        }   
        
        return result;   
    }   

    /**
     * 将字符串中的特殊字符转换[\,$,#]
     * 
     * @param str
     * @return
     */
    public String escapeCharacter(String str) {
    	char buf[] = str.toCharArray();
		StringBuffer strbuf = new StringBuffer(buf.length * 2);   
		int i;   
		for (i = 0; i < buf.length; i++){   
			
			if ("\\".equals(MyFunc.nullToString(buf[i]))) {
				strbuf.append("\\\\");
			} else if ("$".equals(MyFunc.nullToString(buf[i]))) {
				strbuf.append("\\$");
			} else if ("#".equals(MyFunc.nullToString(buf[i]))) {
				strbuf.append("\\#");
			} else {
			    strbuf.append(buf[i]);
			}
		}
		
		return strbuf.toString();
    }
    
    public static void main(String[] args) {
    	EncryDecryUtil crypt = EncryDecryUtil.getInstance();
        String content = "$YjN9303v#";   
        System.out.println(content);
        
        String dcontent = crypt.encryptAES(content);   
        System.out.println(dcontent);   
        
        content = crypt.decryptAES(dcontent);
        System.out.println(content);   
        
        String str = crypt.escapeCharacter(content);
        System.out.println(str); 
    	//crypt.decryptAES(args[0]);
    }
    
    public static String str2HexStr(int b)  
    {    

        String s=Integer.toHexString(b);
        if(s.length()==1){
            return "0"+s;
        }
        else{
            return s;
        }
  
    }  
}
