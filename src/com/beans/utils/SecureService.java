package com.beans.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SecureService {
	
	public boolean checkPasswordPattern(String password){
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@S#$%^&+=])(?=\\S+$).{6,20}";
		//(?=\\S+$).{6,20} ====> more than 6 charactor and less than 20 charactor
		return password.matches(pattern);
	}
	public String genPasswordMD5(String pass){
        try{
        	 MessageDigest m = MessageDigest.getInstance("MD5");
             m.update(pass.getBytes(),0,pass.length());
             return new BigInteger(1,m.digest()).toString(16);
        }catch(Exception e) {
        	 MessageLogService.doWriteMessage(e.toString());
			 return pass;
        }
    }
	public String genPasswordSHA256(String pass){
        try{
        	 MessageDigest m = MessageDigest.getInstance("SHA-256");
        	 m.update(pass.getBytes());
             return m.digest().toString();
        }catch(Exception e) {
       	 	 MessageLogService.doWriteMessage(e.toString());
			 return pass;
        }
    }
	public String randomPassword(int size){ // gen new password
		String AlphaNumericString = 
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"+
			"!@#$%^&*_=+-/.?<>"+
	        "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(size); 
		
		for (int i = 0; i < size; i++) {
			int index = (int)(AlphaNumericString.length() * Math.random()); 
			sb.append(AlphaNumericString.charAt(index)); 
		} 
		return sb.toString();
	}
}