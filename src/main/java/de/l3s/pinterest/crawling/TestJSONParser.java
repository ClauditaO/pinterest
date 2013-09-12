package de.l3s.pinterest.crawling;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;


public class TestJSONParser {
	/*
	{
		"body": [
		         {
		        	 name: "JMOZ",
		        	 href: "/jwmoz/jmoz/",
		        	 src: "http://media-cache-ec0.pinimg.com/216x146/dc/ab/56/dcab566549e1d626110af92bf321f9dd.jpg"
		         },
		         {
		        	 name: "Take me there",
		        	 href: "/jwmoz/take-me-there/",
		        	 src: "http://media-cache-ak0.pinimg.com/216x146/e2/d6/15/e2d615ac2ff7a43213d6c7a6bca2137c.jpg"
		         },
		         ],
	    "meta": {
		            "count": 2
	            }
	}**/
	
	/*
	 * Class of items inside body
	 */
	static class Item {
		String name;
		String href;
		String src;
	}
	/*
	 * Class of meta inside body
	 */
	static class Meta {
		String count;
	}
    
	/*
	 * Main Json class that contains body: list of items, and meta
	 */
	static class Page {
		List<Item> body;
		Meta meta;
	}

	public static void main (String[] args) {
		String json;
		try {
			json = readUrl("http://pinterestapi.co.uk/jwmoz/boards");
			Gson gson = new Gson();        
			Page page = gson.fromJson(json, Page.class);
			for (Item item : page.body)
				System.out.println(item.name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
	/**
	 * Read URL into String
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read); 

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}

	}
}