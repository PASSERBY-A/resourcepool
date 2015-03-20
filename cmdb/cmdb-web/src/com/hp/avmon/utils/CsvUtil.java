package com.hp.avmon.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

//import com.csvreader.CsvReader;
//import com.csvreader.CsvWriter;
import com.hp.xo.uip.cmdb.domain.Node;

public class CsvUtil {
	private Logger log=Logger.getLogger(CsvUtil.class);
	public static String dicu="ci_file_up";
	public static String dicd="ci_file_down";
	/**
     * 读取CSV文件
     */
//     public void  readeCsvTest(){
//         try {    
//             ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
//             String csvFilePath = "c:/test.csv";
//              //编码读
//              CsvReader reader = new CsvReader(csvFilePath,',',Charset.forName("SJIS"));
//              //表头 ~ 第一行
////            reader.readHeaders(); 
//            //逐行读入数据
//              while(reader.readRecord()){ 
//                  csvList.add(reader.getValues());
//              }            
//              reader.close();              
//              for(int row=0;row<csvList.size();row++){
//            	  System.out.println("--"+row);
//            	  for(String s:csvList.get(row)){
//                   System.out.println(s);
//                  }            	  
//              }
//         }catch(Exception ex){
//             System.out.println(ex);
//         }
//     }
     
     /**
      * 写入CSV文件
      */
//     public void writeCsvTest(){
//         try {
//              String csvFilePath = "c:/test.csv";
//              CsvWriter wr =new CsvWriter(csvFilePath,',',Charset.forName("ISO-8859-1"));
//              String[] contents = {"aaaaa大大","bbbbb小小","cccccc","ddddddddd"};                    
//              wr.writeRecord(contents);
//              wr.close();
//          } catch (IOException e) {
//             e.printStackTrace();
//          }
//     }

//     public List<String[]> readCsvFile(String fpath){
//    	 ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
//         String csvFilePath = "c:/test.csv";
//         CsvReader reader =null;
//		try {
//	      //编码读
//	      reader = new CsvReader(csvFilePath,',',Charset.forName("SJIS"));
//          //表头 ~ 第一行
////        reader.readHeaders(); 
//        //逐行读入数据
//          while(reader.readRecord()){ 
//              csvList.add(reader.getValues());
//          }            
//          reader.close();
//		} catch (Exception e) {
//			log.error("", e);
//			reader.close();
//			e.printStackTrace();
//		}
//          return csvList;
//     }
     
//     public String writeCsvToFlie(List<String[]> content,String fileName){
//    	 String cf="";
//    	 try {
//             String csvFile = new File("").getAbsolutePath()+"\\";
//             cf=csvFile+dicd+"\\"+fileName+".csv";
//             File f=new File(csvFile+dicd+"\\");
//             if(!f.exists()){
//            	 f.mkdir();
//             }
//             if(new File(cf).exists()){
//            	 cf=csvFile+dicd+"\\"+fileName+"_"+System.currentTimeMillis()+".csv";
//             }
//             log.debug("server path:"+cf);
//             CsvWriter wr =new CsvWriter(cf,',',Charset.forName("UTF-8"));//ISO-8859-1            
//             for(String[] ss:content){
//               wr.writeRecord(ss);
//             }
//             wr.close();
//         } catch (IOException e) {
//        	log.error("",e); 
//            e.printStackTrace();
//         }
//         return cf;
//     }
     public synchronized String writeXlsToFlie(List<String[]> content,String fileName){
 			String cf="";
 	    	 try {
 	             String csvFile = new File("").getAbsolutePath()+"\\";
 	             cf=csvFile+dicd+"\\"+fileName+".xls";
 	             File f=new File(csvFile+dicd+"\\");
 	             if(!f.exists()){
 	            	 f.mkdir();
 	             }
// 	             if(new File(cf).exists()){
// 	            	 cf=csvFile+dicd+"\\"+fileName+"_"+System.currentTimeMillis()+".xls";
// 	             }
 	             log.debug("server path:"+cf);
 	            WritableWorkbook book=Workbook.createWorkbook(new File(cf));
 	            WritableSheet sheet=book.createSheet("sheet1",0);
 	            WritableCellFormat wcf_content =book.NORMAL_STYLE;
 	            
 	             for(int i=0;i<content.size();i++){
 	            	 String[] ss=content.get(i);
// 	            	 sheet.insertRow(i);
 	            	 for(int j=0;j<ss.length;j++){
// 	            		if(j>=54){
// 	            			System.out.println("Label label"+i+j+"=new Label("+j+","+i+",\""+ss[j]+"\");");
// 	            		} 
// 	            		if(j<54){
 	            		Label label=new Label(j,i,ss[j],wcf_content);
 	            		sheet.addCell(label);
 	            		//jxl.write.Number number = new jxl.write.Number(1,0,789.123);
// 	            		}
 	            	 }
 	             }
 	     		
 	     		book.write();
 	    		book.close();
                
 	         } catch (Exception e) {
 	        	log.error("",e); 
 	            e.printStackTrace();
 	         }
 	         return cf;
 		}
 		
