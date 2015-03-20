package com.hp.xo.uip.cmdb.util;

import java.util.regex.Pattern;

public class NumberUtil {
	//判断 正整数
	public static boolean isNumberic1(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }

		//用正则表达式
		public static boolean isNumberic2(String str){ 
		    Pattern pattern = Pattern.compile("[0-9]*"); 
		    return pattern.matcher(str).matches();    
		 } 

		//用ascii码

		public static boolean isNumberic3(String str){
		   for(int i=str.length();--i>=0;){
		      int chr=str.charAt(i);
		      if(chr<48 || chr>57)
		         return false;
		   }
		   return true;
		}
		
		public static void main(String arg[]){
			String str="21.2";
			System.out.println(NumberUtil.isNumberic1(str));
			System.out.println(NumberUtil.isNumberic1(str));
			System.out.println(NumberUtil.isNumberic1(str));
		}
}
