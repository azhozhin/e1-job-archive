package ru.xrm.app.httpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CachingHttpFetcher {

	String cacheLocation;
	UrlHelper urlEncoder;

	public CachingHttpFetcher(String cacheLocation){
		this.cacheLocation = cacheLocation+"/";
		urlEncoder = new UrlHelper();
	}

	public String fetch(String address, String encoding){
		// TODO: caching time should be placed
		// TODO: caching folder should be cleaned over time
		StringBuilder result=new StringBuilder();
		try {
			String encodedUrl=urlEncoder.encode(address);
			String cacheFileName=cacheLocation+encodedUrl;

			File f=new File(cacheFileName);
			if (f.exists()){
				BufferedReader br=new BufferedReader(new FileReader(cacheFileName));
				String s;
				while((s=br.readLine())!=null){
					result.append(s);
				}
				br.close();
				return result.toString();
			}else{

				URL url=new URL(address);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();

				conn.connect();
				InputStream is=conn.getInputStream();
				BufferedReader br=new BufferedReader(new InputStreamReader(is, encoding));
				String s;
				while((s=br.readLine())!=null){
					result.append(s);
				}
				br.close();
				conn.disconnect();
				
				FileWriter fw=new FileWriter(cacheFileName);
				fw.write(result.toString());
				fw.close();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
}
