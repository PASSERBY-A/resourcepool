package com.hp.avmon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hp.avmon.utils.AvmonUtils;

public class PoiExcel {


	
	public static ImportExcelResult importExcel2007(String pathFile,int page){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		ImportExcelResult result=new ImportExcelResult();
		XSSFWorkbook wb = null;
		try {
			FileInputStream fs = new FileInputStream(pathFile);
			wb = new XSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		XSSFSheet sheet = wb.getSheetAt(page-1);
		for(int i=0;i<=sheet.getLastRowNum();i++){
			XSSFRow row = sheet.getRow(i);
			if(row!=null){
				ArrayList<String> rr=new ArrayList<String>();
				for(int j=0;j<row.getLastCellNum();j++){
					XSSFCell cell = row.getCell(j);
					if(cell!=null){
						String value="N/A";
						if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING){
							value=cell.getStringCellValue();
						}
						else if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){

								if(HSSFDateUtil.isCellDateFormatted(cell)){
									Date date=cell.getDateCellValue();
									if(date!=null){
										value=df.format(date);
									}
									else{
										value="";
									}
//									System.out.format("Date is %s \n",value);
								}
								else{
									value=String.valueOf(cell.getNumericCellValue());
								}
							
						}
						else if(cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA){
							if(cell.getCachedFormulaResultType()==XSSFCell.CELL_TYPE_NUMERIC){
								value=String.valueOf(cell.getNumericCellValue());
							}
							
						}
						else if(cell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
							value="null";
						}
						//System.out.format("add value -  %s \n",value);
						rr.add(value);
					}
					else{
						result.faultRecordCount++;
					}
				}
				boolean allNull=true;
				for(String ss:rr){
					if(!ss.equals("null")){
						allNull=false;
						break;
					}
				}
				if(!allNull){
					result.rows.add(rr);
				}
			}
		}
		
		result.updateStatics();
		result.importPath=pathFile;
		
