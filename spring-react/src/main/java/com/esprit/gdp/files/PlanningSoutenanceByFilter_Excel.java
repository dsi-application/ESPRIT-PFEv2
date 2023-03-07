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

import com.esprit.gdp.dto.StudentSTNExcelDto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class PlanningSoutenanceByFilter_Excel
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
	
    public PlanningSoutenanceByFilter_Excel(List<StudentSTNExcelDto> studentsDtos, String sessionLabel, String PSFile)
	{
		try
		{
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(sessionLabel);
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
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,16));
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(" Planning Soutenances Suivant Filtre - Session " + sessionLabel);
			cell.setCellStyle(sheetTitleStyle);
			
			// Empty Row
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,16));
						
			// Column Title
			HSSFCellStyle columnTitleStyle = columnTitleStyle(workbook, sheet, whiteIndex, greyIndex);
			HSSFRow rowSubhead = sheet.createRow((short) 2);
			rowSubhead.setHeightInPoints(25);
			HSSFCell cellColumnTitle = rowSubhead.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));
			cellColumnTitle.setCellValue("Détails Étudiant");cellColumnTitle.setCellStyle(columnTitleStyle);
						
			cellColumnTitle = rowSubhead.createCell(5);
			sheet.addMergedRegion(new CellRangeAddress(2,2,5,16));
			cellColumnTitle.setCellValue("Détails Soutenance");cellColumnTitle.setCellStyle(columnTitleStyle);
			
	 		/****************/
			
			HSSFRow rowhead = sheet.createRow((short) 3);
			rowhead.setHeightInPoints(20);
			HSSFCell cellColumnSubTitle = rowhead.createCell(0);
			cellColumnSubTitle.setCellValue("Indentifiant");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(0, 4000);
			
			cellColumnSubTitle = rowhead.createCell(1);
			cellColumnSubTitle.setCellValue("Nom & Prénom");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(1, 8000);
			
			cellColumnSubTitle = rowhead.createCell(2);
			cellColumnSubTitle.setCellValue("Mail");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(2, 9000);
			
			cellColumnSubTitle = rowhead.createCell(3);
			cellColumnSubTitle.setCellValue("Téléphone");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(3, 4000);
			
			cellColumnSubTitle = rowhead.createCell(4);
			cellColumnSubTitle.setCellValue("Classe");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(4, 4000);
			
			cellColumnSubTitle = rowhead.createCell(5);
			cellColumnSubTitle.setCellValue("Date");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(5, 3500);
			
			cellColumnSubTitle = rowhead.createCell(6);
			cellColumnSubTitle.setCellValue("Heure");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(6, 2300);
			
			cellColumnSubTitle = rowhead.createCell(7);
			cellColumnSubTitle.setCellValue("Salle");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(7, 2000);
			
			cellColumnSubTitle = rowhead.createCell(8);
			cellColumnSubTitle.setCellValue("Président Jury");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(8, 8000);
			
			cellColumnSubTitle = rowhead.createCell(9);
			cellColumnSubTitle.setCellValue("Mail Président Jury");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(9, 8000);
			
			cellColumnSubTitle = rowhead.createCell(10);
			cellColumnSubTitle.setCellValue("Encadrant Pédagogique");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(10, 8000);
			
			cellColumnSubTitle = rowhead.createCell(11);
			cellColumnSubTitle.setCellValue("Mail Encadrant Pédagogique");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(11, 8000);
			
			cellColumnSubTitle = rowhead.createCell(12);
			cellColumnSubTitle.setCellValue("Membre");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(12, 8000);
			
			cellColumnSubTitle = rowhead.createCell(13);
			cellColumnSubTitle.setCellValue("Mail Membre");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(13, 8000);
			
			cellColumnSubTitle = rowhead.createCell(14);
			cellColumnSubTitle.setCellValue("Pays");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(14, 5000);
			
			cellColumnSubTitle = rowhead.createCell(15);
			cellColumnSubTitle.setCellValue("Mail Entreprise");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(15, 8000);
			
			cellColumnSubTitle = rowhead.createCell(16);
			cellColumnSubTitle.setCellValue("Motif Dépot Incomplet");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(16, 10000);
			
			for(int i=0; i<studentsDtos.size(); i++)
			{
				
				row = sheet.createRow(i + 4);
				
				HSSFCell sheetCell = row.createCell(0);
				sheetCell.setCellValue(studentsDtos.get(i).getIdEt());
				
				sheetCell = row.createCell(1);
				sheetCell.setCellValue(studentsDtos.get(i).getNomet() + " " + studentsDtos.get(i).getPrenomet());
				
				sheetCell = row.createCell(2);
				sheetCell.setCellValue(studentsDtos.get(i).getAdressemailesp());
				
				sheetCell = row.createCell(3);
				sheetCell.setCellValue(studentsDtos.get(i).getTelet());
				
				sheetCell = row.createCell(4);
				sheetCell.setCellValue(studentsDtos.get(i).getClasse());
				
				sheetCell = row.createCell(5);
				sheetCell.setCellValue(studentsDtos.get(i).getDateSoutenance());
				
				sheetCell = row.createCell(6);
				sheetCell.setCellValue(studentsDtos.get(i).getHeureSoutenance());
				
				sheetCell = row.createCell(7);
				sheetCell.setCellValue(studentsDtos.get(i).getSalle());
				
				sheetCell = row.createCell(8);
				sheetCell.setCellValue(studentsDtos.get(i).getJuryPresident());
				
				sheetCell = row.createCell(9);
				sheetCell.setCellValue(studentsDtos.get(i).getMailJuryPresident());
				
				sheetCell = row.createCell(10);
				sheetCell.setCellValue(studentsDtos.get(i).getPedagogicalEncadrant());
				
				sheetCell = row.createCell(11);
				sheetCell.setCellValue(studentsDtos.get(i).getMailEncadrant());
				
				sheetCell = row.createCell(12);
				sheetCell.setCellValue(studentsDtos.get(i).getExpert());
				
				sheetCell = row.createCell(13);
				sheetCell.setCellValue(studentsDtos.get(i).getMailExpert());
				
				sheetCell = row.createCell(14);
				sheetCell.setCellValue(studentsDtos.get(i).getPays());
				
				sheetCell = row.createCell(15);
				sheetCell.setCellValue(studentsDtos.get(i).getMailSociete());
				
				sheetCell = row.createCell(16);
				sheetCell.setCellValue(studentsDtos.get(i).getMotifDepotIncomplet());
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
