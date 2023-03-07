package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

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

// FirstPdfClassSansEns
public class PVEtudiant_PDF
{

//	 public String FILE = new Date().getTime() + "emploi.pdf";
	// public static final Font DARK_GRAY_NORMAL = new Font(FontFamily.COURIER, 28, Font.NORMAL, BaseColor.DARK_GRAY);
	
	BaseColor myRedColor = new BaseColor(204, 0, 0);
    BaseColor myGreyColor = new BaseColor(115, 115, 115);
    BaseColor myTableGreyColor = new BaseColor(230, 230, 230);
    
    BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, myRedColor);
    Font subTitleStyle = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9);
    Font attributeResponseStyleGray = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.GRAY);
    
    Font noteStyle11 = new Font(Font.FontFamily.HELVETICA, 11);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    Font noteStyle13 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
    Font noteStyle14 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font noteItalicStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC, BaseColor.GRAY);
    
    
    public PVEtudiant_PDF(String idEt, String nomet, String classe, String titreProjet, String libelleSociete, String expert, String juryPresident, String membre, String pedagogicalEncadrant, String stnDate, String FILE)
	{
		try
		{
			Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document, stnDate);
	    	addContent(document, idEt, nomet, classe, titreProjet, libelleSociete, pedagogicalEncadrant, membre, juryPresident, expert);
	    	document.newPage();
    		
            document.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void addMetaData(Document document)
    {
        document.addTitle("PV Etudiant");
        document.addSubject("Sotenance PFE");
        document.addKeywords("Esprit, PFE, PV");
        document.addAuthor("Saria ESSID");
        document.addCreator("Saria ESSID");
    }

	private void addTitlePage(Document document, String stnDate) throws DocumentException, MalformedURLException, IOException
    {
    	String espIconPath = "C:\\ESP-DOCS\\Logos\\espritHonoris.jpg";
        Image imageEspIcon = Image.getInstance(espIconPath);
        imageEspIcon.scaleAbsolute(110f, 45f);
        
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{240, 500, 80, 80});
		
        PdfPCell cell1 = new PdfPCell(new Phrase(""));
        PdfPCell cell2 = new PdfPCell(new Phrase("\r\n " + "Procès Verbal de Soutenance de PFE", titleStyle));
        PdfPCell cell3 = new PdfPCell(imageEspIcon);
        PdfPCell cell4 = new PdfPCell(new Phrase(""));
        
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell3.setBorder(Rectangle.NO_BORDER);
        cell4.setBorder(Rectangle.NO_BORDER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        
        document.add(table);
        
        Paragraph preface = new Paragraph("Date de soutenance : " + stnDate, subTitleStyle); 
        preface.setAlignment(Element.ALIGN_CENTER);
        
        addEmptyLine(preface, 1);
        
        document.add(preface);
   
    }
    
	private void addContent(Document document, String identifiant, String candidate, String classe, String titreProjet, String libelleSociete, String pedagogicalEncadrant, String membre, String juryPresident, String expert) throws DocumentException
    {
    	// Add Content - Part 1
    	PdfPTable contentPart1 = new PdfPTable(3);
		contentPart1.setWidthPercentage(100);
		contentPart1.setWidths(new int[]{85, 20, 700});
		
        PdfPCell cp1cell1 = new PdfPCell(new Phrase("Identifiant", noteStyle12));cp1cell1.setFixedHeight(18f);
        PdfPCell cp1cell2 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell3 = new PdfPCell(new Phrase(identifiant, attributeResponseStyle));

        PdfPCell cp1cell7 = new PdfPCell(new Phrase("Classe", noteStyle12));cp1cell7.setFixedHeight(18f);
        PdfPCell cp1cell8 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell9 = new PdfPCell(new Phrase(classe, attributeResponseStyle));
        
        PdfPCell cp1cell4 = new PdfPCell(new Phrase("Candidat", noteStyle12));cp1cell4.setFixedHeight(18f);
        PdfPCell cp1cell5 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell6 = new PdfPCell(new Phrase(candidate, attributeResponseStyle));
        
        PdfPCell cp1cell16 = new PdfPCell(new Phrase("Sujet", noteStyle12));cp1cell16.setFixedHeight(18f);
        PdfPCell cp1cell17 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell18 = new PdfPCell(new Phrase(titreProjet, attributeResponseStyle));
        
        PdfPCell cp1cell10 = new PdfPCell(new Phrase("Encadrants", noteStyle12));cp1cell10.setFixedHeight(18f);
        PdfPCell cp1cell11 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell12 = new PdfPCell(new Phrase(pedagogicalEncadrant + " (ESPRIT) - " + libelleSociete + " (Société) ", attributeResponseStyle));
        
        PdfPCell cp1cell13 = new PdfPCell(new Phrase("Expert", noteStyle12));cp1cell13.setFixedHeight(18f);
        PdfPCell cp1cell14 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell15 = new PdfPCell(new Phrase(expert, attributeResponseStyle));
        
        PdfPCell cp1cell19 = new PdfPCell(new Phrase("Membre", noteStyle12));cp1cell13.setFixedHeight(18f);
        PdfPCell cp1cell20 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell21 = new PdfPCell(new Phrase(membre, attributeResponseStyle));
        
        cp1cell1.setBorder(Rectangle.NO_BORDER); cp1cell2.setBorder(Rectangle.NO_BORDER); cp1cell3.setBorder(Rectangle.NO_BORDER);
        cp1cell4.setBorder(Rectangle.NO_BORDER); cp1cell5.setBorder(Rectangle.NO_BORDER); cp1cell6.setBorder(Rectangle.NO_BORDER);
        cp1cell7.setBorder(Rectangle.NO_BORDER); cp1cell8.setBorder(Rectangle.NO_BORDER); cp1cell9.setBorder(Rectangle.NO_BORDER);
        cp1cell10.setBorder(Rectangle.NO_BORDER); cp1cell11.setBorder(Rectangle.NO_BORDER); cp1cell12.setBorder(Rectangle.NO_BORDER);
        cp1cell13.setBorder(Rectangle.NO_BORDER); cp1cell14.setBorder(Rectangle.NO_BORDER); cp1cell15.setBorder(Rectangle.NO_BORDER);
        cp1cell16.setBorder(Rectangle.NO_BORDER); cp1cell17.setBorder(Rectangle.NO_BORDER); cp1cell18.setBorder(Rectangle.NO_BORDER);
        cp1cell19.setBorder(Rectangle.NO_BORDER); cp1cell20.setBorder(Rectangle.NO_BORDER); cp1cell21.setBorder(Rectangle.NO_BORDER);

        contentPart1.addCell(cp1cell1);contentPart1.addCell(cp1cell2);contentPart1.addCell(cp1cell3);
        contentPart1.addCell(cp1cell7);contentPart1.addCell(cp1cell8);contentPart1.addCell(cp1cell9);
        contentPart1.addCell(cp1cell4);contentPart1.addCell(cp1cell5);contentPart1.addCell(cp1cell6);
        contentPart1.addCell(cp1cell16);contentPart1.addCell(cp1cell17);contentPart1.addCell(cp1cell18);
        contentPart1.addCell(cp1cell10);contentPart1.addCell(cp1cell11);contentPart1.addCell(cp1cell12);
        contentPart1.addCell(cp1cell13);contentPart1.addCell(cp1cell14);contentPart1.addCell(cp1cell15);
        contentPart1.addCell(cp1cell19);contentPart1.addCell(cp1cell20);contentPart1.addCell(cp1cell21);

        
        document.add(contentPart1);
        
        
//        // Add BreakLine
//        Paragraph bl1 = new Paragraph(""); addEmptyLine(bl1, 1);
//        document.add(bl1);
        

        // Add Content - Part 2.1
        Paragraph cpttl = new Paragraph("Appréciation du jury", noteStyle13); 
        cpttl.setAlignment(Element.ALIGN_CENTER);
        document.add(cpttl);
        
        // Add Content - Part 2.2
        PdfPTable contentPart2 = new PdfPTable(7);
		contentPart2.setWidthPercentage(100);
		contentPart2.setWidths(new int[]{393, 52, 52, 52, 52, 52, 52});
		// Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        PdfPCell cp2cell1 = new PdfPCell(new Phrase("Critères", noteStyle12)); horizentalAndVerticalCellCenterWithBackground(cp2cell1, myTableGreyColor1); cp2cell1.setFixedHeight(18f); cp2cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cp2cell2 = new PdfPCell(new Phrase("A++(5)", noteStyle12)); cp2cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); cp2cell2.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp2cell21 = new PdfPCell(new Phrase("A(4)", noteStyle12)); cp2cell21.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); cp2cell21.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp2cell3 = new PdfPCell(new Phrase("B(3.25)", noteStyle12)); cp2cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); cp2cell3.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp2cell4 = new PdfPCell(new Phrase("C(2)", noteStyle12)); cp2cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); cp2cell4.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp2cell5 = new PdfPCell(new Phrase("D(1)", noteStyle12)); cp2cell5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); cp2cell5.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp2cell6 = new PdfPCell(new Phrase("Total", noteStyle12)); cp2cell6.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); cp2cell6.setBackgroundColor(myTableGreyColor1);
        cp2cell1.setBorder(Rectangle.BOX); 
        cp2cell2.setBorder(Rectangle.BOX);
        cp2cell21.setBorder(Rectangle.BOX);cp2cell3.setBorder(Rectangle.BOX); cp2cell4.setBorder(Rectangle.BOX); cp2cell5.setBorder(Rectangle.BOX); cp2cell6.setBorder(Rectangle.BOX);

        PdfPCell cp2cell8 = new PdfPCell(new Phrase("Comprendre et intégrer les enjeux et la stratégie de l’entreprise.", attributeStyle)); cp2cell8.setBackgroundColor(myTableGreyColor); cp2cell8.setFixedHeight(18f); cp2cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);cp2cell8.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        PdfPCell cp2cell9 = new PdfPCell(new Phrase(""));PdfPCell cp2cell91 = new PdfPCell(new Phrase(""));PdfPCell cp2cell10 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell11 = new PdfPCell(new Phrase(""));PdfPCell cp2cell12 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell13 = new PdfPCell(new Phrase(""));
        cp2cell9.setBorder(Rectangle.BOX);cp2cell91.setBorder(Rectangle.BOX); cp2cell10.setBorder(Rectangle.BOX); 
        cp2cell11.setBorder(Rectangle.BOX); cp2cell12.setBorder(Rectangle.BOX); cp2cell13.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell15 = new PdfPCell(new Phrase("Analyser et/ou chercher les solutions à un problème de conception, de réalisation, d’amélioration de produit, de systèmes ou de services au sein d’une organisation.", attributeStyle)); cp2cell15.setBackgroundColor(myTableGreyColor); cp2cell15.setFixedHeight(40f); cp2cell15.setVerticalAlignment(Element.ALIGN_MIDDLE); cp2cell15.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        PdfPCell cp2cell16 = new PdfPCell(new Phrase(""));PdfPCell cp2cell161 = new PdfPCell(new Phrase(""));PdfPCell cp2cell17 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell18 = new PdfPCell(new Phrase(""));PdfPCell cp2cell19 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell20 = new PdfPCell(new Phrase(""));
        cp2cell16.setBorder(Rectangle.BOX);cp2cell161.setBorder(Rectangle.BOX);cp2cell17.setBorder(Rectangle.BOX); 
        cp2cell18.setBorder(Rectangle.BOX);cp2cell19.setBorder(Rectangle.BOX); cp2cell20.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell22 = new PdfPCell(new Phrase("Conduire un projet de création, de conception, de réalisation, d’amélioration de produit, de système ou de service.", attributeStyle)); cp2cell22.setBackgroundColor(myTableGreyColor); cp2cell22.setFixedHeight(30f); cp2cell22.setVerticalAlignment(Element.ALIGN_MIDDLE); cp2cell22.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        PdfPCell cp2cell23 = new PdfPCell(new Phrase(""));PdfPCell cp2cell231 = new PdfPCell(new Phrase(""));PdfPCell cp2cell24 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell25 = new PdfPCell(new Phrase(""));PdfPCell cp2cell26 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell27 = new PdfPCell(new Phrase(""));
        cp2cell23.setBorder(Rectangle.BOX);cp2cell231.setBorder(Rectangle.BOX);cp2cell24.setBorder(Rectangle.BOX); 
        cp2cell25.setBorder(Rectangle.BOX);cp2cell26.setBorder(Rectangle.BOX); cp2cell27.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell29 = new PdfPCell(new Phrase("Mettre en œuvre sa maîtrise scientifique ou technique au sein de l’organisation.", attributeStyle)); cp2cell29.setBackgroundColor(myTableGreyColor); cp2cell29.setFixedHeight(30f); cp2cell29.setVerticalAlignment(Element.ALIGN_MIDDLE); cp2cell29.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        PdfPCell cp2cell30 = new PdfPCell(new Phrase(""));PdfPCell cp2cell301 = new PdfPCell(new Phrase(""));PdfPCell cp2cell31 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell32 = new PdfPCell(new Phrase(""));PdfPCell cp2cell33 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell34 = new PdfPCell(new Phrase(""));
        cp2cell30.setBorder(Rectangle.BOX);cp2cell301.setBorder(Rectangle.BOX);cp2cell31.setBorder(Rectangle.BOX); 
        cp2cell32.setBorder(Rectangle.BOX);cp2cell33.setBorder(Rectangle.BOX); cp2cell34.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell36 = new PdfPCell(new Phrase("Organiser sa mission et manager les ressources", attributeStyle)); cp2cell36.setBackgroundColor(myTableGreyColor); cp2cell36.setFixedHeight(18f); cp2cell36.setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cp2cell37 = new PdfPCell(new Phrase(""));PdfPCell cp2cell371 = new PdfPCell(new Phrase(""));PdfPCell cp2cell38 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell39 = new PdfPCell(new Phrase(""));PdfPCell cp2cell40 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell41 = new PdfPCell(new Phrase(""));
        cp2cell37.setBorder(Rectangle.BOX);cp2cell371.setBorder(Rectangle.BOX);cp2cell38.setBorder(Rectangle.BOX); 
        cp2cell39.setBorder(Rectangle.BOX);cp2cell40.setBorder(Rectangle.BOX); cp2cell41.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell43 = new PdfPCell(new Phrase("Qualité et présentation du document", attributeStyle)); cp2cell43.setBackgroundColor(myTableGreyColor); cp2cell43.setFixedHeight(18f); cp2cell43.setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cp2cell44 = new PdfPCell(new Phrase(""));PdfPCell cp2cell441 = new PdfPCell(new Phrase(""));PdfPCell cp2cell45 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell46 = new PdfPCell(new Phrase(""));PdfPCell cp2cell47 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell48 = new PdfPCell(new Phrase(""));
        cp2cell44.setBorder(Rectangle.BOX);cp2cell441.setBorder(Rectangle.BOX);cp2cell45.setBorder(Rectangle.BOX); 
        cp2cell46.setBorder(Rectangle.BOX); cp2cell47.setBorder(Rectangle.BOX); cp2cell48.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell50 = new PdfPCell(new Phrase("Qualité de la présentation orale", attributeStyle)); cp2cell50.setBackgroundColor(myTableGreyColor); cp2cell50.setFixedHeight(18f); cp2cell50.setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cp2cell51 = new PdfPCell(new Phrase(""));PdfPCell cp2cell511 = new PdfPCell(new Phrase(""));PdfPCell cp2cell52 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell53 = new PdfPCell(new Phrase(""));PdfPCell cp2cell54 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell55 = new PdfPCell(new Phrase(""));
        cp2cell51.setBorder(Rectangle.BOX);cp2cell511.setBorder(Rectangle.BOX);cp2cell52.setBorder(Rectangle.BOX); 
        cp2cell53.setBorder(Rectangle.BOX);cp2cell54.setBorder(Rectangle.BOX); cp2cell55.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell57 = new PdfPCell(new Phrase("Qualité de l’argumentation", attributeStyle)); cp2cell57.setBackgroundColor(myTableGreyColor); cp2cell57.setFixedHeight(18f); cp2cell57.setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cp2cell58 = new PdfPCell(new Phrase(""));PdfPCell cp2cell581 = new PdfPCell(new Phrase(""));PdfPCell cp2cell59 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell60 = new PdfPCell(new Phrase(""));PdfPCell cp2cell61 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell62 = new PdfPCell(new Phrase(""));
        cp2cell58.setBorder(Rectangle.BOX);cp2cell581.setBorder(Rectangle.BOX);cp2cell59.setBorder(Rectangle.BOX); 
        cp2cell60.setBorder(Rectangle.BOX);cp2cell61.setBorder(Rectangle.BOX); cp2cell62.setBorder(Rectangle.BOX);
        
        PdfPCell cp2cell64 = new PdfPCell(new Phrase("", attributeStyle)); cp2cell64.setBackgroundColor(myTableGreyColor); cp2cell64.setFixedHeight(15f); cp2cell64.setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cp2cell65 = new PdfPCell(new Phrase(""));cp2cell65.setBackgroundColor(myTableGreyColor);
        PdfPCell cp2cell651 = new PdfPCell(new Phrase(""));cp2cell651.setBackgroundColor(myTableGreyColor);
        PdfPCell cp2cell66 = new PdfPCell(new Phrase(""));cp2cell66.setBackgroundColor(myTableGreyColor); 
        PdfPCell cp2cell67 = new PdfPCell(new Phrase(""));cp2cell67.setBackgroundColor(myTableGreyColor); 
        PdfPCell cp2cell68 = new PdfPCell(new Phrase(""));cp2cell68.setBackgroundColor(myTableGreyColor); 
        PdfPCell cp2cell69 = new PdfPCell(new Phrase(".../40"));cp2cell69.setBackgroundColor(myTableGreyColor); cp2cell69.setHorizontalAlignment(Element.ALIGN_CENTER);cp2cell69.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cp2cell65.setBorder(Rectangle.BOX);cp2cell651.setBorder(Rectangle.BOX);cp2cell66.setBorder(Rectangle.BOX); 
        cp2cell67.setBorder(Rectangle.BOX);cp2cell68.setBorder(Rectangle.BOX); cp2cell69.setBorder(Rectangle.BOX);
        
        contentPart2.addCell(cp2cell1);contentPart2.addCell(cp2cell2);contentPart2.addCell(cp2cell21);contentPart2.addCell(cp2cell3);contentPart2.addCell(cp2cell4);
        contentPart2.addCell(cp2cell5);contentPart2.addCell(cp2cell6);
        
        contentPart2.addCell(cp2cell8);contentPart2.addCell(cp2cell9);contentPart2.addCell(cp2cell91);contentPart2.addCell(cp2cell10);contentPart2.addCell(cp2cell11);
        contentPart2.addCell(cp2cell12);contentPart2.addCell(cp2cell13);
        
        contentPart2.addCell(cp2cell15);contentPart2.addCell(cp2cell16);contentPart2.addCell(cp2cell161);contentPart2.addCell(cp2cell17);contentPart2.addCell(cp2cell18);
        contentPart2.addCell(cp2cell19);contentPart2.addCell(cp2cell20);
        
        contentPart2.addCell(cp2cell22);contentPart2.addCell(cp2cell23);contentPart2.addCell(cp2cell231);contentPart2.addCell(cp2cell24);contentPart2.addCell(cp2cell25);
        contentPart2.addCell(cp2cell26);contentPart2.addCell(cp2cell27);
        
        contentPart2.addCell(cp2cell29);contentPart2.addCell(cp2cell30);contentPart2.addCell(cp2cell301);contentPart2.addCell(cp2cell31);contentPart2.addCell(cp2cell32);
        contentPart2.addCell(cp2cell33);contentPart2.addCell(cp2cell34);
        
        contentPart2.addCell(cp2cell36);contentPart2.addCell(cp2cell37);contentPart2.addCell(cp2cell371);contentPart2.addCell(cp2cell38);contentPart2.addCell(cp2cell39);
        contentPart2.addCell(cp2cell40);contentPart2.addCell(cp2cell41);
        
        contentPart2.addCell(cp2cell43);contentPart2.addCell(cp2cell44);contentPart2.addCell(cp2cell441);contentPart2.addCell(cp2cell45);contentPart2.addCell(cp2cell46);
        contentPart2.addCell(cp2cell47);contentPart2.addCell(cp2cell48);
        
        contentPart2.addCell(cp2cell50);contentPart2.addCell(cp2cell51);contentPart2.addCell(cp2cell511);contentPart2.addCell(cp2cell52);contentPart2.addCell(cp2cell53);
        contentPart2.addCell(cp2cell54);contentPart2.addCell(cp2cell55);
        
        contentPart2.addCell(cp2cell57);contentPart2.addCell(cp2cell58);contentPart2.addCell(cp2cell581);contentPart2.addCell(cp2cell59);contentPart2.addCell(cp2cell60);
        contentPart2.addCell(cp2cell61);contentPart2.addCell(cp2cell62);
        
        contentPart2.addCell(cp2cell64);contentPart2.addCell(cp2cell65);contentPart2.addCell(cp2cell651);contentPart2.addCell(cp2cell66);contentPart2.addCell(cp2cell67);
        contentPart2.addCell(cp2cell68);contentPart2.addCell(cp2cell69);
        
        document.add(contentPart2);
        
        
//        // Add BreakLine
//        document.add(addDefaultEmptyLine(1));
        
        
        // Add Content - Part 3
        Paragraph cp3 = new Paragraph("Note de soutenance : …………………. / 20", noteStyle14); 
        cp3.setAlignment(Element.ALIGN_CENTER);
        document.add(cp3);
        
        Paragraph cp31 = new Paragraph("* Dans ces cas, le président du jury est prié d'établir un rapport explicatif. \r\n Il est prié de même de statuer, éventuellement, sur la non validité du projet présenté.", attributeResponseStyleGray); 
        cp31.setAlignment(Element.ALIGN_CENTER);
        document.add(cp31);
        
        // Add BreakLine
        //document.add(addDefaultEmptyLine(1));
        
        
        // Add Content - Part 5    ⅗   ⅖ 
        Paragraph cp5 = new Paragraph("Note Finale =  3/5 NS + 2/5 NFE*", noteStyle12); 
        cp5.setAlignment(Element.ALIGN_CENTER);
        document.add(cp5);

        
//        // Add BreakLine
//        document.add(addDefaultEmptyLine(1));
        
        
        // Add Content - Part 4
        //Paragraph cp4 = new Paragraph("*NFE=Moyenne(Evaluation Encadrant Entreprise + Evaluation Encadrant Académique + Evaluation Expert).", noteItalicStyle); 
        Paragraph cp4 = new Paragraph("*NFE = Note Finale Encadrement", noteItalicStyle); 
        cp4.setAlignment(Element.ALIGN_CENTER);
        document.add(cp4);
        
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        
        // Add Content - Part 5
        PdfPTable finalNoteTBL = new PdfPTable(3);
		finalNoteTBL.setWidthPercentage(100);    // table.setTotalWidth(1000);
		finalNoteTBL.setWidths(new int[]{215, 350, 215});
		
        PdfPCell cp6cell1 = new PdfPCell(new Phrase(""));
        PdfPCell cp6cell2 = new PdfPCell(new Phrase("Note Finale : …………………. / 20", noteStyle14));
        PdfPCell cp6cell3 = new PdfPCell(new Phrase(""));
        cp6cell2.setBackgroundColor(myTableGreyColor); 
        
        cp6cell1.setBorder(Rectangle.NO_BORDER);
        cp6cell2.setBorder(Rectangle.NO_BORDER); cp6cell2.setFixedHeight(27f); horizentalAndVerticalCellCenter(cp6cell2);
        cp6cell3.setBorder(Rectangle.NO_BORDER);

        finalNoteTBL.addCell(cp6cell1);
        finalNoteTBL.addCell(cp6cell2);
        finalNoteTBL.addCell(cp6cell3);
        document.add(finalNoteTBL);
        
        
        // Add BreakLine
        // document.add(addDefaultEmptyLine(1));
        
        
        // Add Content - Part 6
        Paragraph cp6 = new Paragraph("Observations:..........................................................................................................................................." + 
        		".............................................................................................................................................................................." + 
        		".................................................................................................................................................................................", noteStyle11); 
        cp6.setAlignment(Element.ALIGN_LEFT);
        document.add(cp6);
        
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        
        // Add Content - Part 7
        Paragraph cp7 = new Paragraph("Signatures\r\n\r\n", noteStyle13); 
        cp7.setAlignment(Element.ALIGN_CENTER);
        document.add(cp7);
        
        
        // Add BreakLine
        //document.add(addDefaultEmptyLine(1));
        
        
        // Add Content - Part 8
        PdfPTable signatureTBL = new PdfPTable(7);
        signatureTBL.setWidthPercentage(100);    // table.setTotalWidth(1000);
        signatureTBL.setWidths(new int[]{500, 20, 500, 20, 500, 15, 500});
		
        PdfPCell cp8cell1 = new PdfPCell(new Phrase("Président", noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cp8cell1, 18f);cp8cell1.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp8cell1a = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell2 = new PdfPCell(new Phrase("Encadrant Pédagogique", noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cp8cell2, 20f);cp8cell2.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp8cell1b = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell22 = new PdfPCell(new Phrase("Encadrant Entreprise", noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cp8cell22, 20f);cp8cell22.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp8cell1c = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell3 = new PdfPCell(new Phrase("Membre", noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cp8cell3, 20f);cp8cell3.setBackgroundColor(myTableGreyColor1);
        
        PdfPCell cp8cell4 = new PdfPCell(new Phrase(juryPresident, attributeResponseStyle)); 
        cp8cell4.setBackgroundColor(myTableGreyColor2); horizentalAndVerticalAndHeightCellCenter(cp8cell4, 16f);
        PdfPCell cp8cellaa = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell5 = new PdfPCell(new Phrase(pedagogicalEncadrant, attributeResponseStyle)); 
        cp8cell5.setBackgroundColor(myTableGreyColor2); horizentalAndVerticalAndHeightCellCenter(cp8cell5, 16f);
        PdfPCell cp8cellbb = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell52 = new PdfPCell(new Phrase(libelleSociete, attributeResponseStyle)); 
        cp8cell52.setBackgroundColor(myTableGreyColor2); horizentalAndVerticalAndHeightCellCenter(cp8cell52, 16f);
        PdfPCell cp8cellcc = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell6 = new PdfPCell(new Phrase(expert, attributeResponseStyle)); 
        cp8cell6.setBackgroundColor(myTableGreyColor2); horizentalAndVerticalAndHeightCellCenter(cp8cell6, 16f);
        
        PdfPCell cp8cell7 = new PdfPCell(new Phrase("")); cp8cell7.setFixedHeight(38f);cp8cell7.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp8cellaaa = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell8 = new PdfPCell(new Phrase(""));cp8cell8.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp8cellbbb = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell9 = new PdfPCell(new Phrase(""));cp8cell9.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp8cellccc = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell10 = new PdfPCell(new Phrase(""));cp8cell10.setBackgroundColor(myTableGreyColor3);

        cp8cell1.setBorder(Rectangle.NO_BORDER);cp8cell2.setBorder(Rectangle.NO_BORDER);cp8cell22.setBorder(Rectangle.NO_BORDER);cp8cell3.setBorder(Rectangle.NO_BORDER);
        cp8cell4.setBorder(Rectangle.NO_BORDER);cp8cell5.setBorder(Rectangle.NO_BORDER);cp8cell52.setBorder(Rectangle.NO_BORDER);cp8cell6.setBorder(Rectangle.NO_BORDER);
        cp8cell7.setBorder(Rectangle.NO_BORDER);cp8cell8.setBorder(Rectangle.NO_BORDER);cp8cell9.setBorder(Rectangle.NO_BORDER);cp8cell10.setBorder(Rectangle.NO_BORDER);
        
        cp8cell1a.setBorder(Rectangle.NO_BORDER);cp8cellaa.setBorder(Rectangle.NO_BORDER);cp8cellaaa.setBorder(Rectangle.NO_BORDER);
        cp8cell1b.setBorder(Rectangle.NO_BORDER);cp8cellbb.setBorder(Rectangle.NO_BORDER);cp8cellbbb.setBorder(Rectangle.NO_BORDER);
        cp8cell1c.setBorder(Rectangle.NO_BORDER);cp8cellcc.setBorder(Rectangle.NO_BORDER);cp8cellccc.setBorder(Rectangle.NO_BORDER);
        
        signatureTBL.addCell(cp8cell1);signatureTBL.addCell(cp8cell1a);signatureTBL.addCell(cp8cell2);signatureTBL.addCell(cp8cell1b);signatureTBL.addCell(cp8cell22);signatureTBL.addCell(cp8cell1c);signatureTBL.addCell(cp8cell3);
        signatureTBL.addCell(cp8cell4);signatureTBL.addCell(cp8cellaa);signatureTBL.addCell(cp8cell5);signatureTBL.addCell(cp8cellbb);signatureTBL.addCell(cp8cell52);signatureTBL.addCell(cp8cellcc);signatureTBL.addCell(cp8cell6);
        signatureTBL.addCell(cp8cell7);signatureTBL.addCell(cp8cellaaa);signatureTBL.addCell(cp8cell8);signatureTBL.addCell(cp8cellbbb);signatureTBL.addCell(cp8cell9);signatureTBL.addCell(cp8cellccc);signatureTBL.addCell(cp8cell10);
        
        document.add(signatureTBL);
        
    }

	private void addEmptyLine(Paragraph paragraph, int number)
    {
        for (int i = 0; i < number; i++)
        {
            paragraph.add(new Paragraph(" "));
        }
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

	private void horizentalAndVerticalCellCenter(PdfPCell cell)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    }
    
	private void horizentalAndVerticalAndHeightCellCenter(PdfPCell cell, float fixedHeight)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setFixedHeight(fixedHeight);
    }
    
	private void horizentalAndVerticalCellCenterWithBackground(PdfPCell cell, BaseColor color)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setBackgroundColor(color);
    }
    
    
}
