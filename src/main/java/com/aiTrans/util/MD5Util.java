package com.aiTrans.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
      public static String ecode(String password)
      {
    	  StringBuffer sb=new StringBuffer();
    	  try
    	  {
				MessageDigest digest=MessageDigest.getInstance("MD5");
				byte[] result=digest.digest(password.getBytes());
				for(int i=0;i<result.length;i++)
				{
					int number=(result[i]&0xff)-3;//
					//int number=(result[i]&0xff);
					String temp=Integer.toHexString(number);
					if(temp.length()==1)
					{
						 sb.append("0");
						 sb.append(temp);
					}
					else
					{
						sb.append(temp);
					}
				}
				return sb.toString();
		  } 
    	  catch (NoSuchAlgorithmException e) 
    	  {
			
				e.printStackTrace();
				return "";
		  }
    	 
      }
      
     
      public static void main(String[] args)
      {
    	 System.out.println(ecode("123456"));
      }
      
     
      

}
