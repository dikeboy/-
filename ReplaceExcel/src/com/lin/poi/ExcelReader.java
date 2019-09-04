package com.lin.poi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public static void main(String[] args) {

		HashMap<String,HashMap<String,String>> map = 	getExcelContent(null);
		
		Set<String> keys = map.keySet();
		for(String s: keys) {
			System.out.println(s+"="+map.get(s));
		}
		
//		System.out.println("شُوهد مؤخراً");
//		
//		try {
//			BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(new File("aa.txt")),"utf-8"));
//			String line= null;
//			while((line=br.readLine())!=null) {
//				System.out.println(line);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

    }
	
	public static HashMap<String,HashMap<String,String>> getExcelContent(String filePath){
        Workbook wb =null;
        Sheet sheet = null;
        if(filePath==null)
        	filePath = "E:\\vova\\develop\\themis_android\\app\\vv.xlsx";
        
        wb = readExcel(filePath);
        if(wb != null){
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();
            int rowNum=sheet.getLastRowNum()+1;//获得总行数
            
            System.out.println("row="+rowNum+" column="+coloumNum);
            HashMap<Integer,String> rowKey =new HashMap<Integer,String>();
            HashMap<Integer,String> colKey = new HashMap<Integer,String>();
           for(int i=1;i<rowNum;i++) {
          		Object cell = getCellFormatValue(sheet.getRow(i).getCell(0));
        		if(cell.equals("")) {
        			break;
        		}
        		rowKey.put(i, cell.toString().trim());
           }
           
           for(int i=1;i<coloumNum;i++) {
         		Object cell = getCellFormatValue(sheet.getRow(0).getCell(i));
	       		if(cell.equals("")) {
	       			break;
	       		}
	       		colKey.put(i, cell.toString().trim().substring(0, 2));
           }
            
            HashMap<String,HashMap<String,String>> map =new HashMap<String,HashMap<String,String>>();
            
            
            for(int i =1;i<coloumNum;i++) {
          		LinkedHashMap<String,String> sm=new LinkedHashMap<String,String>();
            	for(int j=1;j<rowNum;j++) {
            		Object cell = getCellFormatValue(sheet.getRow(j).getCell(i));
//            		if(cell.equals("")) {
//            			continue;
//            		}
            		if(rowKey.get(j)!=null)
            			sm.put(rowKey.get(j), cell.toString());
            	}
            	map.put(colKey.get(i), sm);
            }
            
            return map;
        }
        return null;
	}
    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is =new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
            case NUMERIC:{
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            }
            case FORMULA:{
                //判断cell是否为日期格式
                if(DateUtil.isCellDateFormatted(cell)){
                    //转换为日期格式YYYY-mm-dd
                    cellValue = cell.getDateCellValue();
                }else{
                    //数字
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            case STRING:{
                cellValue = cell.getRichStringCellValue().getString();
                
                try {
//                	cellValue=new String(cell.getRichStringCellValue().getString().getBytes("utf-8"));
                	cellValue = cellValue.toString().replaceAll("(\n)", "");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


                break;
            }
            default:
                cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }

}
