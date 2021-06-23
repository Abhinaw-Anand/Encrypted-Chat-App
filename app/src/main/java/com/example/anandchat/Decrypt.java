package com.example.anandchat;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {


@RequiresApi(api = Build.VERSION_CODES.O)
public static String runner(String text, String em) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

    if(em.equals("AES"))
        return AES(text);
    else
        return DES(text);



}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String AES(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        int v=text.indexOf("++");
        String textencrypted=text.substring(0,v);
        String key=text.substring(v+2);
        Log.d("TAG1","Decryption started");

        Log.d("TAG1","part1 "+textencrypted);
        Log.d("TAG1","part2 "+key);


        byte[] decodedKey = Base64.getDecoder().decode(key);
        SecretKey myaeskey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        Log.d("TAG1","decoded AES key "+myaeskey);
        Cipher desCipher=Cipher.getInstance("AES/ECB/PKCS5Padding");;
        // Initialize the same cipher for decryption
        desCipher.init(Cipher.DECRYPT_MODE, myaeskey);
        byte[] encoded = textencrypted.getBytes("ISO-8859-1");
        byte[] textDecrypted = desCipher.doFinal(encoded);
        Log.d("TAG1","Decrypt ho gaya");

        Log.d("TAG1","decrypted string is ="+ new String(textDecrypted));
        return new String(textDecrypted);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String DES(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        int v=text.indexOf("++");
        String textencrypted=text.substring(0,v);
        String key=text.substring(v+2);
        Log.d("TAG1","Decryption started");

        Log.d("TAG1","part1 "+textencrypted);
        Log.d("TAG1","part2 "+key);


        byte[] decodedKey = Base64.getDecoder().decode(key);
        SecretKey myaeskey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
        Log.d("TAG1","decoded AES key "+myaeskey);
        Cipher desCipher=Cipher.getInstance("DES/ECB/PKCS5Padding");;
        // Initialize the same cipher for decryption
        desCipher.init(Cipher.DECRYPT_MODE, myaeskey);
        byte[] encoded = textencrypted.getBytes("ISO-8859-1");
        byte[] textDecrypted = desCipher.doFinal(encoded);
        Log.d("TAG1","Decrypt ho gaya");

        Log.d("TAG1","decrypted string is ="+ new String(textDecrypted));
        return new String(textDecrypted);

    }


}