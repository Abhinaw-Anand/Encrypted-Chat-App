package com.example.anandchat;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Encrypt {




    public static String AES(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
        SecretKey myAesKey = keygenerator.generateKey();


        Cipher desCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        desCipher.init(Cipher.ENCRYPT_MODE, myAesKey);
        byte[] textb = text.getBytes();


        byte[] textbEncrypted = desCipher.doFinal(textb);
        String encodedKey="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedKey = Base64.getEncoder().encodeToString(myAesKey.getEncoded());
        }
        String decoded = new String(textbEncrypted, "ISO-8859-1");


        Log.d("TAG1","Encrypted text is="+decoded+"++"+encodedKey);
        return decoded+"++"+encodedKey;




    }

    public static String DES(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
        SecretKey myAesKey = keygenerator.generateKey();


        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        desCipher.init(Cipher.ENCRYPT_MODE, myAesKey);
        byte[] textb = text.getBytes();


        byte[] textbEncrypted = desCipher.doFinal(textb);
        String encodedKey="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedKey = Base64.getEncoder().encodeToString(myAesKey.getEncoded());
        }
        String decoded = new String(textbEncrypted, "ISO-8859-1");


        Log.d("TAG1","Encrypted text is="+decoded+"++"+encodedKey);
        return decoded+"++"+encodedKey;




    }







}