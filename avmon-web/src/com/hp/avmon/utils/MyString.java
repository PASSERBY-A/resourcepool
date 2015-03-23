package com.hp.avmon.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyString {

    public static String generateUniqueId(){
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
        return sdf.format(date);
    }
    
    public static void main(String[] args) {
        System.out.println(generateUniqueId());
    }

    public static String toVolumeString(float size) {
        float f=size;
        String val=f+" Bytes";
        if(f>1024){
            f=Math.round(f/1024*100)/100;
            val=f+" KB";
        }
        if(f>1024){
            f=Math.round(f/1024*100)/100;
            val=f+" MB";
        }
        if(f>1024){
            f=Math.round(f/1024*100)/100;
            val=f+" GB";
        }
        return val;
    }
}
