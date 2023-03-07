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

import com.esprit.gdp.dto.ExpertiseStatusExcelDto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class EtatExpertiseByOption_Excel
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

	public EtatExpertiseByOption_Excel(List<ExpertiseStatusExcelDto> ess, String optionlabel, String selectedyear, String PSFile)
	{
		try
		{


			Integer univYearplus1 = Integer.parseInt(selectedyear) + 1;
			String univYear = selectedyear + "-" + univYearplus1;

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Année Universitaire " + univYear);
			sheet.setHorizontallyCenter(true);

			// My Customized Colors
			HSSFPalette palette = workbook.getCustomPalette();

			HSSFColor red = palette.findSimilarColor(179, 0, 0);short redIndex = red.getIndex();
			HSSFColor white = palette.findSimilarColor(255, 255, 255);short whiteIndex = white.getIndex();
			HSSFColor black = palette.findSimilarColor(0, 0, 0);short blackIndex = black.getIndex();
			HSSFColor grey = palette.findSimilarColor(140, 140, 140);short greyIndex = grey.getIndex();

			// Sheet Title
			HSSFCellStyle sheetTitleStyle = sheetTitleStyle(workbook, whiteIndex, redIndex);
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(" État Expertise - " + optionlabel);
			cell.setCellStyle(sheetTitleStyle);

			// Sheet SimpleField
			HSSFCellStyle sheetSimpleFieldStyle = sheetSimpleFieldStyle(workbook, whiteIndex, blackIndex);

			// Empty Row
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,8));

			// Column Title
			HSSFCellStyle columnTitleStyle = columnTitleStyle(workbook, sheet, whiteIndex, greyIndex);
			HSSFRow rowSubhead = sheet.createRow((short) 2);
			rowSubhead.setHeightInPoints(25);


			HSSFCell cellColumnOpt = rowSubhead.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(2,3,0,0));
			cellColumnOpt.setCellValue("Option");cellColumnOpt.setCellStyle(columnTitleStyle);

			HSSFCell cellColumnCls = rowSubhead.createCell(1);
			sheet.addMergedRegion(new CellRangeAddress(2,3,1,1));
			cellColumnCls.setCellValue("Classe");cellColumnCls.setCellStyle(columnTitleStyle);

			HSSFCell cellColumnTitle = rowSubhead.createCell(2);
			sheet.addMergedRegion(new CellRangeAddress(2,2,2,6));
			cellColumnTitle.setCellValue("Détails Étudiant");cellColumnTitle.setCellStyle(columnTitleStyle);

			cellColumnTitle = rowSubhead.createCell(7);
			sheet.addMergedRegion(new CellRangeAddress(2,2,7,8));
			cellColumnTitle.setCellValue("Détails Expert");cellColumnTitle.setCellStyle(columnTitleStyle);

			/****************/

			HSSFRow rowhead = sheet.createRow((short) 3);
			rowhead.setHeightInPoints(20);
			HSSFCell cellColumnSubTitle = rowhead.createCell(0);
			//cellColumnSubTitle.setCellValue("Classe");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(0, 4000);

			cellColumnSubTitle = rowhead.createCell(1);
			sheet.setColumnWidth(1, 4000);

			cellColumnSubTitle = rowhead.createCell(2);
			cellColumnSubTitle.setCellValue("Indentifiant");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(2, 4000);

			cellColumnSubTitle = rowhead.createCell(3);
			cellColumnSubTitle.setCellValue("Nom & Prénom");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(3, 8000);

			cellColumnSubTitle = rowhead.createCell(4);
			cellColumnSubTitle.setCellValue("E-Mail");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(4, 10000);

			cellColumnSubTitle = rowhead.createCell(5);
			cellColumnSubTitle.setCellValue("Téléphone");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(5, 4000);

			cellColumnSubTitle = rowhead.createCell(6);
			cellColumnSubTitle.setCellValue("Note Rest 1");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(6, 4000);

			cellColumnSubTitle = rowhead.createCell(7);
			cellColumnSubTitle.setCellValue("Nom & Prénom");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(7, 8000);

			cellColumnSubTitle = rowhead.createCell(8);
			cellColumnSubTitle.setCellValue("E-Mail");cellColumnSubTitle.setCellStyle(columnTitleStyle);
			sheet.setColumnWidth(8, 9000);

			for(int i=0; i<ess.size(); i++)
			{

				row = sheet.createRow(i + 4);

				/********************************************************************************/
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
				/********************************************************************************/

				HSSFCell sheetCell = row.createCell(0);
				sheetCell.setCellValue(ess.get(i).getStudentOption());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(1);
				sheetCell.setCellValue(ess.get(i).getStudentClasse());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(2);
				sheetCell.setCellValue(ess.get(i).getStudentId());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(3);
				sheetCell.setCellValue(ess.get(i).getStudentFullName());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(4);
				sheetCell.setCellValue(ess.get(i).getStudentMail().toString());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(5);
				sheetCell.setCellValue(ess.get(i).getStudentPhone());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(6);
				sheetCell.setCellValue(ess.get(i).getRest1MarkByExpert());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(7);
				sheetCell.setCellValue(ess.get(i).getExpertFullName());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

				sheetCell = row.createCell(8);
				sheetCell.setCellValue(ess.get(i).getExpertMail());
				sheetCell.setCellStyle(sheetSimpleFieldStyle);

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

	private HSSFCellStyle sheetSimpleFieldStyle(HSSFWorkbook workbook, short whiteIndex, short blackIndex)
	{
		HSSFCellStyle cellStyle = null;
		cellStyle = workbook.createCellStyle();

		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)9);
		font.setFontName("Courier New");
		font.setColor(blackIndex);
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		cellStyle.setFillForegroundColor(whiteIndex);
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
