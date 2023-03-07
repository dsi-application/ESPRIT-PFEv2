package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.esprit.gdp.dto.TeacherQuotaEncadrementExpertiseDto;
import com.esprit.gdp.dto.TeacherQuotaPresidenceMembreDto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class EtatPresidencesAndMembresByYear_Excel
{

	BaseColor myRedColor = new BaseColor(204, 0, 0);
    BaseColor myGreyColor = new BaseColor(115, 115, 115);
    BaseColor myTableGreyColor = new BaseColor(230, 230, 230);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, myRedColor);
    Font subTitleStyle = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9);
    
    Font noteStyle11 = new Font(Font.FontFamily.HELVETICA, 11);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 12);
    Font noteStyle14 = new Font(Font.FontFamily.HELVETICA, 14);
    Font noteItalicStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC, BaseColor.GRAY);
	
    public EtatPresidencesAndMembresByYear_Excel(String selectedYear, List<TeacherQuotaPresidenceMembreDto> ess, String PSFile)
	{
		try
		{
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Année Universitaire " + selectedYear + " - " + (Integer.parseInt(selectedYear) + 1));
			sheet.setHorizontallyCenter(true);
			
			// My Customized Colors
			HSSFPalette palette = workbook.getCustomPalette();
			
			HSSFColor red = palette.findSimilarColor(179, 0, 0);short redIndex = red.getIndex();
			HSSFColor white = palette.findSimilarColor(255, 255, 255);short whiteIndex = white.getIndex();
			HSSFColor grey = palette.findSimilarColor(140, 140, 140);short greyIndex = grey.getIndex();
			
			// Sheet Title
			HSSFCellStyle sheetTitleStyle = sheetTitleStyle(workbook, whiteIndex, redIndex);
		 	HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("État Presidences & Membres\r\n" + selectedYear + " - " + (Integer.parseInt(selectedYear) + 1));
			cell.setCellStyle(sheetTitleStyle);
			
			// Empty Row
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,4));
						
			// Column Title
			HSSFCellStyle columnTitleStyle = columnTitleStyle(workbook, sheet, whiteIndex, greyIndex);
			HSSFRow rowSubhead = sheet.createRow((short) 2);
			rowSubhead.setHeightInPoints(25);
			
//			HSSFCell cellColumnCls = rowSubhead.createCell(0);
//			// sheet.addMergedRegion(new CellRangeAddress(2,3,0,0));
//			sheet.addMergedRegion(new CellRangeAddress(2,3,6,6));
//			cellColumnCls.setCellValue("Classe");cellColumnCls.setCellStyle(columnTitleStyle);
//			
//			HSSFCell cellColumnTitle = rowSubhead.createCell(1);
//			// sheet.addMergedRegion(new CellRangeAddress(2,2,1,4));
//			sheet.addMergedRegion(new CellRangeAddress(2,2,2,5));
//			cellColumnTitle.setCellValue("Détails Étudiant");cellColumnTitle.setCellStyle(columnTitleStyle);
//			
//			cellColumnTitle = rowSubhead.createCell(5);
//			// sheet.addMergedRegion(new CellRangeAddress(2,2,5,6));
//			sheet.addMergedRegion(new CellRangeAddress(2,2,0,1));
//			cellColumnTitle.setCellValue("Détails Encadrant Académique");cellColumnTitle.setCellStyle(columnTitleStyle);
			

			HSSFCell cellColumnTitle = rowSubhead.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,2));
			cellColumnTitle.setCellValue("Détails Enseignant");cellColumnTitle.setCellStyle(columnTitleStyle);

			HSSFCell cellColumnCls = rowSubhead.createCell(3);
			sheet.addMergedRegion(new CellRangeAddress(2,2,3,4));
			cellColumnCls.setCellValue("Quotas");cellColumnCls.setCellStyle(columnTitleStyle);
			
			
	 		/****************/
			
			HSSFRow rowhead = sheet.createRow((short) 3);
			rowhead.setHeightInPoints(20);
			HSSFCell cellColumnSubTitle = rowhead.createCell(0);
			cellColumnSubTitle.setCellValue("Nom & Prénom");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(0, 8000);
			
			cellColumnSubTitle = rowhead.createCell(1);
			cellColumnSubTitle.setCellValue("Indentifiant");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(1, 4000);
			
			cellColumnSubTitle = rowhead.createCell(2);
			cellColumnSubTitle.setCellValue("Unité Pédagogique");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(2, 5500);
			
			cellColumnSubTitle = rowhead.createCell(3);
			cellColumnSubTitle.setCellValue("Présidences");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(3, 4000);
			
			cellColumnSubTitle = rowhead.createCell(4);
			cellColumnSubTitle.setCellValue("Membres");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(4, 3300);
			
			for(int i=0; i<ess.size(); i++)
			{
				
				row = sheet.createRow(i + 4);
				
				HSSFCell sheetCell = row.createCell(0);
				sheetCell.setCellValue(ess.get(i).getNom());
				
				sheetCell = row.createCell(1);
				sheetCell.setCellValue(ess.get(i).getIdentifiant());
				
				sheetCell = row.createCell(2);
				sheetCell.setCellValue(ess.get(i).getUp());
				
				sheetCell = row.createCell(3);
				sheetCell.setCellValue(ess.get(i).getNbrPresidences());
				
				sheetCell = row.createCell(4);
				sheetCell.setCellValue(ess.get(i).getNbrMembres());
				
			}
			
			FileOutputStream fileOut = new FileOutputStream(PSFile);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
