package com.lin.poi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReplaceManager {
  public static final String PATH ="E:\\vova\\lcp_1592_checkout\\themis_android\\reslib\\src\\main\\res";
  public static final String EXCEL_PATH="C:\\Users\\dhlin\\Documents\\WXWork\\1688850333997958\\Cache\\File\\2019-08\\checkout语言包(5).xlsx";
  public static final Map<String,String> LANGUAGE_MAP = initLanguage();
  public static void main(String[] args) {
	  try {
		  
		  HashMap<String,HashMap<String,String>> tmap =ExcelReader.getExcelContent(EXCEL_PATH);
		  //1 为插入多语言   2为删除上次插入的
		  int type =1;
		  switch(type){
			  case 1:{
				  tmap.forEach((k,v)->{
					  String fname = LANGUAGE_MAP.get(k);
					  if(fname!=null) {
							 String path = fname.equals("")?"":"-"+fname;
							 File f = new File(PATH+"\\values"+path+"\\strings.xml");
							 System.out.println(f.toString());
							 HashMap<String,String>  tm =  tmap.get(k);
							 System.out.println(k+"_"+LANGUAGE_MAP.get(k)+"==="+tm);
								try {
										replaceString(f,tm);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					  }
				  });
			  }
			  break;
			  case 2:{
				  tmap.forEach((k,v)->{
					  String fname = LANGUAGE_MAP.get(k);
					  if(fname!=null) {
							 String path = fname.equals("")?"":"-"+fname;
							 File f = new File(PATH+"\\values"+path+"\\strings.xml");
							 System.out.println(f.toString());
							 HashMap<String,String>  tm =  tmap.get(k);
							 System.out.println(k+"_"+LANGUAGE_MAP.get(k)+"==="+tm);
								try {
									deleteFile(f,f,tm.size());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					  }
				  });
			  }
		  }
			 

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public static void replaceString(File file, HashMap<String,String> map) throws Exception {
	  if(!file.exists()) {
		  file.createNewFile();
	  }
	  
	  appendFile(file,file, map);
  }
  
  public static void replaceFile(File file, HashMap<String,String> map) throws Exception {
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		
		bw.write("<resources>");
		bw.newLine();
		StringBuffer sb =new StringBuffer();
		
		for (String name : map.keySet()) {
			sb =new StringBuffer();
			bw.write("\t<string  name=\"");
			bw.write(name);
			bw.write("\">");
			bw.write(map.get(name));
			bw.write("</string>");
			bw.newLine();
		}
		bw.write("</resources>");
		bw.flush();
		bw.close();
  }
  
  
  public static void appendFile(File inputFile,File outputFile, HashMap<String,String> map) throws Exception {
	    BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(inputFile),"utf-8"));
	    List<String> lines =new ArrayList<String>();
	    String line =null;
	    while((line=br.readLine())!=null){
	    	lines.add(line);
	    }
	    br.close();		
	    if(lines.size()>0)
	    	lines.remove(lines.size()-1);
	    
		StringBuffer sb;
		for (String name : map.keySet()) {
			sb =new StringBuffer();
			sb.append("  <string  name=\"");
			sb.append(name);
			sb.append("\">");
			sb.append(getValue(map.get(name)));
			sb.append("</string>");
			lines.add(sb.toString());
		}
		lines.add("</resources>");
		
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
		for (String s : lines) {
			bw.write(s);
			bw.newLine();
		}
		bw.flush();
		bw.close();

  }
  public static String getValue(String value) {
		if(value.contains("'")) {
			int pos = value.indexOf('\'');

			if(pos>=1) {
				if(value.charAt(pos-1)!='\\'){
					System.out.println("i have "+value + pos);
					return value.replace("'", "\\'");
				}
			}
		}
		return value;
  }
  
  public static void deleteFile(File inputFile,File outputFile,int num) throws Exception {
	    BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(inputFile),"utf-8"));
	    List<String> lines =new ArrayList<String>();
	    String line =null;
	    while((line=br.readLine())!=null){
	    	lines.add(line);
	    }
	    br.close();	
	    num=num+1;
	    while(num>0) {
		    if(lines.size()>0)
		    	lines.remove(lines.size()-1);
		    else
		    	break;
		    num--;
	    }
		lines.add("</resources>");
		
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
		for (String s : lines) {
			bw.write(s);
			bw.newLine();
		}
		bw.flush();
		bw.close();

}
  
  public static Map<String,String> initLanguage() {
	  Map<String,String> map =new HashMap<String,String>();
	  map.put("en", "");
	  map.put("ar", "ar");
	  map.put("cs", "cs");
	  map.put("da", "da");
	  map.put("de", "de");
	  map.put("es", "es");
	  map.put("fi", "fi");
	  map.put("fr", "fr");
	  map.put("it", "it");
	  map.put("he", "iw");
	  map.put("nl", "nl");
	  map.put("no", "no");
	  map.put("pl", "pl");
	  map.put("pt", "pt");
	  map.put("ru", "ru");
	  map.put("sk", "sk");
	  map.put("se", "sv");
	  map.put("tr", "tr");
	  map.put("id","in");
	  map.put("jp","ja");
	  map.put("tw", "tw");

	  return map;
  }
}	
