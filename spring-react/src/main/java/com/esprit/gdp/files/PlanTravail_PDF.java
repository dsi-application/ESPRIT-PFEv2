package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esprit.gdp.dto.EntrepriseSupervisorDto;
import com.esprit.gdp.dto.FichePFEHistoryDto;
import com.esprit.gdp.dto.FunctionnalityDto;
import com.esprit.gdp.dto.ProblematicDto;
import com.esprit.gdp.dto.TechnologyDto;
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


public class PlanTravail_PDF
{
	
	BaseColor myRedColor = new BaseColor(204, 0, 0);
	BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    BaseColor myTableGreyColor4 = new BaseColor(242, 242, 242);
    BaseColor myGreyColor = new BaseColor(89, 89, 89);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, myRedColor);
    Font subTitleStyleRedBold = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myRedColor);
    Font subTitleStyleRedNormal = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, myRedColor);
    Font subTitleStyleGray = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.GRAY);
    Font subTitleBoldStyleGray = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, myGreyColor);
    Font subTitleNormalStyleGray = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    
    
    public PlanTravail_PDF(String DSFile, String studentFN, FichePFEHistoryDto fichePFEHistoryDto)
    {
        try
        {
        	
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(DSFile));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent( document, studentFN, fichePFEHistoryDto);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, String studentFN, FichePFEHistoryDto fichePFEHistoryDto) throws DocumentException, MalformedURLException, IOException
    {

    	// English
    	DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
		
    	document.add(addDefaultEmptyLine(1));
    	
        Paragraph cptt2 = new Paragraph("Plan Travail", titleStyle);
    	cptt2.setAlignment(Element.ALIGN_CENTER);
    	addEmptyLine(cptt2, 4);
        document.add(cptt2);
        
        Paragraph cptt4 = new Paragraph("");
        cptt4.add(new Phrase("Titre Projet :\r\n", subTitleStyleRedBold));
        cptt4.add(new Phrase(fichePFEHistoryDto.getTitreProjet(), attributeStyle));
        addEmptyLine(cptt4, 1);
        document.add(cptt4);
        
        Paragraph cptt5 = new Paragraph("");
        cptt5.add(new Phrase("Description :\r\n", subTitleStyleRedBold));
        cptt5.add(new Phrase(fichePFEHistoryDto.getDescriptionProjet(), attributeStyle));
        cptt5.setAlignment(PdfPCell.ALIGN_JUSTIFIED);
        addEmptyLine(cptt5, 1);
        document.add(cptt5);
        
        Paragraph cptt10 = new Paragraph("");
        cptt10.add(new Phrase("Problématique(s) :\r\n", subTitleStyleRedBold));
        document.add(cptt10);
        
        for(ProblematicDto pd : fichePFEHistoryDto.getProblematics())
        {
        	PdfPTable contentPart455 = new PdfPTable(2);
            contentPart455.setWidthPercentage(100);
            contentPart455.setWidths(new int[]{15, 900});
        	PdfPCell cp1cellq_11 = new PdfPCell(new Phrase("-  ", attributeStyle));
        	cp1cellq_11.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        	cp1cellq_11.setLeading(5f, 1f);
            PdfPCell cp1cellq_12 = new PdfPCell(new Phrase(pd.getName(), attributeStyle));
            cp1cellq_12.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
            cp1cellq_12.setLeading(5f, 1f);
            cp1cellq_11.setBorder(Rectangle.NO_BORDER);cp1cellq_12.setBorder(Rectangle.NO_BORDER);        
            contentPart455.addCell(cp1cellq_11);contentPart455.addCell(cp1cellq_12);       
            document.add(contentPart455);
        }
        
        Paragraph cptt12 = new Paragraph("");
        addEmptyLine(cptt12, 1);
        cptt12.add(new Phrase("Fonctionnalité(s) :\r\n", subTitleStyleRedBold));
        document.add(cptt12);
        
        for(FunctionnalityDto fd : fichePFEHistoryDto.getFunctionnalities())
        {
        	PdfPTable contentPart455 = new PdfPTable(2);
            contentPart455.setWidthPercentage(100);
            contentPart455.setWidths(new int[]{15, 900});
        	PdfPCell cp1cellq_11 = new PdfPCell(new Phrase("-  "));
        	cp1cellq_11.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        	cp1cellq_11.setLeading(5f, 1f);
        	
            PdfPCell cp1cellq_12 = new PdfPCell(new Phrase(fd.getName() + "\r\n", attributeStyle));
            cp1cellq_12.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
            cp1cellq_12.setLeading(5f, 1f);
            
            PdfPCell cp1cellq_21 = new PdfPCell(new Phrase("    "));
        	cp1cellq_21.setLeading(5f, 1f);
        	
        	PdfPCell cp1cellq_22 = new PdfPCell(new Phrase(fd.getDescription() + "\r\n", subTitleNormalStyleGray));
            cp1cellq_22.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
            cp1cellq_22.setLeading(5f, 1f);
            
            cp1cellq_11.setBorder(Rectangle.NO_BORDER);cp1cellq_12.setBorder(Rectangle.NO_BORDER);
            
            contentPart455.addCell(cp1cellq_11);contentPart455.addCell(cp1cellq_12);

            if(fd.getDescription() != null)
            {
                if(!fd.getDescription().equalsIgnoreCase("null"))
                {
                    cp1cellq_21.setBorder(Rectangle.NO_BORDER);cp1cellq_22.setBorder(Rectangle.NO_BORDER);
                    contentPart455.addCell(cp1cellq_21);contentPart455.addCell(cp1cellq_22);
                }
            }
            
            document.add(contentPart455);
            
        }
        
        Paragraph cptt11 = new Paragraph("");
        addEmptyLine(cptt11, 1);
        cptt11.add(new Phrase("Technologie(s) :", subTitleStyleRedBold));

        for(TechnologyDto td : fichePFEHistoryDto.getTechnologies())
        {
        	cptt11.add(new Phrase("\r\n-  " + td.getName(), attributeStyle));
        }
        addEmptyLine(cptt11, 1);
        document.add(cptt11);
        
        Paragraph cptt131 = new Paragraph("");
        cptt131.add(new Phrase("Entreprise & Encarement :\r\n", subTitleStyleRedBold));
        cptt131.add(new Phrase("Entreprise Accueil :\r\n", subTitleStyleRedNormal));
        document.add(cptt131);
        
        /*********************************************************************************************************/
        PdfPTable contentPart1 = new PdfPTable(3);
        contentPart1.setWidthPercentage(100);
        contentPart1.setWidths(new int[]{1, 170, 770});
        float fixedLeading = 2.75f;
        float multipliedLeading = 1f;
		
        PdfPCell cp1cellz_11 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_12 = new PdfPCell(new Phrase("Label :", subTitleStyleGray));
        cp1cellz_12.setLeading(fixedLeading, multipliedLeading);  //(Fixed Leading, Multiplied Leading)
        PdfPCell cp1cellz_13 = new PdfPCell(new Phrase(fichePFEHistoryDto.getCompanyDto().getDesignation(), attributeStyle));
        cp1cellz_13.setLeading(fixedLeading, multipliedLeading);
        
        PdfPCell cp1cellz_21 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_22 = new PdfPCell(new Phrase("Siége Social :", subTitleStyleGray));
        cp1cellz_22.setLeading(fixedLeading, multipliedLeading);
        PdfPCell cp1cellz_23 = new PdfPCell(new Phrase(fichePFEHistoryDto.getCompanyDto().getSiegeSocial(), attributeStyle));
        cp1cellz_23.setLeading(fixedLeading, multipliedLeading);
        
        PdfPCell cp1cellz_31 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_32 = new PdfPCell(new Phrase("Secteur Activité :", subTitleStyleGray));
        cp1cellz_32.setLeading(fixedLeading, multipliedLeading);
        PdfPCell cp1cellz_33 = new PdfPCell(new Phrase(fichePFEHistoryDto.getCompanyDto().getLibelleSecteur(), attributeStyle));
        cp1cellz_33.setLeading(fixedLeading, multipliedLeading);
        
        PdfPCell cp1cellz_41 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_42 = new PdfPCell(new Phrase("Adresse :", subTitleStyleGray));
        cp1cellz_42.setLeading(fixedLeading, multipliedLeading);
        String fullAddress = fichePFEHistoryDto.getCompanyDto().getAddress() + ", " + fichePFEHistoryDto.getCompanyDto().getNomPays();
        PdfPCell cp1cellz_43 = new PdfPCell(new Phrase(fullAddress, attributeStyle));
        cp1cellz_43.setLeading(fixedLeading, multipliedLeading);
        
        PdfPCell cp1cellz_51 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_52 = new PdfPCell(new Phrase("E-Mail :", subTitleStyleGray));
        cp1cellz_52.setLeading(fixedLeading, multipliedLeading);
        PdfPCell cp1cellz_53 = new PdfPCell(new Phrase(fichePFEHistoryDto.getCompanyDto().getMail(), attributeStyle));
        cp1cellz_53.setLeading(fixedLeading, multipliedLeading);
        
        PdfPCell cp1cellz_61 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_62 = new PdfPCell(new Phrase("Téléphone :", subTitleStyleGray));
        cp1cellz_62.setLeading(fixedLeading, multipliedLeading);
        String phoneNbr = fichePFEHistoryDto.getCompanyDto().getPhone();
        String abbrNbrPh = "--";
        if(!phoneNbr.equalsIgnoreCase("--------"))
        {
        	abbrNbrPh = phoneNbr;
        }
        PdfPCell cp1cellz_63 = new PdfPCell(new Phrase(abbrNbrPh, attributeStyle));
        cp1cellz_63.setLeading(fixedLeading, multipliedLeading);
        
        PdfPCell cp1cellz_71 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_72 = new PdfPCell(new Phrase("Fax :", subTitleStyleGray));
        cp1cellz_72.setLeading(fixedLeading, multipliedLeading);
        String faxNbr = fichePFEHistoryDto.getCompanyDto().getFax();
        String abbrNbrFx = "--";
        if(!faxNbr.equalsIgnoreCase("--------"))
        {
        	abbrNbrFx = faxNbr;
        }
        PdfPCell cp1cellz_73 = new PdfPCell(new Phrase(abbrNbrFx, attributeStyle));
        cp1cellz_73.setLeading(fixedLeading, multipliedLeading);
        
        cp1cellz_11.setBorder(Rectangle.NO_BORDER);cp1cellz_12.setBorder(Rectangle.NO_BORDER);cp1cellz_13.setBorder(Rectangle.NO_BORDER);
        cp1cellz_21.setBorder(Rectangle.NO_BORDER);cp1cellz_22.setBorder(Rectangle.NO_BORDER);cp1cellz_23.setBorder(Rectangle.NO_BORDER);
        cp1cellz_31.setBorder(Rectangle.NO_BORDER);cp1cellz_32.setBorder(Rectangle.NO_BORDER);cp1cellz_33.setBorder(Rectangle.NO_BORDER);
        cp1cellz_41.setBorder(Rectangle.NO_BORDER);cp1cellz_42.setBorder(Rectangle.NO_BORDER);cp1cellz_43.setBorder(Rectangle.NO_BORDER);
        cp1cellz_51.setBorder(Rectangle.NO_BORDER);cp1cellz_52.setBorder(Rectangle.NO_BORDER);cp1cellz_53.setBorder(Rectangle.NO_BORDER);
        cp1cellz_61.setBorder(Rectangle.NO_BORDER);cp1cellz_62.setBorder(Rectangle.NO_BORDER);cp1cellz_63.setBorder(Rectangle.NO_BORDER);
        cp1cellz_71.setBorder(Rectangle.NO_BORDER);cp1cellz_72.setBorder(Rectangle.NO_BORDER);cp1cellz_73.setBorder(Rectangle.NO_BORDER);
        
        contentPart1.addCell(cp1cellz_11);contentPart1.addCell(cp1cellz_12);contentPart1.addCell(cp1cellz_13);
        contentPart1.addCell(cp1cellz_21);contentPart1.addCell(cp1cellz_22);contentPart1.addCell(cp1cellz_23);
        contentPart1.addCell(cp1cellz_31);contentPart1.addCell(cp1cellz_32);contentPart1.addCell(cp1cellz_33);
        contentPart1.addCell(cp1cellz_41);contentPart1.addCell(cp1cellz_42);contentPart1.addCell(cp1cellz_43);
        contentPart1.addCell(cp1cellz_51);contentPart1.addCell(cp1cellz_52);contentPart1.addCell(cp1cellz_53);
        contentPart1.addCell(cp1cellz_61);contentPart1.addCell(cp1cellz_62);contentPart1.addCell(cp1cellz_63);
        contentPart1.addCell(cp1cellz_71);contentPart1.addCell(cp1cellz_72);contentPart1.addCell(cp1cellz_73);
        
        document.add(contentPart1);
        
        /*********************************************************************************************************/
        Paragraph cptt132 = new Paragraph("");
        addEmptyLine(cptt132, 1);
        cptt132.add(new Phrase("Encadrant Entreprise :", subTitleStyleRedNormal));
        document.add(cptt132);
        
        Paragraph cptsup = new Paragraph("");
        
        PdfPTable contentPart2 = new PdfPTable(3);
        contentPart2.setWidthPercentage(100);
        contentPart2.setWidths(new int[]{12, 120, 770});
		
        for(EntrepriseSupervisorDto cs : fichePFEHistoryDto.getCompanySupervisors())
        {
        	float fixedLeading1 = 2.75f;
            float multipliedLeading1 = 1f;
            
            insertCell(contentPart2, "- " + cs.getFirstName() + " " + cs.getLastName(), Element.ALIGN_LEFT, 3, subTitleBoldStyleGray, fixedLeading1, multipliedLeading1);
            // insertCell(contentPart2, " ", Element.ALIGN_LEFT, 3, titleStyle, fixedLeading1, multipliedLeading1);
            
            PdfPCell cp1celle_21 = new PdfPCell(new Phrase(""));
            PdfPCell cp1celle_22 = new PdfPCell(new Phrase("E-Mail :", subTitleStyleGray));
            cp1celle_22.setLeading(fixedLeading1, multipliedLeading1);
            PdfPCell cp1celle_23 = new PdfPCell(new Phrase(cs.getEmail(), attributeStyle));
            cp1celle_23.setLeading(fixedLeading1, multipliedLeading1);
            
            PdfPCell cp1celle_31 = new PdfPCell(new Phrase(""));
            PdfPCell cp1celle_32 = new PdfPCell(new Phrase("Téléphone :", subTitleStyleGray));
            cp1celle_32.setLeading(fixedLeading1, multipliedLeading1);
            String supPhoneNbr = cs.getNumTelephone();
            String abbrNbrSupPh = "--";
            if(!supPhoneNbr.equalsIgnoreCase("--------"))
            {
            	abbrNbrSupPh = supPhoneNbr;
            }
            PdfPCell cp1celle_33 = new PdfPCell(new Phrase(abbrNbrSupPh, attributeStyle));
            cp1celle_33.setLeading(fixedLeading1, multipliedLeading1);
            
            cp1celle_21.setBorder(Rectangle.NO_BORDER);cp1celle_22.setBorder(Rectangle.NO_BORDER);cp1celle_23.setBorder(Rectangle.NO_BORDER);
            cp1celle_31.setBorder(Rectangle.NO_BORDER);cp1celle_32.setBorder(Rectangle.NO_BORDER);cp1celle_33.setBorder(Rectangle.NO_BORDER);
            
            contentPart2.addCell(cp1celle_21);contentPart2.addCell(cp1celle_22);contentPart2.addCell(cp1celle_23);
            contentPart2.addCell(cp1celle_31);contentPart2.addCell(cp1celle_32);contentPart2.addCell(cp1celle_33);
            
        }
        
        document.add(cptsup);
        document.add(contentPart2);
        Paragraph cpttEL = new Paragraph("");
        addEmptyLine(cpttEL, 5);
        document.add(cpttEL);
        
        PdfPTable contentSignStu = new PdfPTable(2);
        contentSignStu.setWidthPercentage(100);
        contentSignStu.setWidths(new int[]{650, 250});
        
    	PdfPCell cp1cellu_11 = new PdfPCell(new Phrase(""));
    	cp1cellu_11.setLeading(5f, 1f);
        PdfPCell cp1cellu_12 = new PdfPCell(new Phrase(studentFN, attributeStyle));
        cp1cellu_12.setLeading(5f, 1f);
        cp1cellu_12.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp1cellu_21 = new PdfPCell(new Phrase(""));
    	cp1cellu_21.setLeading(5f, 1f);
        PdfPCell cp1cellu_22 = new PdfPCell(new Phrase("Tunis, " + formattedDate.format(new Date()) + " " + " " + " " + " " + " ", attributeStyle));
        cp1cellu_22.setLeading(5f, 1f);
        cp1cellu_22.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cp1cellu_11.setBorder(Rectangle.NO_BORDER);cp1cellu_12.setBorder(Rectangle.NO_BORDER);
        cp1cellu_21.setBorder(Rectangle.NO_BORDER);cp1cellu_22.setBorder(Rectangle.NO_BORDER);
        contentSignStu.addCell(cp1cellu_11);contentSignStu.addCell(cp1cellu_12);
        contentSignStu.addCell(cp1cellu_21);contentSignStu.addCell(cp1cellu_22);
        document.add(contentSignStu);
        
    }

    private void addMetaData(Document document)
    {
        document.addTitle("Plan Travail");
        document.addSubject("PFE");
        document.addKeywords("Stage, PFE, Plan Travail");
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
    
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font, float fixedLeading1, float multipliedLeading1){
		  
		  //create a new cell with the specified Text and Font
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  //set the cell alignment
		  cell.setHorizontalAlignment(align);
		  //set the cell column span in case you want to merge two or more cells
		  cell.setColspan(colspan);
		  cell.setBorder(Rectangle.NO_BORDER);
		  cell.setLeading(fixedLeading1, multipliedLeading1);
		  //in case there is no text and you wan to create an empty row
//		  if(text.trim().equalsIgnoreCase("")){
//		   cell.setMinimumHeight(15f);
//		  }
		  //add the call to the table
		  table.addCell(cell);
		  
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
