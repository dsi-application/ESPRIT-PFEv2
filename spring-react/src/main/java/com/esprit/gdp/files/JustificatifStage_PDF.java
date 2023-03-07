package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esprit.gdp.dto.StudentJustificatifStageDto;
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


public class JustificatifStage_PDF
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
    
    
    public JustificatifStage_PDF(String DSFile, StudentJustificatifStageDto studentJustifStgDto, String companyName, String dateDebutStage, String dateFinStage)
    {
        try
        {
        	
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(DSFile));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent( document, studentJustifStgDto, companyName, dateDebutStage, dateFinStage);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, StudentJustificatifStageDto studentJustifStgDto, String companyName, String dateDebutStage, String dateFinStage) throws DocumentException, MalformedURLException, IOException
    {
    	// English
    	DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
		
    	document.add(addDefaultEmptyLine(1));
    	
    	Paragraph cpttl = new Paragraph("Tunis, " + formattedDate.format(new Date()) + " " + " " + " " + " " + " ", attributeStyle);
    	cpttl.setAlignment(Element.ALIGN_RIGHT);
    	addEmptyLine(cpttl, 1);
        document.add(cpttl);
        
        Paragraph cptt2 = new Paragraph("Justificatif de Stage Obligatoire", titleStyle);
    	cptt2.setAlignment(Element.ALIGN_CENTER);
    	addEmptyLine(cptt2, 3);
        document.add(cptt2);
        
        Paragraph cptt4 = new Paragraph("");
        cptt4.add(new Phrase("ESPRIT, ", subTitleStyle));
        cptt4.add(new Phrase("Ecole Supérieure Privée d'Ingénierie et de Technolopgies Z.I Chotrana 2, BP 106, Pôle Technologique EL Ghazala 2083. ", attributeStyle));
        addEmptyLine(cptt4, 1);
        document.add(cptt4);
        
        Paragraph cptt5 = new Paragraph("");
        cptt5.add(new Phrase("Nous confirmons que l’étudiant(e), ", attributeStyle));
        cptt5.add(new Phrase(studentJustifStgDto.getFullName(), subTitleStyleGray));
        cptt5.add(new Phrase(" né(e) le ", attributeStyle));
        cptt5.add(new Phrase(studentJustifStgDto.getBirthDay(), subTitleStyleGray));
        cptt5.add(new Phrase(" est propriétaire de la Carte d’Identité Nationale CIN/Passeport numéro ", attributeStyle));
        cptt5.add(new Phrase(studentJustifStgDto.getCinPassport(), subTitleStyleGray));
        cptt5.add(new Phrase(".", attributeStyle));
        cptt5.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt5, 1);
        document.add(cptt5);
        
        Paragraph cptt6 = new Paragraph("");
        cptt6.add(new Phrase(studentJustifStgDto.getFullName(), subTitleStyleGray));
        cptt6.add(new Phrase(" est un(e) étudiant(e) inscrit(e) à plein temps à", attributeStyle));
        cptt6.add(new Phrase(" ESPRIT ", subTitleStyleGray));
        cptt6.add(new Phrase("pour l’année universitaire ", attributeStyle));
        cptt6.add(new Phrase("2021/2022", subTitleStyleGray));
        cptt6.add(new Phrase(".", attributeStyle));
        cptt6.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt6, 1);
        document.add(cptt6);
        
        Paragraph cptt7 = new Paragraph("");
        cptt7.add(new Phrase("Conformément aux standards de notre programme de formation, ", attributeStyle));
        cptt7.add(new Phrase(studentJustifStgDto.getFullName(), subTitleStyleGray));
        cptt7.add(new Phrase(" est tenu(e) à faire un stage obligatoire de projet fin d’études pour l’obtention du Diplôme National d’Ingénieur en Informatique au sein de l’entreprise ", attributeStyle));
        cptt7.add(new Phrase(companyName, subTitleStyleGray));
        cptt7.add(new Phrase(" et ce du ", attributeStyle));
        cptt7.add(new Phrase(dateDebutStage, subTitleStyleGray));
        cptt7.add(new Phrase(" au ", attributeStyle));
        cptt7.add(new Phrase(dateFinStage, subTitleStyleGray));
        cptt7.add(new Phrase(".", attributeStyle));
        cptt7.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt7, 1);
        document.add(cptt7);
        
        Paragraph cptt8 = new Paragraph("");
        cptt8.add(new Phrase("Nous exigeons que le stage ait une durée minimale de ", attributeStyle));
        cptt8.add(new Phrase("6 mois", subTitleStyleGray));
        cptt8.add(new Phrase(".\r\n", attributeStyle));
        cptt8.add(new Phrase("Habituellement, nos étudiantes choisissent une entreprise locale, mais un programme de stage international est également reconnu par notre institution.", attributeStyle));
        cptt8.setAlignment(Element.ALIGN_JUSTIFIED);
        addEmptyLine(cptt8, 1);
        document.add(cptt8);
        
        Paragraph cptt9 = new Paragraph("");
        cptt9.add(new Phrase("L’étudiant(e) ", attributeStyle));
        cptt9.add(new Phrase(studentJustifStgDto.getFullName(), subTitleStyleGray));
        cptt9.add(new Phrase(" n'a pas encore réalisé le stage obligatoire.\r\n", attributeStyle));
        cptt9.add(new Phrase("N'hésitez pas à nous contacter sur le +216 70 685 685 pour plus d'informations.", attributeStyle));
        addEmptyLine(cptt9, 7);
        document.add(cptt9);
        
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
        document.addTitle("Justificatif Stage");
        document.addSubject("PFE");
        document.addKeywords("Stage, PFE, Justificatif Stage");
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
