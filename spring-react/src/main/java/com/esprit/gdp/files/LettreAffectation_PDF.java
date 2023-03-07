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


public class LettreAffectation_PDF
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
    
    
    public LettreAffectation_PDF(String DSFile, String studentFullName, String departementLabel, String companyName, String dateDebutStage, String dateFinStage)
    {
        try
        {
        	
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(DSFile));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent( document, studentFullName, departementLabel, companyName, dateDebutStage, dateFinStage);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, String studentFullName, String departementLabel, String companyName, String dateDebutStage, String dateFinStage) throws DocumentException, MalformedURLException, IOException
    {
    	// English
    	DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
		
    	document.add(addDefaultEmptyLine(1));
    	
    	Paragraph cpttl = new Paragraph("Tunis, " + formattedDate.format(new Date()) + " " + " " + " " + " " + " ", attributeStyle);
    	cpttl.setAlignment(Element.ALIGN_RIGHT);
    	addEmptyLine(cpttl, 2);
        document.add(cpttl);
        
        Paragraph cptt2 = new Paragraph("Lettre Affectation", titleStyle);
    	cptt2.setAlignment(Element.ALIGN_CENTER);
    	addEmptyLine(cptt2, 4);
        document.add(cptt2);
        
        Paragraph cptt4 = new Paragraph("");
        cptt4.add(new Phrase("Suite à l’accord de la Société : ", attributeStyle));
        cptt4.add(new Phrase(companyName, subTitleStyleGray));
        addEmptyLine(cptt4, 2);
        document.add(cptt4);
        
        Paragraph cptt10 = new Paragraph("");
        cptt10.add(new Phrase("L’Étudiant(e) : ", attributeStyle));
        cptt10.add(new Phrase(studentFullName, subTitleStyleGray));
        cptt10.setAlignment(Element.ALIGN_CENTER);
        document.add(cptt10);
        
        Paragraph cptt11 = new Paragraph("");
        cptt11.add(new Phrase("Inscrit(e) en : ", attributeStyle));
        cptt11.add(new Phrase("5ème Année ", subTitleStyleGray));
        cptt11.add(new Phrase(departementLabel, subTitleStyleGray));
        cptt11.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(cptt11, 1);
        document.add(cptt11);
        
        
        Paragraph cptt7 = new Paragraph("");
        cptt7.add(new Phrase("est affectée à ", attributeStyle));
        cptt7.add(new Phrase(companyName, subTitleStyleGray));
        cptt7.add(new Phrase(" pour effectuer un stage de fin d’études, PFE, et ce du ", attributeStyle));
        cptt7.add(new Phrase(dateDebutStage, subTitleStyleGray));
        cptt7.add(new Phrase(" au ", attributeStyle));
        cptt7.add(new Phrase(dateFinStage, subTitleStyleGray));
        cptt7.add(new Phrase(".\r\n", attributeStyle));
        cptt7.add(new Phrase("Par ailleurs, il/elle est assuré(e) auprès de la STAR Assurances par le contrat N°600/403 983.", attributeStyle));
        cptt7.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt7, 7);
        document.add(cptt7);
        
        Paragraph cptt14 = new Paragraph("");
        cptt14.add(new Phrase("Majdi GHARBI " + " " + " " + " " + "\r\nDépartement des Stages", noteStyle12));
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
        document.addTitle("Lettre Affectation");
        document.addSubject("PFE");
        document.addKeywords("Stage, PFE, Lettre Affectation");
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
