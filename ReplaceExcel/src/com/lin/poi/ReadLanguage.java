package com.lin.poi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadLanguage {

	public static void main(String[] args) {
		File f =new File("E:\\vova\\develop\\themis_android\\app\\src\\main\\res\\values");
		try {
			readDir(f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<String,String> readDir(File f) throws Exception {
		System.out.println("path="+f.getAbsolutePath()+" "+f.exists());
		if(f.exists()&&f.isDirectory()) {
			  File[] file = f.listFiles();
			  for(int i=0;i<file.length;i++) {
				 if(file[i].getName().equals("strings.xml")) {
				  return readLanguage(file[i]);
				 }
			  }
		}
		return null;
	}
	static Pattern p=Pattern.compile("name=\"([^\"]+)[^>]+>([^<]+)"); 
	public static HashMap<String,String>  readLanguage(File f) throws Exception {
		BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
		String line;
		
		HashMap<String,String> map =new HashMap<String,String>();
		while((line =br.readLine())!=null) {
			Matcher m= p.matcher(line);
			if(m.find()) {
				map.put(m.group(1),m.group(2));
		
			}
		
		}
		br.close();
		return map;
	}
}
