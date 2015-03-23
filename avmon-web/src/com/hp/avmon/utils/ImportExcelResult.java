package com.hp.avmon.utils;

import java.util.ArrayList;

public class ImportExcelResult {
	public ArrayList<ArrayList<String>> rows=new ArrayList<ArrayList<String>>();
	public int successRecordCount=0;
	public int faultRecordCount=0;
	public int minColumnCount=10000;
	public int maxColumnCount=0;
	public int rowCount=0;
	public int colCount=0;
	public String importPath="";
	public ImportExcelResult(){
		
	}
	public void updateStatics() {
		rowCount=rows.size();
		for(int i=0;i<rows.size();i++){
			if(minColumnCount>rows.get(i).size())
				minColumnCount=rows.get(i).size();
			if(maxColumnCount<rows.get(i).size())
				maxColumnCount=rows.get(i).size();
			
		}
		colCount=minColumnCount;
		successRecordCount=rowCount;
	}
}
