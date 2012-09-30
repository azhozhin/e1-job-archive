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
import java.util.HashMap;
import java.util.Map;

public class CachingHttpFetcher {

	String cacheLocation;
	UrlHelper urlHelper;
	static CachingHttpFetcher instance;
	Map<String,String> innerCache;
	
	public static CachingHttpFetcher getInstance(){
		if (instance==null){
			instance=new CachingHttpFetcher("cache");
		}
		return instance;
	}
	
	private CachingHttpFetcher(String cacheLocation){
		this.cacheLocation = cacheLocation+"/";
		this.urlHelper = UrlHelper.getInstance();
		this.innerCache=new HashMap<String, String>();
	}

	public String fetch(String address, String encoding) throws IOException{
		// TODO: caching time should be placed
		// TODO: caching folder should be cleaned over time
		StringBuilder result=new StringBuilder();
		try {
			String encodedUrl;
			if (!innerCache.containsKey(address)){
				encodedUrl=urlHelper.encode(address);
				innerCache.put(address, encodedUrl);
			}else{
				encodedUrl=innerCache.get(address);
			}
			String cacheFileName=cacheLocation+encodedUrl;

			File f=new File(cacheFileName);
			if (f.exists()){
				FileReader fr=new FileReader(cacheFileName);
				BufferedReader br=new BufferedReader(fr);
				String s;
				while((s=br.readLine())!=null){
					result.append(s);
				}
				fr.close();
				br.close();
				return result.toString();
			}else{
				System.err.format("\nFetching from internet %s\n cachefile: %s\n",address, encodedUrl);

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
			e.printStackTrace();
		} 
		return result.toString();
	}
	
	public String fetchWithRetry(String address, String encoding, int sleep){
		String result=null;
		try{
			result=fetch(address, encoding);
		}catch(IOException e){
			try {
				Thread.sleep(sleep);
				result=fetch(address,encoding);
			} catch (Exception e1) {
				try{
					Thread.sleep(sleep);
					result=fetch(address,encoding);
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
