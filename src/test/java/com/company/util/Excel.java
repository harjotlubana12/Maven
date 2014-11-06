package com.company.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Excel {
	private FileInputStream filein;		//file path
	private HSSFWorkbook workbook;		//workbook
	private HSSFSheet sheet;			//sheetNo./sheetname
	private HSSFCell cell;				//row and column--->cell
	private DataFormatter formatter;
	public final int TOTAL_ROW;
	
//	private FileInputStream filein;		//file path
//	private XSSFWorkbook workbook;		//workbook
//	private XSSFSheet sheet;			//sheetNo./sheetname
//	private XSSFCell cell;				//row and column--->cell
	
	
	 public Excel( String filepath, String sheetname) throws Exception{
		filein = new FileInputStream(filepath);
//		workbook = new XSSFWorkbook(filein);
		workbook = new HSSFWorkbook(filein);
		sheet = workbook.getSheet(sheetname);
		TOTAL_ROW = sheet.getLastRowNum();
	}
	
	 @Deprecated
	public void setExcel( String filepath, String sheetname) throws Exception{
		filein = new FileInputStream(filepath);
//		workbook = new XSSFWorkbook(filein);
		workbook = new HSSFWorkbook(filein);
		sheet = workbook.getSheet(sheetname);
	}
	
	
	public String getCellValue(int row, int column) {
		cell = sheet.getRow(row).getCell(column);		//never works for other then text
		formatter = new DataFormatter();
		System.out.println(sheet.getRow(row).getCell(column).CELL_TYPE_BLANK);
//		return cell.getStringCellValue();
		return formatter.formatCellValue(cell);
	}
	public boolean isCellEmpty(int row, int column) {
		try {
			cell = sheet.getRow(row).getCell(column);
			return false;
		}catch(Exception e){
			return true;
		}
	}
	
//	public boolean isRowEmpty(int row) {
//		try {
//			cell = (HSSFCell)(Object)sheet.getRow(row);
//			return false;
//		}catch(Exception e) {
//			return true;
//		}
//	}
	
	public boolean isColumnEmpty(int row) {
		try {
			sheet.getRow(row).getLastCellNum();
			return false;
		}catch(Exception e) {
			return true;
		}
	}
	
	public int getColumnLength(int row) {
		System.out.println(sheet.getRow(row).getLastCellNum()+"   column count");
		return sheet.getRow(row).getLastCellNum();
	}
	
	@Deprecated
	public int getRowCount() {
		return sheet.getLastRowNum();
	}
	
	public void insertData(WebDriver driver, By locator, int row, int column) {
		if(!isCellEmpty(row, column)) {
			driver.findElement(locator).sendKeys(getCellValue(row, column));
		}
	}
	
	public List<String> createHeaderList() {
		int column = 0;
		List<String> header = new ArrayList<String>();
		while(true) {
			if(isCellEmpty(0, ++column))
				break;
			String cellHeader = null;
//			cellHeader = getCellValue(0, column).split(" ")[0]+StringUtils.capitalize(getCellValue(0, column).split(" ")[1]);
			cellHeader = StringUtils.capitalize(getCellValue(0, column));
			cellHeader.replace(" ", "").replace((getCellValue(0, column).charAt(0)+"").toUpperCase().charAt(0), getCellValue(0, column).charAt(0));
			System.out.println(cellHeader);
			header.add(cellHeader);
		}
		return header;
	}


}
