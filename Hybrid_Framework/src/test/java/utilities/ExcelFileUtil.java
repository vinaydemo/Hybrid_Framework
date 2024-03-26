package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.reporters.jq.Main;

public class ExcelFileUtil 
{
	Workbook wb;
	//constructor for reading excel path
	public ExcelFileUtil(String ExcelPath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(ExcelPath);
		wb = WorkbookFactory.create(fi);	
	}
	public int rowcount(String Sheetname) 
	{
		return wb.getSheet(Sheetname).getLastRowNum();
	}
	public String getCellData(String Sheetname,int row,int column) throws Throwable
	{
		String Data ;
		if(wb.getSheet(Sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC) 
		{
			int CellData =(int)wb.getSheet(Sheetname).getRow(row).getCell(column).getNumericCellValue();
			Data = String.valueOf(CellData);
		}else 
		{
			Data=wb.getSheet(Sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return Data;
	}
	public void setCellData(String Sheetname,int row,int coliumn,String result,String writeexcel) throws Throwable
	{
		Sheet ws = wb.getSheet(Sheetname);
		Row rownum = ws.getRow(row);
		Cell cell = rownum.createCell(coliumn);
		cell.setCellValue(result);
		if(result.equalsIgnoreCase("Pass")) 
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(coliumn).setCellStyle(style);
			
		}
		else if(result.equalsIgnoreCase("Fail"))
		{

			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(coliumn).setCellStyle(style);
			
		}
		else if(result.equalsIgnoreCase("Blocked"))
		{

			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(coliumn).setCellStyle(style);
		  }
		
		FileOutputStream fo = new FileOutputStream(writeexcel);
		wb.write(fo);
	  
      	}
	
	
  
}


