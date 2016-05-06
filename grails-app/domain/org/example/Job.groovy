package org.example
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
class Job {
    
    String filename
    String fullPath
    Date uploadDate = new Date()
    
    static SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
    static SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
    static def rowsQ;
    static def rowsCur=0;
    
    static def createHelper
    static def workbook;
    static def sheet;
    static def hlink_style;
    static def rowIterator;
    static def cell;
    static def row;
    
    static constraints = {
        filename(blank:false,nullable:false)
        fullPath(blank:false,nullable:false)
    }
	
	public static int getRowsQ(){
		return rowsQ;
	}
	public static int getRowsCur(){
		return rowsCur;
	}
	
	
	public static boolean checkCellNull(Cell cell){
		cell = row.getCell(0);
		if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING) return true;
		else return false;
				
	}
	
	public static void makeOneRow(Row row) throws UnsupportedEncodingException, IOException{
		
  /*      cell=row.getCell(3); //ip to 14
        row.createCell(12).setCellValue(cell.getStringCellValue());*/

        cell=row.getCell(7); //time
        if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
        	Cell cell1= row.getCell(2);
        	cell1.setCellValue("[["+format2.format(cell1.getDateCellValue())+"]]");
       }
       else {
    	   row.createCell(2).setCellValue(cell.getStringCellValue().substring(11,19));
       }
  /*      cell=row.getCell(1); //date
        row.createCell(2).setCellValue(format1.format(cell.getDateCellValue()));
        
        cell= row.getCell(6);
        if(cell.getCellType()==Cell.CELL_TYPE_STRING) row.createCell(13).setCellValue(cell.getStringCellValue());
        
        cell= row.getCell(5);
        if(cell.getCellType()==Cell.CELL_TYPE_STRING) row.createCell(8).setCellValue(cell.getStringCellValue());
        else row.createCell(8).setCellValue("");
        
        */
        cell = row.getCell(0);
        String value = new DecimalFormat("#.#######################").format(cell.getNumericCellValue());
        System.out.print("["+value+"]");
    /*  String checkedNum = CSV.checkNumber(value);
     //   System.out.print("["+CSV.checkNumber(value)+"]");
        row.createCell(9).setCellValue(checkedNum);
        row.createCell(10).setCellValue(Utils.timeZone(checkedNum));*/
      //  cell= row.getCell(6);
      //  cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell = row.createCell(15);
        cell.setCellValue(value);
        Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);

        link.setAddress(Utils.numbers(value));
        cell.setHyperlink(link);
        cell.setCellStyle(hlink_style);
        
      String IP = row.getCell(3).getStringCellValue();
        System.out.println(IP.substring(0, IP.length()-3));
        row.createCell(3).setCellValue(IP.substring(0, IP.length()-3));
        row.createCell(14).setCellValue(IP.substring(IP.length()-2, IP.length())+" - "+Utils.checkIP(IP.substring(0, IP.length()-3)));
        

     
        Map<String, String> hashmap = Utils.googleNumber(value);
        int ii=0;
        for (Map.Entry<String, String> entry: hashmap.entrySet()){
        
        cell = row.createCell(16+ii);
        cell.setCellValue(entry.getKey());
        Hyperlink link2 = createHelper.createHyperlink(Hyperlink.LINK_URL);
        
        link2.setAddress(entry.getValue());
        cell.setHyperlink(link2);
        cell.setCellStyle(hlink_style);
        ii++;
        }
       

        
        /*      cell = row.createCell(14);
        cell.setCellValue("CallHistory");
        Hyperlink link1 = createHelper.createHyperlink(Hyperlink.LINK_URL);

        link1.setAddress(Utils.callHistory(value));
        cell.setHyperlink(link1);
        cell.setCellStyle(hlink_style);*/
        
	}

	public static void moveOneRow(){
        row = rowIterator.next();
	}
        
	public static void saveFile() throws IOException{
        file.close();

	}
    
        public static void mmm1(FileInputStream file1) throws IOException{
              FileInputStream file = (FileInputStream)file1;   
		workbook = new XSSFWorkbook(file1);
        sheet = workbook.getSheetAt(0);
        hlink_style = workbook.createCellStyle();
        Font hlink_font = workbook.createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setFont(hlink_font);
        createHelper = workbook.getCreationHelper();
        rowIterator = sheet.iterator();
        rowsQ=sheet.getPhysicalNumberOfRows();
        System.out.println("Total rows:" + rowsQ);
        System.out.println("Total columns:" + sheet.getRow(0).getPhysicalNumberOfCells());
        writeExcel ();
        file.close();
        }
    	public static void writeExcel () throws UnsupportedEncodingException, IOException{

        	moveOneRow();
                if (!checkCellNull(cell)) makeOneRow(row);
                rowsCur++;


                System.out.println("");
            if (rowIterator.hasNext()) writeExcel();
//            else saveFile();

    }
}