 	public List<List<Cell>> readJxl(String filePath){
 		List<List<Cell>> li=new ArrayList<List<Cell>>();
 		try{
 		Workbook book=
 		Workbook.getWorkbook(new File(filePath));
 		Sheet sheet=book.getSheet(0);
 		int row=sheet.getRows();
 		int col=sheet.getColumns();
 		
 		for(int i=0;i<row;i++){
 			List<Cell> lirow=new ArrayList<Cell>();
 			for(int j=0;j<col;j++){	
 		      Cell cell=sheet.getCell(j,i);
 		      lirow.add(cell);
 		    }
 			li.add(lirow);
 		}
 		book.close();
 		}catch(Exception e){
 		 log.error("", e);
 		}
 		return li;
 	}
 	
 	public List<String[]> transNode(List<Node> lin,String nodecolumn){
		List<String[]> lis=new ArrayList<String[]>();
		String metan[]=null;
		String metal[]=null;
		String type[]=new String[1];
		for(Node n:lin){
			if(metan==null){
			   Map<String,String> man=n.getMeta();
			   type[0]=n.getDerivedFrom();
			   lis.add(type);
			    metan=new String[man.size()];
			    metal=new String[man.size()];			   
			    Iterator<String> ite= man.keySet().iterator();
			    int i=0;
			    while(ite.hasNext()){
				   String key=ite.next();
				   metan[i]=key;
				   metal[i]=man.get(key);
				   i=i+1;
			    }
			   if(nodecolumn!=null&&!"".equals(nodecolumn)){
				  String[] ns= nodecolumn.split(";");
				  String[] metan1=new String[ns.length];
				  String[] metal1=new String[ns.length];
				  for(int i1=0;i1<ns.length;i1++){
					  metan1[i1]=ns[i1].toLowerCase();
					  metal1[i1]=man.get(metan1[i1])==null?"":man.get(metan1[i1]);
				  }
				  metan=metan1;metal=metal1;
			   }
			   lis.add(metan);
			   lis.add(metal);
			}
			String[] v=new String[metan.length];
			int j=0;
			for(String k:metan){
			  v[j]=n.getMapData().get(k);
			  if(v[j]==null && (k.equals("id")||k.equals("name"))){
				  log.debug(v[j]+k);
			  }else if(v[j]!=null && k.equals("name")){
				  log.debug(k+":"+v[j]);
			  }
			  j=j+1;
			}
			lis.add(v);
		}
    	 return lis; 
    }
     public static void main(String arg[]){
    	 CsvUtil ct=new CsvUtil();
//    	 ct.writeCsvTest();
//    	 ct.readeCsvTest();
    	 
    	 String p="c:\\testJxl.xls";
    	 ct.readJxl(p);
     }
}
