package com.jfeat.am.power.base.numberCheck;

import com.jfeat.am.power.base.numberCheck.impl.AESEncoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by craftperson on 2018/1/19.
 */

public class CheckNumber {
    public static boolean checkNumberIdentify(String source) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        int length = source.length();
        String num = source.substring(0,length-3);
        String en = source.substring(length-3);
        if(getThreeAp(AESEncoder.AESEncoder(num)).equals(en)){
            return true;
        }else {
            return false;
        }
    }

    private static String getThreeAp(String content){
        StringBuilder stringBuilder = new StringBuilder();
        int length = content.length();
        for(int i=0;i<length-1;i++){
            char c = content.charAt(i);
            if(check(c)){
                stringBuilder.append(c);
            }
            if(stringBuilder.length()==3){
                break;
            }
        }
        return stringBuilder.toString();
    }

    private static boolean check(char c) {
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z'))){
            return   true;
        }else{
            return   false;
        }
    }
}
