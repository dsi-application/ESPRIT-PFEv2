package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esprit.gdp.dto.StudentGrilleAcademicEncadrantDto;
import com.esprit.gdp.models.GrilleAcademicEncadrant;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GrilleEncadrantAcademique_PDF
{

	BaseColor myRedColor = new BaseColor(204, 0, 0);
    BaseColor myGreyColor = new BaseColor(115, 115, 115);
    BaseColor myTableGreyColor = new BaseColor(230, 230, 230);
    
    BaseColor borderBorder = new BaseColor(128, 128, 128);
    
    BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    
    // Segoesc.ttf - Segoescb
    Font signatureFont = FontFactory.getFont("C:/Windows/Fonts/Segoesc.ttf", BaseFont.CP1252,true, 9);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, myRedColor);
    Font subTitleStyle = new Font(Font.FontFamily.COURIER, 11, Font.BOLD, myGreyColor);
    Font miniSubTitleStyle = new Font(Font.FontFamily.COURIER, 10, Font.BOLD, myGreyColor);
    Font noteStyle = new Font(Font.FontFamily.COURIER, 11, Font.BOLD, BaseColor.BLACK);
    Font signStyle = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL, BaseColor.BLACK);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9);
    Font attributeResponseStyleSmall = new Font(Font.FontFamily.HELVETICA, 8);
    
    Font noteStyle11 = new Font(Font.FontFamily.HELVETICA, 11);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
    // attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
    Font noteStyle13 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
    Font noteStyle14 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font noteItalicStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC, BaseColor.GRAY);
    
    Font redSubTitle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myRedColor);
    
    
    public GrilleEncadrantAcademique_PDF(String idEt, StudentGrilleAcademicEncadrantDto studentGAE, GrilleAcademicEncadrant grilleAE, String fullNameAE, String FILE)
	{
		try
		{
			
			Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent(document, idEt, studentGAE, grilleAE, fullNameAE);
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
        document.addTitle("Fiche Dépôt PFE");
        document.addSubject("Sotenance PFE");
        document.addKeywords("Esprit, PFE, Fiche Dépôt");
        document.addAuthor("Saria ESSID");
        document.addCreator("Saria ESSID");
    }

	private void addTitlePage(Document document) throws DocumentException, MalformedURLException, IOException
    {
    	String espIconPath = "C:\\ESP-DOCS\\Logos\\espritHonoris.jpg";
        Image imageEspIcon = Image.getInstance(espIconPath);
        imageEspIcon.scaleAbsolute(110f, 45f);
        
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{320, 530, 80, 80});
		
        PdfPCell cell1 = new PdfPCell(new Phrase(""));
        PdfPCell cell2 = new PdfPCell(new Phrase("\r\n " + "Grille Encadrant Académique", titleStyle));
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
        
    }
    
	private void addContent(Document document, String identifiant, StudentGrilleAcademicEncadrantDto studentGAE, GrilleAcademicEncadrant grilleAE, String fullNameAE) throws DocumentException
    {
		
        // Add BreakLine
        document.add(addDefaultEmptyLine(2));
        
    	// Add Content - Part 1
    	PdfPTable contentPart1 = new PdfPTable(3);
		contentPart1.setWidthPercentage(100);
		contentPart1.setWidths(new int[]{200, 20, 700});
		
        PdfPCell cp1cell1 = new PdfPCell(new Phrase("Nom de l'Étudiant", noteStyle12));cp1cell1.setFixedHeight(15f);
        PdfPCell cp1cell2 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell3 = new PdfPCell(new Phrase(studentGAE.getFullName(), attributeResponseStyle));

        PdfPCell cp1cell7 = new PdfPCell(new Phrase("Département", noteStyle12));cp1cell7.setFixedHeight(15f);
        PdfPCell cp1cell8 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell9 = new PdfPCell(new Phrase(studentGAE.getDepartment(), attributeResponseStyle));
        
        PdfPCell cp1cell4 = new PdfPCell(new Phrase("Option", noteStyle12));cp1cell4.setFixedHeight(15f);
        PdfPCell cp1cell5 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell6 = new PdfPCell(new Phrase(studentGAE.getOption().replace("_01", ""), attributeResponseStyle));
        
        PdfPCell cp1cell10 = new PdfPCell(new Phrase("Entreprise d'Accueil", noteStyle12));cp1cell10.setFixedHeight(15f);
        PdfPCell cp1cell11 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell12 = new PdfPCell(new Phrase(studentGAE.getSocieteLibelle(), attributeResponseStyle));
        
        PdfPCell cp1cell13 = new PdfPCell(new Phrase("Nom du ou des Projets", noteStyle12));cp1cell13.setFixedHeight(15f);
        PdfPCell cp1cell14 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell15 = new PdfPCell(new Phrase(studentGAE.getTitreProjet(), attributeResponseStyle));
        
        cp1cell1.setBorder(Rectangle.NO_BORDER); cp1cell2.setBorder(Rectangle.NO_BORDER); cp1cell3.setBorder(Rectangle.NO_BORDER);
        cp1cell4.setBorder(Rectangle.NO_BORDER); cp1cell5.setBorder(Rectangle.NO_BORDER); cp1cell6.setBorder(Rectangle.NO_BORDER);
        cp1cell7.setBorder(Rectangle.NO_BORDER); cp1cell8.setBorder(Rectangle.NO_BORDER); cp1cell9.setBorder(Rectangle.NO_BORDER);
        cp1cell10.setBorder(Rectangle.NO_BORDER); cp1cell11.setBorder(Rectangle.NO_BORDER); cp1cell12.setBorder(Rectangle.NO_BORDER);
        cp1cell13.setBorder(Rectangle.NO_BORDER); cp1cell14.setBorder(Rectangle.NO_BORDER); cp1cell15.setBorder(Rectangle.NO_BORDER);

        contentPart1.addCell(cp1cell1);contentPart1.addCell(cp1cell2);contentPart1.addCell(cp1cell3);
        contentPart1.addCell(cp1cell7);contentPart1.addCell(cp1cell8);contentPart1.addCell(cp1cell9);
        contentPart1.addCell(cp1cell4);contentPart1.addCell(cp1cell5);contentPart1.addCell(cp1cell6);
        contentPart1.addCell(cp1cell10);contentPart1.addCell(cp1cell11);contentPart1.addCell(cp1cell12);
        contentPart1.addCell(cp1cell13);contentPart1.addCell(cp1cell14);contentPart1.addCell(cp1cell15);
        
        document.add(contentPart1);
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        
		
		Paragraph modl1 = new Paragraph("Note RDV Pédagogiques\r\n", subTitleStyle);
        document.add(modl1);
        
        Paragraph modl11 = new Paragraph("- Livrables (7 points)", miniSubTitleStyle);
        modl11.setSpacingAfter(5);
        document.add(modl11);
        
        PdfPTable contentPartTbl1 = new PdfPTable(5);
		contentPartTbl1.setWidthPercentage(100);
		contentPartTbl1.setWidths(new int[]{200, 100, 100, 70, 50});
		PdfPCell tbl1Header1 = new PdfPCell(new Phrase("Livrable", noteStyle12));tbl1Header1.setFixedHeight(20f);horizentalAndVerticalCellCenterWithBackground(tbl1Header1, myTableGreyColor1);
		PdfPCell tbl1Header2 = new PdfPCell(new Phrase("Dûment rempli \r\net rendu à temps", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl1Header2, myTableGreyColor1);
		PdfPCell tbl1Header3 = new PdfPCell(new Phrase("Dûment rempli \r\net rendu en retard", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl1Header3, myTableGreyColor1);
		PdfPCell tbl1Header4 = new PdfPCell(new Phrase("Non rendu", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl1Header4, myTableGreyColor1);
		PdfPCell tbl1Header5 = new PdfPCell(new Phrase("Note", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl1Header5, myTableGreyColor1);

		PdfPCell tbl1Ln11 = new PdfPCell(new Phrase("Planning de Stage", attributeResponseStyle));tbl1Ln11.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl1Ln11, myTableGreyColor2);
		PdfPCell tbl1Ln12 = new PdfPCell(new Phrase("3 pts", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln12);
		PdfPCell tbl1Ln13 = new PdfPCell(new Phrase("2 pts", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln13);
		PdfPCell tbl1Ln14 = new PdfPCell(new Phrase("0 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln14);
		PdfPCell tbl1Ln15 = new PdfPCell(new Phrase(grilleAE.getNotePlanningStage().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl1Ln15, myTableGreyColor3);

		PdfPCell tbl1Ln21 = new PdfPCell(new Phrase("Bilan Périodique Début Stage", attributeResponseStyle));tbl1Ln21.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl1Ln21, myTableGreyColor2);
		PdfPCell tbl1Ln22 = new PdfPCell(new Phrase("1 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln22);
		PdfPCell tbl1Ln23 = new PdfPCell(new Phrase("0.5 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln23);
		PdfPCell tbl1Ln24 = new PdfPCell(new Phrase("0 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln24);
		PdfPCell tbl1Ln25 = new PdfPCell(new Phrase(grilleAE.getNoteBilanPeriodiqueDebutStage().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl1Ln25, myTableGreyColor3);
		
		PdfPCell tbl1Ln31 = new PdfPCell(new Phrase("Bilan Périodique Milieu Stage", attributeResponseStyle));tbl1Ln31.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl1Ln31, myTableGreyColor2);
		PdfPCell tbl1Ln32 = new PdfPCell(new Phrase("1 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln32);
		PdfPCell tbl1Ln33 = new PdfPCell(new Phrase("0.5 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln33);
		PdfPCell tbl1Ln34 = new PdfPCell(new Phrase("0 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln34);
		PdfPCell tbl1Ln35 = new PdfPCell(new Phrase(grilleAE.getNoteBilanPeriodiqueMilieuStage().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl1Ln35, myTableGreyColor3);

		PdfPCell tbl1Ln41 = new PdfPCell(new Phrase("Bilan Périodique Fin Stage", attributeResponseStyle));tbl1Ln41.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl1Ln41, myTableGreyColor2);
		PdfPCell tbl1Ln42 = new PdfPCell(new Phrase("1 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln42);
		PdfPCell tbl1Ln43 = new PdfPCell(new Phrase("0.5 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln43);
		PdfPCell tbl1Ln44 = new PdfPCell(new Phrase("0 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln44);
		PdfPCell tbl1Ln45 = new PdfPCell(new Phrase(grilleAE.getNoteBilanPeriodiqueFinStage().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl1Ln45, myTableGreyColor3);
		
		PdfPCell tbl1Ln51 = new PdfPCell(new Phrase("Journal de Bord", attributeResponseStyle));tbl1Ln51.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl1Ln51, myTableGreyColor2);
		PdfPCell tbl1Ln52 = new PdfPCell(new Phrase("1 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln52);
		PdfPCell tbl1Ln53 = new PdfPCell(new Phrase("0.5 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln53);
		PdfPCell tbl1Ln54 = new PdfPCell(new Phrase("0 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl1Ln54);
		PdfPCell tbl1Ln55 = new PdfPCell(new Phrase(grilleAE.getNoteJournalBord().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl1Ln55, myTableGreyColor3);

		tbl1Header1.setBorderColor(borderBorder);tbl1Header2.setBorderColor(borderBorder);tbl1Header3.setBorderColor(borderBorder);tbl1Header4.setBorderColor(borderBorder);tbl1Header5.setBorderColor(borderBorder);
		tbl1Ln11.setBorderColor(borderBorder);tbl1Ln12.setBorderColor(borderBorder);tbl1Ln13.setBorderColor(borderBorder);tbl1Ln14.setBorderColor(borderBorder);tbl1Ln15.setBorderColor(borderBorder);
		tbl1Ln21.setBorderColor(borderBorder);tbl1Ln22.setBorderColor(borderBorder);tbl1Ln23.setBorderColor(borderBorder);tbl1Ln24.setBorderColor(borderBorder);tbl1Ln25.setBorderColor(borderBorder);
		tbl1Ln31.setBorderColor(borderBorder);tbl1Ln32.setBorderColor(borderBorder);tbl1Ln33.setBorderColor(borderBorder);tbl1Ln34.setBorderColor(borderBorder);tbl1Ln35.setBorderColor(borderBorder);
		tbl1Ln41.setBorderColor(borderBorder);tbl1Ln42.setBorderColor(borderBorder);tbl1Ln43.setBorderColor(borderBorder);tbl1Ln44.setBorderColor(borderBorder);tbl1Ln45.setBorderColor(borderBorder);
		tbl1Ln51.setBorderColor(borderBorder);tbl1Ln52.setBorderColor(borderBorder);tbl1Ln53.setBorderColor(borderBorder);tbl1Ln54.setBorderColor(borderBorder);tbl1Ln55.setBorderColor(borderBorder);

		contentPartTbl1.addCell(tbl1Header1);contentPartTbl1.addCell(tbl1Header2);contentPartTbl1.addCell(tbl1Header3);contentPartTbl1.addCell(tbl1Header4);contentPartTbl1.addCell(tbl1Header5);
        contentPartTbl1.addCell(tbl1Ln11);contentPartTbl1.addCell(tbl1Ln12);contentPartTbl1.addCell(tbl1Ln13);contentPartTbl1.addCell(tbl1Ln14);contentPartTbl1.addCell(tbl1Ln15);
        contentPartTbl1.addCell(tbl1Ln21);contentPartTbl1.addCell(tbl1Ln22);contentPartTbl1.addCell(tbl1Ln23);contentPartTbl1.addCell(tbl1Ln24);contentPartTbl1.addCell(tbl1Ln25);
        contentPartTbl1.addCell(tbl1Ln31);contentPartTbl1.addCell(tbl1Ln32);contentPartTbl1.addCell(tbl1Ln33);contentPartTbl1.addCell(tbl1Ln34);contentPartTbl1.addCell(tbl1Ln35);
        contentPartTbl1.addCell(tbl1Ln41);contentPartTbl1.addCell(tbl1Ln42);contentPartTbl1.addCell(tbl1Ln43);contentPartTbl1.addCell(tbl1Ln44);contentPartTbl1.addCell(tbl1Ln45);
        contentPartTbl1.addCell(tbl1Ln51);contentPartTbl1.addCell(tbl1Ln52);contentPartTbl1.addCell(tbl1Ln53);contentPartTbl1.addCell(tbl1Ln54);contentPartTbl1.addCell(tbl1Ln55);
        
        document.add(contentPartTbl1);
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        PdfPTable contentPartTbl2 = new PdfPTable(4);
        contentPartTbl2.setWidthPercentage(100);
        contentPartTbl2.setWidths(new int[]{200, 135, 135, 50});
		PdfPCell tbl2Header1 = new PdfPCell(new Phrase("Fiche d'Évaluation", noteStyle12));tbl2Header1.setFixedHeight(30f);horizentalAndVerticalCellCenterWithBackground(tbl2Header1, myTableGreyColor1);
		PdfPCell tbl2Header2 = new PdfPCell(new Phrase("Encadrant Satisfait", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl2Header2, myTableGreyColor1);
		PdfPCell tbl2Header3 = new PdfPCell(new Phrase("Encadrant \r\nMoyennement Satisfait", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl2Header3, myTableGreyColor1);
		PdfPCell tbl2Header4 = new PdfPCell(new Phrase("Note", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl2Header4, myTableGreyColor1);

		PdfPCell tbl2Ln11 = new PdfPCell(new Phrase("Fiche d'Évaluation Mi-Parcours", attributeResponseStyle));tbl2Ln11.setFixedHeight(20f);horizentalAndVerticalCellWithBackground(tbl2Ln11, myTableGreyColor2);
		PdfPCell tbl2Ln12 = new PdfPCell(new Phrase("2 pts", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl2Ln12);
		PdfPCell tbl2Ln13 = new PdfPCell(new Phrase("1 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl2Ln13);
		PdfPCell tbl2Ln14 = new PdfPCell(new Phrase(grilleAE.getNoteFicheEvaluationMiParcour().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl2Ln14, myTableGreyColor3);

		PdfPCell tbl2Ln21 = new PdfPCell(new Phrase("Fiche d'Évaluation Finale", attributeResponseStyle));tbl2Ln21.setFixedHeight(25f);horizentalAndVerticalCellWithBackground(tbl2Ln21, myTableGreyColor2);
		PdfPCell tbl2Ln22 = new PdfPCell(new Phrase("2 pts", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl2Ln22);
		PdfPCell tbl2Ln23 = new PdfPCell(new Phrase("1 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl2Ln23);
		PdfPCell tbl2Ln24 = new PdfPCell(new Phrase(grilleAE.getNoteFicheEvaluationFinale().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl2Ln24, myTableGreyColor3);
		
		tbl2Header1.setBorderColor(borderBorder);tbl2Header2.setBorderColor(borderBorder);tbl2Header3.setBorderColor(borderBorder);tbl2Header4.setBorderColor(borderBorder);
		tbl2Ln11.setBorderColor(borderBorder);tbl2Ln12.setBorderColor(borderBorder);tbl2Ln13.setBorderColor(borderBorder);tbl2Ln14.setBorderColor(borderBorder);
		tbl2Ln21.setBorderColor(borderBorder);tbl2Ln22.setBorderColor(borderBorder);tbl2Ln23.setBorderColor(borderBorder);tbl2Ln24.setBorderColor(borderBorder);

		contentPartTbl2.addCell(tbl2Header1);contentPartTbl2.addCell(tbl2Header2);contentPartTbl2.addCell(tbl2Header3);contentPartTbl2.addCell(tbl2Header4);
		contentPartTbl2.addCell(tbl2Ln11);contentPartTbl2.addCell(tbl2Ln12);contentPartTbl2.addCell(tbl2Ln13);contentPartTbl2.addCell(tbl2Ln14);
		contentPartTbl2.addCell(tbl2Ln21);contentPartTbl2.addCell(tbl2Ln22);contentPartTbl2.addCell(tbl2Ln23);contentPartTbl2.addCell(tbl2Ln24);
        
        PdfPTable contentPartTbl3 = new PdfPTable(4);
        contentPartTbl3.setWidthPercentage(100);
        contentPartTbl3.setWidths(new int[]{200, 135, 135, 50});
		PdfPCell tbl3Header1 = new PdfPCell(new Phrase("RDV", noteStyle12));tbl3Header1.setFixedHeight(30f);horizentalAndVerticalCellCenterWithBackground(tbl3Header1, myTableGreyColor1);
		PdfPCell tbl3Header2 = new PdfPCell(new Phrase("Assurée", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl3Header2, myTableGreyColor1);
		PdfPCell tbl3Header3 = new PdfPCell(new Phrase("Non assurée", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl3Header3, myTableGreyColor1);
		PdfPCell tbl3Header4 = new PdfPCell(new Phrase("Note", noteStyle12));horizentalAndVerticalCellCenterWithBackground(tbl3Header4, myTableGreyColor1);

		PdfPCell tbl3Ln11 = new PdfPCell(new Phrase("1ère Restitution", attributeResponseStyle));tbl3Ln11.setFixedHeight(25f);horizentalAndVerticalCellWithBackground(tbl3Ln11, myTableGreyColor2);
		PdfPCell tbl3Ln12 = new PdfPCell(new Phrase("4.5 pts", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl3Ln12);
		PdfPCell tbl3Ln13 = new PdfPCell(new Phrase("0 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl3Ln13);
		PdfPCell tbl3Ln14 = new PdfPCell(new Phrase(grilleAE.getNoteRestitution1().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl3Ln14, myTableGreyColor3);

		PdfPCell tbl3Ln21 = new PdfPCell(new Phrase("2ème Restitution", attributeResponseStyle));tbl3Ln21.setFixedHeight(25f);horizentalAndVerticalCellWithBackground(tbl3Ln21, myTableGreyColor2);
		PdfPCell tbl3Ln22 = new PdfPCell(new Phrase("4.5 pts", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl3Ln22);
		PdfPCell tbl3Ln23 = new PdfPCell(new Phrase("0 pt", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl3Ln23);
		PdfPCell tbl3Ln24 = new PdfPCell(new Phrase(grilleAE.getNoteRestitution2().toString(), attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl3Ln24, myTableGreyColor3);
		
		tbl3Header1.setBorderColor(borderBorder);tbl3Header2.setBorderColor(borderBorder);tbl3Header3.setBorderColor(borderBorder);tbl3Header4.setBorderColor(borderBorder);
		tbl3Ln11.setBorderColor(borderBorder);tbl3Ln12.setBorderColor(borderBorder);tbl3Ln13.setBorderColor(borderBorder);tbl3Ln14.setBorderColor(borderBorder);
		tbl3Ln21.setBorderColor(borderBorder);tbl3Ln22.setBorderColor(borderBorder);tbl3Ln23.setBorderColor(borderBorder);tbl3Ln24.setBorderColor(borderBorder);

		contentPartTbl3.addCell(tbl3Header1);contentPartTbl3.addCell(tbl3Header2);contentPartTbl3.addCell(tbl3Header3);contentPartTbl3.addCell(tbl3Header4);
		contentPartTbl3.addCell(tbl3Ln11);contentPartTbl3.addCell(tbl3Ln12);contentPartTbl3.addCell(tbl3Ln13);contentPartTbl3.addCell(tbl3Ln14);
		contentPartTbl3.addCell(tbl3Ln21);contentPartTbl3.addCell(tbl3Ln22);contentPartTbl3.addCell(tbl3Ln23);contentPartTbl3.addCell(tbl3Ln24);
        
        /**********************************************************************************************************************/
        // PdfPCell cell3 = new PdfPCell(imageEspIcon);
        PdfPTable contentFusion2Tbs = new PdfPTable(3);
        contentFusion2Tbs.setWidthPercentage(100);
        contentFusion2Tbs.setWidths(new int[]{257, 6, 257});
                
        PdfPCell tblHP1 = new PdfPCell(new Phrase("- Fiches d'évaluations (4 points)", miniSubTitleStyle));tblHP1.setFixedHeight(18f);
        PdfPCell tblHP12 = new PdfPCell(new Phrase(""));tblHP12.setFixedHeight(18f);
        PdfPCell tblHP2 = new PdfPCell(new Phrase("- RDV Pédagogiques (9 points)", miniSubTitleStyle));tblHP2.setFixedHeight(18f);
        
		PdfPCell tblP1 = new PdfPCell(contentPartTbl2);tblP1.setFixedHeight(30f);
		PdfPCell tblP12 = new PdfPCell(new Phrase(""));
		PdfPCell tblP2 = new PdfPCell(contentPartTbl3);
		
		tblHP1.setBorder(Rectangle.NO_BORDER);tblHP12.setBorder(Rectangle.NO_BORDER);tblHP2.setBorder(Rectangle.NO_BORDER);
		tblP1.setBorder(Rectangle.NO_BORDER);tblP12.setBorder(Rectangle.NO_BORDER);tblP2.setBorder(Rectangle.NO_BORDER);

		contentFusion2Tbs.addCell(tblHP1);contentFusion2Tbs.addCell(tblHP12);contentFusion2Tbs.addCell(tblHP2);
		contentFusion2Tbs.addCell(tblP1);contentFusion2Tbs.addCell(tblP12);contentFusion2Tbs.addCell(tblP2);
        
        document.add(contentFusion2Tbs);
        
		/**********************************************************************************************************************/
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        Paragraph modl2 = new Paragraph("Note Appréciation Globale de l'Encadrant Académique sur le déroulement du Stage", subTitleStyle);
        modl2.setSpacingAfter(5);
        document.add(modl2);
        
        PdfPTable notePart = new PdfPTable(3);
        notePart.setWidthPercentage(100);
        notePart.setWidths(new int[]{220, 135, 180});
		PdfPCell noteCell1 = new PdfPCell(new Phrase(""));noteCell1.setFixedHeight(20f);
		PdfPCell noteCell2 = new PdfPCell(new Phrase("Note : " + grilleAE.getNoteAcademicEncadrant().toString() + " / 20", noteStyle));horizentalAndVerticalCellCenterWithBackground(noteCell2, myTableGreyColor3);
		PdfPCell noteCell3 = new PdfPCell(new Phrase(""));
		noteCell1.setBorder(Rectangle.NO_BORDER);noteCell2.setBorderColor(borderBorder);noteCell3.setBorder(Rectangle.NO_BORDER);
		notePart.addCell(noteCell1);notePart.addCell(noteCell2);notePart.addCell(noteCell3);
        
        document.add(notePart);
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        PdfPTable contentPartTbl4 = new PdfPTable(2);
        contentPartTbl4.setWidthPercentage(100);
        contentPartTbl4.setWidths(new int[]{370, 150});
		PdfPCell tbl4Header1 = new PdfPCell(new Phrase("Note Encadrant Académique = ( Note RDV Pédagogiques * 80% + Note Appréciation Globale * 20% )", noteStyle12));tbl4Header1.setFixedHeight(20f);horizentalAndVerticalCellCenterWithBackground(tbl4Header1, myTableGreyColor1);
		PdfPCell tbl4Header2 = new PdfPCell(new Phrase(""));
		
		PdfPCell tbl4Ln11 = new PdfPCell(new Phrase("Note RDV Pédagogiques", attributeResponseStyle));tbl4Ln11.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl4Ln11, myTableGreyColor3);
		PdfPCell tbl4Ln12 = new PdfPCell(new Phrase(grilleAE.getNoteRDVPedagogique().toString() + " / 20", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl4Ln12);
		
		PdfPCell tbl4Ln21 = new PdfPCell(new Phrase("Note d'appréciation Globale de l'Encadrant Académique", attributeResponseStyle));tbl4Ln21.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl4Ln21, myTableGreyColor3);
		PdfPCell tbl4Ln22 = new PdfPCell(new Phrase(grilleAE.getNoteAppreciationGlobale().toString() + " / 20", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl4Ln22);

		PdfPCell tbl4Ln31 = new PdfPCell(new Phrase("Note Encadrant Académique", attributeResponseStyle));tbl4Ln31.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl4Ln31, myTableGreyColor2);
		PdfPCell tbl4Ln32 = new PdfPCell(new Phrase(grilleAE.getNoteAcademicEncadrant().toString() + " / 20", attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl4Ln32, myTableGreyColor2);
		
		tbl4Header1.setColspan(2);
		
		tbl4Header1.setBorderColor(borderBorder);
		tbl4Ln11.setBorderColor(borderBorder);tbl4Ln12.setBorderColor(borderBorder);
		tbl4Ln21.setBorderColor(borderBorder);tbl4Ln22.setBorderColor(borderBorder);
		tbl4Ln31.setBorderColor(borderBorder);tbl4Ln32.setBorderColor(borderBorder);
		
		contentPartTbl4.addCell(tbl4Header1);
		contentPartTbl4.addCell(tbl4Ln11);contentPartTbl4.addCell(tbl4Ln12);
		contentPartTbl4.addCell(tbl4Ln21);contentPartTbl4.addCell(tbl4Ln22);
		contentPartTbl4.addCell(tbl4Ln31);contentPartTbl4.addCell(tbl4Ln32);
		
        document.add(contentPartTbl4);
        
        
        // Add BreakLine
        //document.add(addDefaultEmptyLine(1));
        
        Paragraph modl3 = new Paragraph("Synthèse de la Note d'Encadrement", subTitleStyle);
        modl3.setSpacingBefore(10);modl3.setSpacingAfter(5);
        document.add(modl3);
        
        PdfPTable contentPartTbl5 = new PdfPTable(2);
        contentPartTbl5.setWidthPercentage(100);
        contentPartTbl5.setWidths(new int[]{370, 150});
		PdfPCell tbl5Header1 = new PdfPCell(new Phrase("Note Finale Encadrement =\r\n ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )", noteStyle12));tbl5Header1.setFixedHeight(25f);horizentalAndVerticalCellCenterWithBackground(tbl5Header1, myTableGreyColor1);
		PdfPCell tbl5Header2 = new PdfPCell(new Phrase(""));
		
		PdfPCell tbl5Ln11 = new PdfPCell(new Phrase("Note Encadrant Académique", attributeResponseStyle));tbl5Ln11.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl5Ln11, myTableGreyColor3);
		PdfPCell tbl5Ln12 = new PdfPCell(new Phrase(grilleAE.getNoteAcademicEncadrant().toString() + " / 20", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl5Ln12);
		
		PdfPCell tbl5Ln21 = new PdfPCell(new Phrase("Note Expert", attributeResponseStyle));tbl5Ln21.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl5Ln21, myTableGreyColor3);
		PdfPCell tbl5Ln22 = new PdfPCell(new Phrase(grilleAE.getNoteExpert().toString() + " / 20", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl5Ln22);
		
		PdfPCell tbl5Ln31 = new PdfPCell(new Phrase("Note Encadrant Entreprise", attributeResponseStyle));tbl5Ln31.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl5Ln31, myTableGreyColor3);
		PdfPCell tbl5Ln32 = new PdfPCell(new Phrase(grilleAE.getNoteEncadrantEntreprise().toString() + " / 20", attributeResponseStyle));horizentalAndVerticalCellCenter(tbl5Ln32);
		
		PdfPCell tbl5Ln41 = new PdfPCell(new Phrase("Note Finale d'Encadrement", attributeResponseStyle));tbl5Ln41.setFixedHeight(18f);horizentalAndVerticalCellWithBackground(tbl5Ln41, myTableGreyColor2);
		PdfPCell tbl5Ln42 = new PdfPCell(new Phrase(grilleAE.getNoteFinaleEncadrement().toString() + " / 20", attributeResponseStyle));horizentalAndVerticalCellCenterWithBackground(tbl5Ln42, myTableGreyColor2);

		tbl5Header1.setColspan(2);
		
		tbl5Header1.setBorderColor(borderBorder);tbl5Header2.setBorderColor(borderBorder);
		tbl5Ln11.setBorderColor(borderBorder);tbl5Ln12.setBorderColor(borderBorder);
		tbl5Ln21.setBorderColor(borderBorder);tbl5Ln22.setBorderColor(borderBorder);
		tbl5Ln31.setBorderColor(borderBorder);tbl5Ln32.setBorderColor(borderBorder);
		tbl5Ln41.setBorderColor(borderBorder);tbl5Ln42.setBorderColor(borderBorder);
		
		contentPartTbl5.addCell(tbl5Header1);
		contentPartTbl5.addCell(tbl5Ln11);contentPartTbl5.addCell(tbl5Ln12);
		contentPartTbl5.addCell(tbl5Ln21);contentPartTbl5.addCell(tbl5Ln22);
		contentPartTbl5.addCell(tbl5Ln31);contentPartTbl5.addCell(tbl5Ln32);
		contentPartTbl5.addCell(tbl5Ln41);contentPartTbl5.addCell(tbl5Ln42);
		
        document.add(contentPartTbl5);
        
        DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
		
    	//document.add(addDefaultEmptyLine(5));
    	
    	Paragraph signTime = new Paragraph("Tunis, le " + formattedDate.format(new Date()) + "\r\n", signStyle);
    	signTime.setSpacingBefore(24);
    	signTime.setAlignment(Element.ALIGN_RIGHT);
    	document.add(signTime);

	    Paragraph signParag = new Paragraph(new Phrase(fullNameAE, signStyle));
	    signParag.setAlignment(Element.ALIGN_RIGHT);
	    document.add(signParag);
        
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
	
	private void horizentalAndVerticalCellCenterWithBackground(PdfPCell cell, BaseColor color)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setBackgroundColor(color);
    }
	
	private void horizentalAndVerticalCellWithBackground(PdfPCell cell, BaseColor color)
    {
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setBackgroundColor(color);
    }
	
	private void horizentalAndVerticalCellCenter(PdfPCell cell)
    {
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    }

}
