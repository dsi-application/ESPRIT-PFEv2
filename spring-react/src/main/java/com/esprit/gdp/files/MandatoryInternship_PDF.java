package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class MandatoryInternship_PDF
{
	
	BaseColor myRedColor = new BaseColor(204, 0, 0);
	BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    BaseColor myTableGreyColor4 = new BaseColor(242, 242, 242);
    BaseColor myGreyColor = new BaseColor(115, 115, 115);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font subTitleStyle = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font subTitleStyleGray = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.GRAY);
    Font nbrConvStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, myRedColor);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    Font noteGreyBoldStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myGreyColor);
    Font noteGreyNormalStyle = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9);
    
    
    public MandatoryInternship_PDF(String DSFile, String studentFullName, String studentBirthDay)
    {
        try
        {
        	
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(DSFile));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent(document, studentFullName, studentBirthDay);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, String studentFullName, String studentBirthDay) throws DocumentException, MalformedURLException, IOException
    {
    	// English
    	DateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");  
		
    	document.add(addDefaultEmptyLine(1));
    	
    	Paragraph cpttl = new Paragraph("Tunis, " + formattedDate.format(new Date()) + " " + " " + " " + " " + " ", attributeStyle);
    	cpttl.setAlignment(Element.ALIGN_RIGHT);
    	addEmptyLine(cpttl, 1);
        document.add(cpttl);
        
        Paragraph cptt2 = new Paragraph("Mandatory Internship", titleStyle);
    	cptt2.setAlignment(Element.ALIGN_CENTER);
    	addEmptyLine(cptt2, 3);
        document.add(cptt2);
        
        Paragraph cptt4 = new Paragraph("");
        cptt4.add(new Phrase("ESPRIT, ", subTitleStyle));
        cptt4.add(new Phrase("School of Engineering,", attributeStyle));
        cptt4.add(new Phrase("Industrial area Chotrana 2 -BP 160- 2083- Technological Pole- El Ghazala", attributeStyle));
        addEmptyLine(cptt4, 1);
        document.add(cptt4);
        
        Paragraph cptt5 = new Paragraph("");
        cptt5.add(new Phrase("To whom it may concern,", attributeStyle));
        addEmptyLine(cptt5, 2);
        document.add(cptt5);
        
        Paragraph cptt6 = new Paragraph("");
        cptt6.add(new Phrase("Hereby we confirm that ", attributeStyle));
        cptt6.add(new Phrase(studentFullName, subTitleStyleGray));
        cptt6.add(new Phrase(" born on ", attributeStyle));
        cptt6.add(new Phrase(studentBirthDay, subTitleStyleGray));
        cptt6.add(new Phrase(", he is a full time student enrolled at the University ESPRIT.", attributeStyle));
        cptt6.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt6, 1);
        document.add(cptt6);
        
        Paragraph cptt7 = new Paragraph("");
        cptt7.add(new Phrase("According to the standard of our engineering-program, ", attributeStyle));
        cptt7.add(new Phrase(studentFullName, subTitleStyleGray));
        cptt7.add(new Phrase(" is required to do an internship of 6 months in order to finish his/her studies.", attributeStyle));
        cptt7.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt7, 1);
        document.add(cptt7);
        
        Paragraph cptt8 = new Paragraph("");
        cptt8.add(new Phrase("The standard requires the internship to have a duration of 6 months.", attributeStyle));
        document.add(cptt8);
        
        Paragraph cptt9 = new Paragraph("");
        cptt9.add(new Phrase(studentFullName, subTitleStyleGray));
        cptt9.add(new Phrase(" has not completed the mandatory internship yet.", attributeStyle));
        addEmptyLine(cptt9, 1);
        document.add(cptt9);
        
        Paragraph cptt10 = new Paragraph("");
        cptt10.add(new Phrase("Please feel free to contact me at (+216) 70 685 685 in Tunis, should further information be required.", attributeStyle));
        addEmptyLine(cptt10, 8);
        document.add(cptt10);
        
        Paragraph cptt14 = new Paragraph("");
        cptt14.add(new Phrase("Majdi GHARBI\r\n" + " " + " " + " " + "Internships Director" + " ", noteStyle12));
        cptt14.setAlignment(Element.ALIGN_RIGHT);
        document.add(cptt14);
        
        document.add(addDefaultEmptyLine(1));
        
        String signDrPath = "C:\\ESP-DOCS\\Logos\\conventionSign.png";
        Image signDrIcon = Image.getInstance(signDrPath);
        signDrIcon.scaleAbsolute(150f, 70f);
        PdfPTable signTBL = new PdfPTable(2);
        signTBL.setWidthPercentage(100);
        signTBL.setWidths(new int[]{820, 290});
	  	PdfPCell cellST_11 = new PdfPCell(new Phrase(""));
	    PdfPCell cellST_12 = new PdfPCell(signDrIcon);
	    cellST_11.setBorder(Rectangle.NO_BORDER);cellST_12.setBorder(Rectangle.NO_BORDER);
	    signTBL.addCell(cellST_11);signTBL.addCell(cellST_12);
	    document.add(signTBL);
    }

    private void addMetaData(Document document)
    {
        document.addTitle("Mandatory Internship");
        document.addSubject("PFE");
        document.addKeywords("Stage, PFE, Mandatory Internship");
        document.addAuthor("Saria ESSID");
        document.addCreator("Saria ESSID");
    }

    private void addTitlePage(Document document) throws DocumentException, MalformedURLException, IOException
    {
    	
    	String espIconPath = "C:\\ESP-DOCS\\Logos\\espritHonoris.jpg";
        Image imageEspIcon = Image.getInstance(espIconPath);
        imageEspIcon.scaleAbsolute(110f, 45f);
        
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{800, 80, 130});
		
        PdfPCell cell1 = new PdfPCell(new Phrase(""));
        PdfPCell cell3 = new PdfPCell(imageEspIcon);
        PdfPCell cell4 = new PdfPCell(new Phrase(""));
        
        cell1.setBorder(Rectangle.NO_BORDER);
        cell3.setBorder(Rectangle.NO_BORDER);
        cell4.setBorder(Rectangle.NO_BORDER);

        table.addCell(cell1);
        table.addCell(cell3);
        table.addCell(cell4);
        
        document.add(table);
        
    }

    private Paragraph addDefaultEmptyLine(int number)
    {
    	Paragraph bl3 = new Paragraph("");
        for (int i = 0; i < number; i++)
        {
        	bl3.add(new Paragraph(" "));
        }
        return bl3;
    }
    
    private void addEmptyLine(Paragraph paragraph, int number)
    {
        for (int i = 0; i < number; i++)
        {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    
}