		return result;
	}
	
	public static ImportExcelResult importExcel2003(String pathFile,int page){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		ImportExcelResult result=new ImportExcelResult();
		HSSFWorkbook wb = null;
		try {
			FileInputStream fs = new FileInputStream(pathFile);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		HSSFSheet sheet = wb.getSheetAt(page-1);
		for(int i=0;i<=sheet.getLastRowNum();i++){
			HSSFRow row = sheet.getRow(i);
			if(row!=null){
				ArrayList<String> rr=new ArrayList<String>();
				for(int j=0;j<row.getLastCellNum();j++){
					HSSFCell cell = row.getCell(j);
					if(cell!=null){
						String value="N/A";
						if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
							value=cell.getStringCellValue();
						}
						else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								Date date=cell.getDateCellValue();
								if(date!=null){
									value=df.format(date);
								}
								else{
									value="";
								}
//								System.out.format("Date is %s \n",value);
							}
							else{
								value=String.valueOf(cell.getNumericCellValue());
							}
						}
						else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
							if(cell.getCachedFormulaResultType()==HSSFCell.CELL_TYPE_NUMERIC){
								value=String.valueOf(cell.getNumericCellValue());
							}
						}
						else if(cell.getCellType()==HSSFCell.CELL_TYPE_BLANK){
							value="null";
						}
						rr.add(value);
					}
					else{
						result.faultRecordCount++;
					}
				}
				boolean allNull=true;
				for(String ss:rr){
					if(!ss.equals("null")){
						allNull=false;
						break;
					}
				}
				if(!allNull){
					result.rows.add(rr);
				}
			}
		}
		
		result.updateStatics();
		result.importPath=pathFile;
		
		return result;
	}
	
	public static ImportExcelResult importExcel(String pathFile,int page){
		try{
			if(pathFile.toLowerCase().endsWith("xlsx")){
				return importExcel2007(pathFile,page);
			}
			else{
				return importExcel2003(pathFile,page);
			}
		}catch(Exception e){
			System.out.println("Import Excel ERROR: "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public static ImportExcelResult importExcel(String pathFile){
		return importExcel(pathFile,1);
	}
	
	static public void main(String[] args) throws Exception {


		ImportExcelResult rr=importExcel("d:/tmp/test.xlsx",1);
		if(rr!=null){
			for(int i=0;i<rr.rowCount;i++){
				System.out.println("");
				for(String s:rr.rows.get(i)){
					System.out.print(s+"   ");
				}
			}
		}
	}

	static public void main2(String[] args) throws Exception {
		FileOutputStream fos = new FileOutputStream("d:\test.xls");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "first sheet");
		HSSFRow row = s.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("Hello! This message is generated from POI.");
		wb.write(fos);
		fos.close();
	}

	public static String generateExcelFile(String template, HashMap map) {
		if(template==null||template.equals("")){
			return generateExcelFile(map);
		}
		String downloadFileName=(String) map.get("downloadFileName");
		if(downloadFileName==null || downloadFileName.equals("")){
			downloadFileName=AvmonUtils.generateTimeId();
		}
		String rootPath=PoiExcel.class.getResource("/").getPath().substring(1);
		System.out.println("************"+rootPath);
		String inputFile=rootPath+"../template/"+template;
		String outputFile="temp/"+downloadFileName+".xls";
		String fullOutputFile=rootPath+"../../"+outputFile;
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(inputFile));
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		for(int i=0;i<=sheet.getLastRowNum();i++){
			HSSFRow row = sheet.getRow(i);
			if(row!=null){
				for(int j=0;j<row.getLastCellNum();j++){
					HSSFCell cell = row.getCell(j);
					if(cell!=null){
						if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
							String msg = cell.getStringCellValue();
							if(msg.startsWith("<list:")){
								String keyName=msg.substring(6,msg.length()-1);
								ArrayList<String> aa=(ArrayList<String>)map.get(keyName);
								if(aa!=null){
									for(int k=0;k<aa.size();k++){
										HSSFRow r = sheet.getRow(i+k);
										if(r==null){
											r=sheet.createRow(i+k);
										}
										HSSFCell c = r.getCell(j);
										if(c==null){
											c=r.createCell(j);
										}
										String strCellValue=String.valueOf(aa.get(k));
										c.setCellValue(strCellValue);
									}
								}
							}
							else if(msg.startsWith("<")){
								String keyName=msg.substring(1,msg.length()-1);
								String value=(String)map.get(keyName);
								if(value!=null)
									cell.setCellValue(value);
							}
						}
					}
				}
			}
		}
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(fullOutputFile);
			out.flush();
			wb.write(out); 
			out.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return fullOutputFile;
		
	}
	
	public static String generateExcelFile(HashMap map) {
		String downloadFileName=(String) map.get("downloadFileName");
		if(downloadFileName==null || downloadFileName.equals("")){
			downloadFileName=AvmonUtils.generateTimeId();
		}
		String rootPath=PoiExcel.class.getResource("/").getPath().substring(1);
		System.out.println("************"+rootPath);
		String inputFile=rootPath+"../template/blank.xls";
		String outputFile="temp/"+downloadFileName+".xls";
		String fullOutputFile=rootPath+"../../"+outputFile;
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(inputFile));
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		if(row!=null){
			for(int i=0;i<10;i++){
				HSSFCell cell = row.getCell(i);
				if(cell!=null){
					if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
						String msg = cell.getStringCellValue();

							if(msg.startsWith("<")){
								String keyName=msg.substring(1,msg.length()-1);
								String value=(String)map.get(keyName);
								if(value!=null)
									cell.setCellValue(value);
							}

					}
				}
			}
		}
		
		ArrayList<String> header=(ArrayList<String>) map.get("listHeader");
		
		//set headers
		if(header!=null){
			HSSFRow r = sheet.getRow(1);
			if(r==null){
				r=sheet.createRow(1);
			}
			for(int col=0;col<header.size();col++){
				String hh=header.get(col);
				HSSFCell c = r.getCell(col);
				if(c==null){
					c=r.createCell(col);
				}
				c.setCellValue(hh);
			}
		}
		//set cells
		if(header!=null){
			for(int col=0;col<header.size();col++){
				String hh=header.get(col);
				
				ArrayList<String> vals=(ArrayList<String>) map.get("list."+hh);
				if(vals==null){
					System.out.format("column %s not found\n","list."+hh);
				}
				else{
					for(int i=0;i<vals.size();i++){
						HSSFRow r = sheet.getRow(i+2);
						if(r==null){
							r=sheet.createRow(i+2);
						}
						HSSFCell c = r.getCell(col);
						if(c==null){
							c=r.createCell(col);
						}
						String strCellValue=String.valueOf(vals.get(i));
						c.setCellValue(strCellValue);
					}
				}
			}
		}
			
			
		FileOutputStream out;
		try {
			out = new FileOutputStream(fullOutputFile);
			out.flush();
			wb.write(out); 
			out.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return fullOutputFile;
		
	}

}
