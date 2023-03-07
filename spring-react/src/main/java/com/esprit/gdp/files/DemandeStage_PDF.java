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


public class DemandeStage_PDF
{
	
	BaseColor myRedColor = new BaseColor(204, 0, 0);
	BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    BaseColor myTableGreyColor4 = new BaseColor(242, 242, 242);
    BaseColor myGreyColor = new BaseColor(115, 115, 115);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font subTitleStyle = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font subTitleStyleGray = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GRAY);
    Font nbrConvStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, myRedColor);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    Font noteGreyBoldStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myGreyColor);
    Font noteGreyNormalStyle = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9);
    
    
    public DemandeStage_PDF(String DSFile, String studentFullName, String classe)
    {
        try
        {
        	
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(DSFile));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent(document, studentFullName, classe);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, String studentFullName, String studentClass) throws DocumentException, MalformedURLException, IOException
    {
    	
    	DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
		
    	document.add(addDefaultEmptyLine(1));
    	
    	Paragraph cpttl = new Paragraph("Tunis, le " + formattedDate.format(new Date()) + " " + " ", attributeStyle);
    	cpttl.setAlignment(Element.ALIGN_RIGHT);
    	addEmptyLine(cpttl, 1);
        document.add(cpttl);
        
        Paragraph cptt2 = new Paragraph("Demande Stage", titleStyle);
    	cptt2.setAlignment(Element.ALIGN_CENTER);
    	addEmptyLine(cptt2, 1);
        document.add(cptt2);
        
        // subTitleStyle
        Paragraph cptt3 = new Paragraph("À l’aimable attention de la Direction Générale", subTitleStyle);
    	cptt3.setAlignment(Element.ALIGN_CENTER);
    	addEmptyLine(cptt3, 1);
        document.add(cptt3);
        
        document.add(addDefaultEmptyLine(2));
        
        
        Paragraph cptt4 = new Paragraph("");
        cptt4.add(new Phrase("Objet: ", subTitleStyle));
        cptt4.add(new Phrase("Demande de Stage PFE", attributeStyle)); 
        addEmptyLine(cptt4, 1);
        document.add(cptt4);
        
        Paragraph cptt5 = new Paragraph("");
        cptt5.add(new Phrase("Madame, Monsieur,", attributeStyle));
        addEmptyLine(cptt5, 1);
        document.add(cptt5);
        
        Paragraph cptt6 = new Paragraph("");
        cptt6.add(new Phrase("L’École Supérieure Privée d’Ingénierie et de Technologies, ESPRIT SA, est un " + 
        		"établissement d’enseignement supérieur privé ayant pour objet principal, la formation " + 
        		"d’ingénieurs dans les domaines des technologies de l’information et de la communication.\r\n", attributeStyle));
        cptt6.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt6);
        
        Paragraph cptt8 = new Paragraph("");
        cptt8.add(new Phrase("Notre objectif consiste à former des ingénieurs opérationnels dès leur sortie d’école. C’est dans ce but que nous encourageons nos élèves à mettre en pratique le savoir qu’ils ont acquis au cours de leur cursus universitaire.", attributeStyle));
        cptt8.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt8, 1);
        document.add(cptt8);
        
        Paragraph cptt9 = new Paragraph("");
        cptt9.add(new Phrase("C’est également dans le but de les amener à s’intégrer dans l’environnement de l’entreprise que nous vous demandons de bien vouloir accepter :", attributeStyle));
        cptt9.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt9, 1);
        document.add(cptt9);
        
        Paragraph cptt10 = new Paragraph("");
        cptt10.add(new Phrase("L’Étudiant(e) : ", subTitleStyle));
        cptt10.add(new Phrase(studentFullName, subTitleStyleGray));
        cptt10.setAlignment(Element.ALIGN_CENTER);
        document.add(cptt10);
        
        Paragraph cptt11 = new Paragraph("");
        cptt11.add(new Phrase("Inscrit(e) en : ", subTitleStyle));
        cptt11.add(new Phrase(studentClass, subTitleStyleGray));
        cptt11.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(cptt11, 1);
        document.add(cptt11);
        
        Paragraph cptt12 = new Paragraph("");
        cptt12.add(new Phrase("pour effectuer un stage obligatoire de PFE au sein de votre honorable société.\r\n" + 
        		"Nous restons à votre entière disposition pour tout renseignement complémentaire.", attributeStyle));
        addEmptyLine(cptt12, 1);
        document.add(cptt12);
        
        Paragraph cptt13 = new Paragraph("");
        cptt13.add(new Phrase("En vous remerciant pour votre précieux soutien, nous vous prions d’agréer, Madame, Monsieur, l’expression de nos salutations distinguées", attributeStyle));
        addEmptyLine(cptt13, 3);
        document.add(cptt13);
        
        Paragraph cptt14 = new Paragraph("");
        cptt14.add(new Phrase("Majdi GHARBI " + " " + " " + " " + "\r\nDépartement des Stages\r\n\r\n", noteStyle12));
        cptt14.setAlignment(Element.ALIGN_RIGHT);
        document.add(cptt14);
        
        addEmptyLine(cptt14, 1);
        
        String signDrPath = "C:\\ESP-DOCS\\Logos\\conventionSign.png";
        Image signDrIcon = Image.getInstance(signDrPath);
        signDrIcon.scaleAbsolute(150f, 70f);
        PdfPTable signTBL = new PdfPTable(2);
        signTBL.setWidthPercentage(100);
        signTBL.setWidths(new int[]{815, 285});
	  	PdfPCell cellST_11 = new PdfPCell(new Phrase(""));
	    PdfPCell cellST_12 = new PdfPCell(signDrIcon);
	    cellST_11.setBorder(Rectangle.NO_BORDER);cellST_12.setBorder(Rectangle.NO_BORDER);
	    signTBL.addCell(cellST_11);signTBL.addCell(cellST_12);
	    document.add(signTBL);
    }

    private void addMetaData(Document document)
    {
        document.addTitle("Demande Stage");
        document.addSubject("PFE");
        document.addKeywords("Stage, PFE, Demande Stage");
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
