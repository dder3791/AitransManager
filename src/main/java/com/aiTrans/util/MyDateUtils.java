package com.aiTrans.util;

import java.text.SimpleDateFormat;
import java.util.Date;





public class MyDateUtils {
	private static final String DATA_19 = "yyyy-MM-dd HH:mm:ss";
	private static final String DATA_10 = "yyyy-MM-dd";
	private static final String DATA_8 = "yyyyMMdd";
	public static String dateToStr(Date date,int formatLen){
		if(date==null){
			return null;
		}
		String patt = DATA_19;
		switch(formatLen){
			case 19 :
				patt = DATA_19;
				break;
			case 10 :
				patt = DATA_10;
				break;
			case 8 :
				patt = DATA_8;
				break;
		} 
		long t = date.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(patt);
		return sdf.format(t);
    }
	public static void main(String args[]){
		System.out.println(MyDateUtils.dateToStr(new Date(),22));
	}

}