//			System.out.println("Excel file has been generated successfully: " + PSFile);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


    private HSSFCellStyle sheetTitleStyle(HSSFWorkbook workbook, short whiteIndex, short redIndex)
	{
    	HSSFCellStyle cellStyle = null;
    	cellStyle = workbook.createCellStyle();
		
    	HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("Courier New");
        font.setColor(whiteIndex);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        cellStyle.setFillForegroundColor(redIndex);
	    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    cellStyle.setBorderBottom(BorderStyle.HAIR);  
	    cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
	    cellStyle.setBorderRight(BorderStyle.HAIR);  
	    cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyle.setBorderLeft(BorderStyle.HAIR);  
	    cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
	    cellStyle.setBorderTop(BorderStyle.HAIR);  
	    cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    
	    return cellStyle;
	}
	
	private HSSFCellStyle columnTitleStyle(HSSFWorkbook workbook, HSSFSheet sheet, short whiteIndex, short greyIndex)
	{
		HSSFCellStyle columnTitleStyle = null;
		columnTitleStyle = workbook.createCellStyle();
		
		HSSFFont fontCTS = workbook.createFont();
		fontCTS.setFontName("Courier New");
		fontCTS.setColor(whiteIndex);
		fontCTS.setBold(true);
	    columnTitleStyle.setFont(fontCTS);
	    columnTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    columnTitleStyle.setAlignment(HorizontalAlignment.CENTER);
	  
	    columnTitleStyle.setFillForegroundColor(greyIndex);
	    columnTitleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    columnTitleStyle.setBorderBottom(BorderStyle.HAIR);  
	    columnTitleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
	    columnTitleStyle.setBorderRight(BorderStyle.HAIR);  
	    columnTitleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    columnTitleStyle.setBorderLeft(BorderStyle.HAIR);  
	    columnTitleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
	    columnTitleStyle.setBorderTop(BorderStyle.HAIR);  
	    columnTitleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    
	    return columnTitleStyle;
	}
	
	
//	private void addMetaData(Document document)
//    {
//        document.addTitle("PV Etudiant");
//        document.addSubject("Sotenance PFE");
//        document.addKeywords("Esprit, PFE, PV");
//        document.addAuthor("Saria ESSID");
//        document.addCreator("Saria ESSID");
//    }

}
