package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil 
{
	XSSFWorkbook wb;
	public ExcelFileUtil(String Excelpath) throws IOException
	{
		FileInputStream fi= new FileInputStream(Excelpath);
		wb= new XSSFWorkbook(fi);
	}
	
	public int rowCount(String sheetname)
	
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	
	public String getcelldata(String sheetname,int row,int column)
	{
		String data="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int a=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(a);
		}
		else
		{
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	
	
	public void setcelldata(String sheetname,int row,int column,String status,String WriteExcel) throws IOException
	{
		XSSFSheet ws=wb.getSheet(sheetname);
		XSSFRow rc= ws.getRow(row);
		XSSFCell cell=rc.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		{
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
            rc.getCell(column).setCellStyle(style);
		}
	else if(status.equalsIgnoreCase("fail"))
			{
				XSSFCellStyle style=wb.createCellStyle();
				XSSFFont font = wb.createFont();
				font.setColor(IndexedColors.RED.getIndex());
				font.setBold(true);
				style.setFont(font);
	            rc.getCell(column).setCellStyle(style);
			}
	 else if(status.equalsIgnoreCase("blocked"))
		{
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
            rc.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);
		
	}

}
