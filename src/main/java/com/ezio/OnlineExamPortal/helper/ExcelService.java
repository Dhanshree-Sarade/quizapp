package com.ezio.OnlineExamPortal.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {
	
//	 public ByteArrayInputStream createExcelTemplate() throws IOException {
//	        String[] columns = {"QueId", "QueNum", "Questions", "Option1", "Option2", "Option3", "Option4", "Answer", "Marks", "topicId"};
//
//	        Workbook workbook = new XSSFWorkbook();
//	        Sheet sheet = workbook.createSheet("Questions");
//
//	        Row headerRow = sheet.createRow(0);
//	        for (int i = 0; i < columns.length; i++) {
//	            Cell cell = headerRow.createCell(i);
//	            cell.setCellValue(columns[i]);
//	        }
//
//	        ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        workbook.write(out);
//	        workbook.close();
//
//	        return new ByteArrayInputStream(out.toByteArray());
//	    }
	
	
	public ByteArrayInputStream createExcelTemplate() throws IOException {
        String[] columns = {"QueId", "QueNum", "Questions", "Option1", "Option2", "Option3", "Option4", "Answer", "Marks", "topicId"};

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Questions");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setFontName("Times New Roman");

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a CellStyle for the cell borders
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setFont(headerFont);

        // Create the header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Apply border style to all cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.getCell(i);
            cell.setCellStyle(cellStyle);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

}
