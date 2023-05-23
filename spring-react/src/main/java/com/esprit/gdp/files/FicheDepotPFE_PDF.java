package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esprit.gdp.dto.StudentTreatDepotDto;
import com.esprit.gdp.models.temporaryEntities.UrgRdvPFEDto;
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

public class FicheDepotPFE_PDF
{

	BaseColor myRedColor = new BaseColor(204, 0, 0);
    BaseColor myGreyColor = new BaseColor(115, 115, 115);
    BaseColor myTableGreyColor = new BaseColor(230, 230, 230);
    
    BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    
    // Segoesc.ttf - Segoescb
    Font signatureFont = FontFactory.getFont("C:/Windows/Fonts/Segoesc.ttf", BaseFont.CP1252,true, 9);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, myRedColor);
    Font subTitleStyle = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9);
    Font attributeResponseStyleSmall = new Font(Font.FontFamily.HELVETICA, 8);
    
    Font noteStyle11 = new Font(Font.FontFamily.HELVETICA, 11);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    Font noteStyle121 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, myGreyColor);
    Font noteStyle13 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
    Font noteStyle14 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font noteItalicStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC, BaseColor.GRAY);
    
    Font redSubTitle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myRedColor);
    
    
    public FicheDepotPFE_PDF(String idEt, String studentFullName, String classe, String pedagogicalEncadrant, String academicCoordiantor, StudentTreatDepotDto studentTreatDepotDto, UrgRdvPFEDto urgRdvPFEDto, String FILE)
	{
		try
		{
            /*
			System.out.println("----**********---------PV--------------------1-> " + idEt);
			System.out.println("----**********---------PV--------------------2-> " + studentFullName);
			System.out.println("----**********---------PV--------------------3-> " + classe);
			System.out.println("----**********---------PV--------------------4-> " + pedagogicalEncadrant);
			System.out.println("----**********---------PV--------------------5-> " + academicCoordiantor);
			System.out.println("----**********---------PV--------------------6-> " + studentTreatDepotDto.getSocieteLibelle());
			System.out.println("----**********---------PV--------------------7-> " + urgRdvPFEDto.getDateDepot());
			System.out.println("----**********---------PV--------------------8-> " + FILE);
			*/
			
			Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent(document, idEt, studentFullName, classe, pedagogicalEncadrant, academicCoordiantor, studentTreatDepotDto, urgRdvPFEDto);
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
		table.setWidths(new int[]{350, 500, 80, 80});
		
        PdfPCell cell1 = new PdfPCell(new Phrase(""));
        PdfPCell cell2 = new PdfPCell(new Phrase("\r\n " + "Fiche de Dépôt PFE", titleStyle));
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
    
	private void addContent(Document document, String identifiant, String studentFullName, String classe, String pedagogicalEncadrant, String academicCoordiantor, StudentTreatDepotDto studentTreatDepotDto, UrgRdvPFEDto urgRdvPFEDto) throws DocumentException
    {
		// Add BreakLine
        document.add(addDefaultEmptyLine(2));
        
        Paragraph cp11 = new Paragraph("Date de Dépôt: " + urgRdvPFEDto.getDateDepot(), noteStyle121); 
        cp11.setAlignment(Element.ALIGN_LEFT);
        document.add(cp11);
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
    	// Add Content - Part 1
    	PdfPTable contentPart1 = new PdfPTable(3);
		contentPart1.setWidthPercentage(100);
		contentPart1.setWidths(new int[]{200, 20, 700});
		
        PdfPCell cp1cell1 = new PdfPCell(new Phrase("Nom de l'Étudiant", noteStyle12));cp1cell1.setFixedHeight(18f);
        PdfPCell cp1cell2 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell3 = new PdfPCell(new Phrase(studentFullName, attributeResponseStyle));

        PdfPCell cp1cell7 = new PdfPCell(new Phrase("Département", noteStyle12));cp1cell7.setFixedHeight(18f);
        PdfPCell cp1cell8 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell9 = new PdfPCell(new Phrase(studentTreatDepotDto.getDepartment(), attributeResponseStyle));
        
        PdfPCell cp1cell4 = new PdfPCell(new Phrase("Option", noteStyle12));cp1cell4.setFixedHeight(18f);
        PdfPCell cp1cell5 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell6 = new PdfPCell(new Phrase(studentTreatDepotDto.getOption(), attributeResponseStyle));
        
        PdfPCell cp1cell16 = new PdfPCell(new Phrase("Classe", noteStyle12));cp1cell16.setFixedHeight(18f);
        PdfPCell cp1cell17 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell18 = new PdfPCell(new Phrase(classe, attributeResponseStyle));
        
        PdfPCell cp1cell10 = new PdfPCell(new Phrase("Entreprise d'Accueil", noteStyle12));cp1cell10.setFixedHeight(18f);
        PdfPCell cp1cell11 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell12 = new PdfPCell(new Phrase(studentTreatDepotDto.getSocieteLibelle(), attributeResponseStyle));
        
        PdfPCell cp1cell13 = new PdfPCell(new Phrase("Nom du ou des Projets", noteStyle12));cp1cell13.setFixedHeight(18f);
        PdfPCell cp1cell14 = new PdfPCell(new Phrase(":", attributeStyle));
        PdfPCell cp1cell15 = new PdfPCell(new Phrase(studentTreatDepotDto.getTitreProjet(), attributeResponseStyle));
        
        cp1cell1.setBorder(Rectangle.NO_BORDER); cp1cell2.setBorder(Rectangle.NO_BORDER); cp1cell3.setBorder(Rectangle.NO_BORDER);
        cp1cell4.setBorder(Rectangle.NO_BORDER); cp1cell5.setBorder(Rectangle.NO_BORDER); cp1cell6.setBorder(Rectangle.NO_BORDER);
        cp1cell7.setBorder(Rectangle.NO_BORDER); cp1cell8.setBorder(Rectangle.NO_BORDER); cp1cell9.setBorder(Rectangle.NO_BORDER);
        cp1cell10.setBorder(Rectangle.NO_BORDER); cp1cell11.setBorder(Rectangle.NO_BORDER); cp1cell12.setBorder(Rectangle.NO_BORDER);
        cp1cell13.setBorder(Rectangle.NO_BORDER); cp1cell14.setBorder(Rectangle.NO_BORDER); cp1cell15.setBorder(Rectangle.NO_BORDER);
        cp1cell16.setBorder(Rectangle.NO_BORDER); cp1cell17.setBorder(Rectangle.NO_BORDER); cp1cell18.setBorder(Rectangle.NO_BORDER);

        contentPart1.addCell(cp1cell1);contentPart1.addCell(cp1cell2);contentPart1.addCell(cp1cell3);
        contentPart1.addCell(cp1cell7);contentPart1.addCell(cp1cell8);contentPart1.addCell(cp1cell9);
        contentPart1.addCell(cp1cell4);contentPart1.addCell(cp1cell5);contentPart1.addCell(cp1cell6);
        contentPart1.addCell(cp1cell16);contentPart1.addCell(cp1cell17);contentPart1.addCell(cp1cell18);
        contentPart1.addCell(cp1cell10);contentPart1.addCell(cp1cell11);contentPart1.addCell(cp1cell12);
        contentPart1.addCell(cp1cell13);contentPart1.addCell(cp1cell14);contentPart1.addCell(cp1cell15);
        
        
        document.add(contentPart1);
        
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(2));
        
        
        // Livrables
        PdfPTable contentPart21 = new PdfPTable(2);
        contentPart21.setWidthPercentage(100);
		contentPart21.setWidths(new int[]{90, 20});
		PdfPCell cp2cell211 = new PdfPCell(new Phrase("Livrable", noteStyle12));cp2cell211.setFixedHeight(25f);
		horizentalAndVerticalCellCenterWithBackground(cp2cell211, myTableGreyColor1);
        PdfPCell cp2cell212 = new PdfPCell(new Phrase("État", noteStyle12));
        horizentalAndVerticalCellCenterWithBackground(cp2cell212, myTableGreyColor1);
        
//		System.out.println("-------------LOL---------------------> 1: " + urgRdvPFEDto.getOnPlanningStg());
//		System.out.println("-------------LOL---------------------> 2: " + urgRdvPFEDto.getOnBilanPerDebStg());
//		System.out.println("-------------LOL---------------------> 3: " + urgRdvPFEDto.getOnBilanPerMilStg());
//		System.out.println("-------------LOL---------------------> 4: " + urgRdvPFEDto.getOnFicheEvalMiPar());
//		System.out.println("-------------LOL---------------------> 5: " + urgRdvPFEDto.getOnFicheEvalFin());
//		System.out.println("-------------LOL---------------------> 6: " + urgRdvPFEDto.getOnBilanPerFinStg());
//		System.out.println("-------------LOL---------------------> 7: " + urgRdvPFEDto.getOnJournalBord());
//		System.out.println("-------------LOL---------------------> 8: " + urgRdvPFEDto.getOnRapportStg());

        PdfPCell cp2cell213 = new PdfPCell(new Phrase("Planning de Stage", attributeResponseStyle));cp2cell213.setFixedHeight(20f);cp2cell213.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell214 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnPlanningStg()), attributeResponseStyleSmall)); cp2cell214.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
        
        PdfPCell cp2cell215 = new PdfPCell(new Phrase("Bilan Périodique début de Stage", attributeResponseStyle));cp2cell215.setFixedHeight(20f);cp2cell215.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell216 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnBilanPerDebStg()), attributeResponseStyleSmall)); cp2cell216.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell217 = new PdfPCell(new Phrase("Bilan Périodique milieu de Stage", attributeResponseStyle));cp2cell217.setFixedHeight(20f);cp2cell217.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell218 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnBilanPerMilStg()), attributeResponseStyleSmall)); cp2cell218.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell219 = new PdfPCell(new Phrase("Fiche d'Évaluation mi-parcours", attributeResponseStyle));cp2cell219.setFixedHeight(20f);cp2cell219.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell2110 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnFicheEvalMiPar()), attributeResponseStyleSmall)); cp2cell2110.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell2111 = new PdfPCell(new Phrase("Fiche d'Évaluation finale", attributeResponseStyle));cp2cell2111.setFixedHeight(20f);cp2cell2111.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell2112 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnFicheEvalFin()), attributeResponseStyleSmall)); cp2cell2112.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell2113 = new PdfPCell(new Phrase("Bilan Périodique fin de Stage", attributeResponseStyle));cp2cell2113.setFixedHeight(20f);cp2cell2113.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell2114 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnBilanPerFinStg()), attributeResponseStyleSmall)); cp2cell2114.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell2115 = new PdfPCell(new Phrase("Journal de Bord", attributeResponseStyle));cp2cell2115.setFixedHeight(20f);cp2cell2115.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell2116 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnJournalBord()), attributeResponseStyleSmall)); cp2cell2116.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell2117 = new PdfPCell(new Phrase("Rapport de Stage", attributeResponseStyle));cp2cell2117.setFixedHeight(20f);cp2cell2117.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell2118 = new PdfPCell(new Phrase(statusLivrable(urgRdvPFEDto.getOnRapportStg()), attributeResponseStyleSmall)); cp2cell2118.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        
        cp2cell211.setBorder(Rectangle.BOX); cp2cell212.setBorder(Rectangle.BOX); 
        cp2cell213.setBorder(Rectangle.BOX); cp2cell214.setBorder(Rectangle.BOX);
        
        cp2cell215.setBorder(Rectangle.BOX); cp2cell216.setBorder(Rectangle.BOX);
        cp2cell217.setBorder(Rectangle.BOX); cp2cell218.setBorder(Rectangle.BOX);
        cp2cell219.setBorder(Rectangle.BOX); cp2cell2110.setBorder(Rectangle.BOX);
        cp2cell2111.setBorder(Rectangle.BOX); cp2cell2112.setBorder(Rectangle.BOX);
        cp2cell2113.setBorder(Rectangle.BOX); cp2cell2114.setBorder(Rectangle.BOX);
        cp2cell2115.setBorder(Rectangle.BOX); cp2cell2116.setBorder(Rectangle.BOX);
        cp2cell2117.setBorder(Rectangle.BOX); cp2cell2118.setBorder(Rectangle.BOX);

        contentPart21.addCell(cp2cell211);contentPart21.addCell(cp2cell212);
        contentPart21.addCell(cp2cell213);contentPart21.addCell(cp2cell214);
        
        contentPart21.addCell(cp2cell215);contentPart21.addCell(cp2cell216);
        contentPart21.addCell(cp2cell217);contentPart21.addCell(cp2cell218);
        contentPart21.addCell(cp2cell219);contentPart21.addCell(cp2cell2110);
        contentPart21.addCell(cp2cell2111);contentPart21.addCell(cp2cell2112);
        contentPart21.addCell(cp2cell2113);contentPart21.addCell(cp2cell2114);
        contentPart21.addCell(cp2cell2115);contentPart21.addCell(cp2cell2116);
        contentPart21.addCell(cp2cell2117);contentPart21.addCell(cp2cell2118);
        
        
        
        // Encadrement
        PdfPTable contentPart22 = new PdfPTable(2);
        contentPart22.setWidthPercentage(100);
		contentPart22.setWidths(new int[]{50, 50});
		PdfPCell cp2cell221 = new PdfPCell(new Phrase("Nom Encadrant Académique", noteStyle12));cp2cell221.setFixedHeight(25f);
		horizentalAndVerticalCellCenterWithBackground(cp2cell221, myTableGreyColor1);
        PdfPCell cp2cell222 = new PdfPCell(new Phrase(pedagogicalEncadrant, attributeResponseStyle));cp2cell222.setFixedHeight(18f);
        horizentalAndVerticalAndHeightCellCenter(cp2cell222, 20f);

        PdfPCell cp2cell223 = new PdfPCell(new Phrase("Note", noteStyle14));cp2cell223.setFixedHeight(10f);
        horizentalAndVerticalCellCenterWithBackground(cp2cell223, myTableGreyColor1);
        PdfPCell cp2cell224 = new PdfPCell(new Phrase(urgRdvPFEDto.getNote().toString(), noteStyle121));
        cp2cell224.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cp2cell224.setVerticalAlignment(Element.ALIGN_MIDDLE);

        
        cp2cell221.setBorder(Rectangle.BOX); cp2cell222.setBorder(Rectangle.BOX); 
        cp2cell223.setBorder(Rectangle.BOX); cp2cell224.setBorder(Rectangle.BOX);
        
        contentPart22.addCell(cp2cell221);contentPart22.addCell(cp2cell222);
        contentPart22.addCell(cp2cell223);contentPart22.addCell(cp2cell224);
        
		// Container : Livrables + Encadrement
        PdfPTable contentPart2 = new PdfPTable(3);
        contentPart2.setWidthPercentage(100);
        contentPart2.setWidths(new int[]{50, 10, 40});
		
        
        // Moyen\r\n[10-12[\r\n
        
        PdfPCell cp2cell1 = new PdfPCell(new Phrase("Livrables", redSubTitle));cp2cell1.setFixedHeight(25f);
        PdfPCell cp2cell2 = new PdfPCell(contentPart21);
        
        PdfPCell cp2cell3 = new PdfPCell(new Phrase(""));
        PdfPCell cp2cell4 = new PdfPCell(new Phrase(""));

        PdfPCell cp2cell5 = new PdfPCell(new Phrase("Encadrement", redSubTitle));cp2cell5.setFixedHeight(18f);
        PdfPCell cp2cell6 = new PdfPCell(contentPart22);cp2cell6.setFixedHeight(50f);
        
        cp2cell1.setBorder(Rectangle.NO_BORDER); cp2cell3.setBorder(Rectangle.NO_BORDER); cp2cell5.setBorder(Rectangle.NO_BORDER); 
        cp2cell2.setBorder(Rectangle.NO_BORDER); cp2cell4.setBorder(Rectangle.NO_BORDER); cp2cell3.setBorder(Rectangle.NO_BORDER); 

        contentPart2.addCell(cp2cell1);contentPart2.addCell(cp2cell3);contentPart2.addCell(cp2cell5);
        contentPart2.addCell(cp2cell2);contentPart2.addCell(cp2cell4);contentPart2.addCell(cp2cell6);
        
        
        document.add(contentPart2);
        
		// Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        Paragraph cpttl = new Paragraph("RDV Pédagogiques", redSubTitle); 
        cpttl.setAlignment(Element.ALIGN_LEFT);
        document.add(cpttl);
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
		PdfPTable contentPart3 = new PdfPTable(4);
		contentPart3.setWidthPercentage(100);
		contentPart3.setWidths(new int[]{50, 50, 50, 50});
		PdfPCell cp2cell31 = new PdfPCell(new Phrase("RDV", noteStyle12));cp2cell31.setFixedHeight(25f);
		horizentalAndVerticalCellCenterWithBackground(cp2cell31, myTableGreyColor1);
        PdfPCell cp2cell32 = new PdfPCell(new Phrase("Date", noteStyle12));
        horizentalAndVerticalCellCenterWithBackground(cp2cell32, myTableGreyColor1);
        PdfPCell cp2cell33 = new PdfPCell(new Phrase("Présence du Membre", noteStyle12));
        horizentalAndVerticalCellCenterWithBackground(cp2cell33, myTableGreyColor1);
        PdfPCell cp2cell34 = new PdfPCell(new Phrase("État", noteStyle12));
        horizentalAndVerticalCellCenterWithBackground(cp2cell34, myTableGreyColor1);
        
        PdfPCell cp2cell35 = new PdfPCell(new Phrase("1ère Restitution", noteStyle12));cp2cell35.setFixedHeight(18f);cp2cell35.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);cp2cell35.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell36 = new PdfPCell(new Phrase(formatDate(urgRdvPFEDto.getDateRDV1()), attributeResponseStyle));cp2cell36.setFixedHeight(18f);cp2cell36.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
        PdfPCell cp2cell37 = new PdfPCell(new Phrase(presenceMembre(urgRdvPFEDto.getPresenceKindRDV1()), attributeResponseStyle));cp2cell37.setFixedHeight(18f);cp2cell37.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cp2cell38 = new PdfPCell(new Phrase(statusRDV(urgRdvPFEDto.getStatusKindRDV1()), attributeResponseStyle));cp2cell38.setFixedHeight(18f);cp2cell38.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell39 = new PdfPCell(new Phrase("2ère Restitution", noteStyle12));cp2cell39.setFixedHeight(18f);cp2cell39.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);cp2cell39.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell310 = new PdfPCell(new Phrase(formatDate(urgRdvPFEDto.getDateRDV2()), attributeResponseStyle));cp2cell310.setFixedHeight(18f);cp2cell310.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cp2cell311 = new PdfPCell(new Phrase(presenceMembre(urgRdvPFEDto.getPresenceKindRDV2()), attributeResponseStyle));cp2cell311.setFixedHeight(18f);cp2cell311.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cp2cell312 = new PdfPCell(new Phrase(statusRDV(urgRdvPFEDto.getStatusKindRDV2()), attributeResponseStyle));cp2cell312.setFixedHeight(18f);cp2cell312.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cp2cell313 = new PdfPCell(new Phrase("3ère Restitution", noteStyle12));cp2cell313.setFixedHeight(18f);cp2cell313.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);cp2cell313.setBackgroundColor(myTableGreyColor3);
        PdfPCell cp2cell314 = new PdfPCell(new Phrase(formatDate(urgRdvPFEDto.getDateRDV3()), attributeResponseStyle));cp2cell314.setFixedHeight(18f);cp2cell314.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cp2cell315 = new PdfPCell(new Phrase(presenceMembre(urgRdvPFEDto.getPresenceKindRDV3()), attributeResponseStyle));cp2cell315.setFixedHeight(18f);cp2cell315.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cp2cell316 = new PdfPCell(new Phrase(statusRDV(urgRdvPFEDto.getStatusKindRDV3()), attributeResponseStyle));cp2cell316.setFixedHeight(18f);cp2cell316.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        cp2cell31.setBorder(Rectangle.BOX); cp2cell32.setBorder(Rectangle.BOX);cp2cell33.setBorder(Rectangle.BOX); cp2cell34.setBorder(Rectangle.BOX);
        cp2cell35.setBorder(Rectangle.BOX); cp2cell36.setBorder(Rectangle.BOX);cp2cell37.setBorder(Rectangle.BOX); cp2cell38.setBorder(Rectangle.BOX);
        cp2cell39.setBorder(Rectangle.BOX); cp2cell310.setBorder(Rectangle.BOX);cp2cell311.setBorder(Rectangle.BOX); cp2cell312.setBorder(Rectangle.BOX);
        cp2cell313.setBorder(Rectangle.BOX); cp2cell314.setBorder(Rectangle.BOX);cp2cell315.setBorder(Rectangle.BOX); cp2cell316.setBorder(Rectangle.BOX);

        contentPart3.addCell(cp2cell31);contentPart3.addCell(cp2cell32);contentPart3.addCell(cp2cell33);contentPart3.addCell(cp2cell34);
        contentPart3.addCell(cp2cell35);contentPart3.addCell(cp2cell36);contentPart3.addCell(cp2cell37);contentPart3.addCell(cp2cell38);
        contentPart3.addCell(cp2cell39);contentPart3.addCell(cp2cell310);contentPart3.addCell(cp2cell311);contentPart3.addCell(cp2cell312);
        contentPart3.addCell(cp2cell313);contentPart3.addCell(cp2cell314);contentPart3.addCell(cp2cell315);contentPart3.addCell(cp2cell316);
        
        document.add(contentPart3);
        
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(2));
        
        // Add Content - Part 7
        Paragraph cp7 = new Paragraph("Signatures", noteStyle13); 
        cp7.setAlignment(Element.ALIGN_CENTER);
        document.add(cp7);
        
        
        // Add BreakLine
        document.add(addDefaultEmptyLine(1));
        
        // Add Content - Part 8
        PdfPTable signatureTBL = new PdfPTable(3);
        signatureTBL.setWidthPercentage(100);    // table.setTotalWidth(1000);
        signatureTBL.setWidths(new int[]{500, 20, 500});
		
        PdfPCell cp8cell1 = new PdfPCell(new Phrase("Encadrant Académique", noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cp8cell1, 20f);cp8cell1.setBackgroundColor(myTableGreyColor1);
        PdfPCell cp8cell1a = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell2 = new PdfPCell(new Phrase("Coordinateur Pédagogique des Stages", noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cp8cell2, 20f);cp8cell2.setBackgroundColor(myTableGreyColor1);
        
        PdfPCell cp8cell4 = new PdfPCell(new Phrase(pedagogicalEncadrant, attributeResponseStyle)); 
        cp8cell4.setBackgroundColor(myTableGreyColor2); horizentalAndVerticalAndHeightCellCenter(cp8cell4, 20f);
        PdfPCell cp8cellaa = new PdfPCell(new Phrase(""));
        PdfPCell cp8cell5 = new PdfPCell(new Phrase(academicCoordiantor, attributeResponseStyle)); 
        cp8cell5.setBackgroundColor(myTableGreyColor2); horizentalAndVerticalAndHeightCellCenter(cp8cell5, 20f);
        
        
        
        // seguisym.ttf
        PdfPCell cp8cell7 = new PdfPCell(new Phrase("OK, \r\n by " + pedagogicalEncadrant.toLowerCase(), signatureFont)); cp8cell7.setFixedHeight(40f);cp8cell7.setBackgroundColor(myTableGreyColor3);horizentalAndVerticalAndHeightCellCenter(cp8cell7);
        PdfPCell cp8cellaaa = new PdfPCell(new Phrase(""));
        System.out.println("*************************************************************> academicCoordiantor: " + academicCoordiantor.toLowerCase());
        PdfPCell cp8cell8 = new PdfPCell(new Phrase("OK, \r\n by " + academicCoordiantor.toLowerCase(), signatureFont));cp8cell8.setBackgroundColor(myTableGreyColor3); horizentalAndVerticalAndHeightCellCenter(cp8cell8);

        cp8cell1.setBorder(Rectangle.NO_BORDER);cp8cell2.setBorder(Rectangle.NO_BORDER);
        cp8cell4.setBorder(Rectangle.NO_BORDER);cp8cell5.setBorder(Rectangle.NO_BORDER);
        cp8cell7.setBorder(Rectangle.NO_BORDER);cp8cell8.setBorder(Rectangle.NO_BORDER);
        
        cp8cell1a.setBorder(Rectangle.NO_BORDER);cp8cellaa.setBorder(Rectangle.NO_BORDER);cp8cellaaa.setBorder(Rectangle.NO_BORDER);
        
        signatureTBL.addCell(cp8cell1);signatureTBL.addCell(cp8cell1a);signatureTBL.addCell(cp8cell2);
        signatureTBL.addCell(cp8cell4);signatureTBL.addCell(cp8cellaa);signatureTBL.addCell(cp8cell5);
        signatureTBL.addCell(cp8cell7);signatureTBL.addCell(cp8cellaaa);signatureTBL.addCell(cp8cell8);
        
        document.add(signatureTBL);
        
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

	private void horizentalAndVerticalAndHeightCellCenter(PdfPCell cell, float fixedHeight)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setFixedHeight(fixedHeight);
    }
	
	private void horizentalAndVerticalAndHeightCellCenter(PdfPCell cell)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    }
    
	private void horizentalAndVerticalCellCenterWithBackground(PdfPCell cell, BaseColor color)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setBackgroundColor(color);
    }
    
	private String statusLivrable(Boolean status)
	{
		String strStatus = "--";
		if(status == true)
		{
			strStatus = "OK";
		}
		return strStatus;
	}
	
	private String presenceMembre(String presence)
	{
		String strStatus = "Oui";
		if(presence.equalsIgnoreCase("Non"))
		{
			strStatus = "--";
		}
		return strStatus;
	}
	
	private String statusRDV(String status)
	{
		String strStatus = "OK";
		if(status.equalsIgnoreCase("Non"))
		{
			strStatus = "--";
		}
		return strStatus;
	}
	
	private String formatDate(Date dt)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(dt);
	}
    
}
