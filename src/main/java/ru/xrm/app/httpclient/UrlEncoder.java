package ru.xrm.app.httpclient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UrlEncoder {

	MessageDigest md;
	
	public UrlEncoder(){
		try {
			md=MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String encode(String url){
		md.update(url.getBytes());
		byte[] digest=md.digest();
		StringBuilder sb=new StringBuilder();

		for (int i=0;i<digest.length;i++){
			String hex=Integer.toHexString(0xFF & digest[i]);
			if (hex.length()==1){
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}
