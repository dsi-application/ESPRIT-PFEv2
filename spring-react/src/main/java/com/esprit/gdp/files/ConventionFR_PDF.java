package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.esprit.gdp.dto.StudentConvFRDto;
import com.esprit.gdp.models.Convention;
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


public class ConventionFR_PDF
{
	
	BaseColor myRedColor = new BaseColor(204, 0, 0);
	BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    BaseColor myTableGreyColor4 = new BaseColor(242, 242, 242);
    BaseColor myGreyColor = new BaseColor(102, 102, 102);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font nbrConvStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, myRedColor);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    Font noteGreyBoldStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myGreyColor);
    Font noteGreyNormalStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY);
    
    
    public ConventionFR_PDF(Optional<Convention> conv, String path, StudentConvFRDto scf, String studentOption, String studentDepartment)
    {
        try
        {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(path));
//            document.open();
//            addMetaData(document);
//            addTitlePage(document);
//            document.close();
            
        	System.out.println("-----------------------------------> LOL: Generate PDF");
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent(document, conv, scf, studentOption, studentDepartment);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, Optional<Convention> conv, StudentConvFRDto scf, String studentOption, String studentDepartment) throws DocumentException, MalformedURLException, IOException
    {
    	
    	DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
    	document.add(addDefaultEmptyLine(2));
    	Paragraph cpttl1 = new Paragraph("Avertissement", noteGreyBoldStyle);
        document.add(cpttl1);
        
        Paragraph cpttl2 = new Paragraph("Les personnes qui sollicitent le bénéfice d’une convention de stage doivent être obligatoirement " + 
        		"inscrites et participer réellement à un cycle de formation ou d’enseignement " + 
        		"autorisant la réalisation d’un stage en entreprise. La convention de stage peut être remise en " + 
        		"cause par l’inspection du travail lors d’un contrôle au sein de l’entreprise ou à la demande du " + 
        		"stagiaire. Le juge peut alors procéder à une requalification en contrat de travail si les " + 
        		"conditions de stage ne sont pas remplies.", attributeStyle);
        cpttl2.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cpttl2);
        
        Paragraph cpttl3 = new Paragraph("Les ressortissants étrangers n’appartenant pas à l’Union Européenne et à l’Espace " + 
        		"Économique Européen se voient délivrés une carte de séjour portant la mention « stagiaire ». " + 
        		"Ils ne peuvent exercer aucune activité salariée sur le territoire national. Ils ne sont pas " + 
        		"autorisés à se maintenir sur le territoire français à l’issue de leur stage.", attributeStyle);
        cpttl3.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cpttl3);
        document.add(addDefaultEmptyLine(2));
        
        
        Paragraph cptt21 = new Paragraph("ARTICLE 1 : LES PARTIES SIGNATAIRES", noteGreyBoldStyle);
        document.add(cptt21);
        
        /* Entreprise d'Accueil */
        Paragraph cptt22 = new Paragraph("Entreprise d'Accueil", noteGreyNormalStyle);
        document.add(cptt22);
        
        PdfPTable contentPart21 = new PdfPTable(2);
        contentPart21.setWidthPercentage(100);
        contentPart21.setWidths(new int[]{300, 670});
		
        PdfPCell cp1cellz_12 = new PdfPCell(new Phrase("Nom :", attributeStyle));
        PdfPCell cp1cellz_13 = new PdfPCell(new Phrase(conv.get().getEntrepriseAccueilConvention().getDesignation(), attributeStyle));
        
        PdfPCell cp1cellz_321 = new PdfPCell(new Phrase("Représentée par :", attributeStyle));
        PdfPCell cp1cellz_331 = new PdfPCell(new Phrase(conv.get().getResponsable(), attributeStyle));
        
        PdfPCell cp1cellz_322 = new PdfPCell(new Phrase("En qualité de :", attributeStyle));
        PdfPCell cp1cellz_332 = new PdfPCell(new Phrase("Responsable RH", attributeStyle));
        
        PdfPCell cp1cellz_22 = new PdfPCell(new Phrase("Adresse :", attributeStyle));
        PdfPCell cp1cellz_23 = new PdfPCell(new Phrase(conv.get().getAddress(), attributeStyle));
        
        PdfPCell cp1cellz_52 = new PdfPCell(new Phrase("Téléphone :", attributeStyle));
        String telEntrep = "............................................................";
        if(conv.get().getEntrepriseAccueilConvention().getSecteurEntreprise() != null)
        {
        	telEntrep = conv.get().getTelephone();
        }
        PdfPCell cp1cellz_53 = new PdfPCell(new Phrase(telEntrep, attributeStyle));

        PdfPCell cp1cellz_42 = new PdfPCell(new Phrase("N° SIREN ou SIRET :", attributeStyle));
        PdfPCell cp1cellz_43 = new PdfPCell(new Phrase("............................................................", attributeStyle));
        
        String actEntrep = "............................................................";
        if(conv.get().getEntrepriseAccueilConvention().getSecteurEntreprise() != null)
        {
        	actEntrep = conv.get().getEntrepriseAccueilConvention().getSecteurEntreprise().getLibelleSecteur();
        }
        PdfPCell cp1cellz_62 = new PdfPCell(new Phrase("Nature de l’activité de l’entreprise :", attributeStyle));
        PdfPCell cp1cellz_63 = new PdfPCell(new Phrase(actEntrep, attributeStyle));
        
        
        cp1cellz_12.setBorder(Rectangle.NO_BORDER);cp1cellz_13.setBorder(Rectangle.NO_BORDER);
        cp1cellz_321.setBorder(Rectangle.NO_BORDER);cp1cellz_331.setBorder(Rectangle.NO_BORDER);
        cp1cellz_322.setBorder(Rectangle.NO_BORDER);cp1cellz_332.setBorder(Rectangle.NO_BORDER);
        cp1cellz_22.setBorder(Rectangle.NO_BORDER);cp1cellz_23.setBorder(Rectangle.NO_BORDER);
        cp1cellz_52.setBorder(Rectangle.NO_BORDER);cp1cellz_53.setBorder(Rectangle.NO_BORDER);
        cp1cellz_42.setBorder(Rectangle.NO_BORDER);cp1cellz_43.setBorder(Rectangle.NO_BORDER);
        cp1cellz_62.setBorder(Rectangle.NO_BORDER);cp1cellz_63.setBorder(Rectangle.NO_BORDER);
        
        contentPart21.addCell(cp1cellz_12);contentPart21.addCell(cp1cellz_13);
        contentPart21.addCell(cp1cellz_321);contentPart21.addCell(cp1cellz_331);
        contentPart21.addCell(cp1cellz_322);contentPart21.addCell(cp1cellz_332);
        contentPart21.addCell(cp1cellz_22);contentPart21.addCell(cp1cellz_23);
        contentPart21.addCell(cp1cellz_52);contentPart21.addCell(cp1cellz_53);
        contentPart21.addCell(cp1cellz_42);contentPart21.addCell(cp1cellz_43);
        contentPart21.addCell(cp1cellz_62);contentPart21.addCell(cp1cellz_63);
        
        document.add(contentPart21);
        document.add(addDefaultEmptyLine(1));
        
        /* Stagiaire */
        Paragraph cptt3 = new Paragraph("Stagiaire", noteGreyNormalStyle);
        document.add(cptt3);
        
        PdfPTable contentPart3 = new PdfPTable(2);
        contentPart3.setWidthPercentage(100);
        contentPart3.setWidths(new int[]{250, 720});
		
        PdfPCell cell_311 = new PdfPCell(new Phrase("Nom et Prénom :", attributeStyle));
        PdfPCell cell_312 = new PdfPCell(new Phrase(scf.getFullName(), attributeStyle));
        
        PdfPCell cell_321 = new PdfPCell(new Phrase("Date et lieu de naissance :", attributeStyle));
        PdfPCell cell_322 = new PdfPCell(new Phrase(scf.getDatenaisset() + " à " + scf.getLieunaiset(), attributeStyle));
        
        PdfPCell cell_331 = new PdfPCell(new Phrase("Nationalité :", attributeStyle));
        PdfPCell cell_332 = new PdfPCell(new Phrase(scf.getNationalite(), attributeStyle));
        
        PdfPCell cell_341 = new PdfPCell(new Phrase("Adresse :", attributeStyle));
        PdfPCell cell_342 = new PdfPCell(new Phrase(scf.getAdresseet(), attributeStyle));
        
        PdfPCell cell_351 = new PdfPCell(new Phrase("Téléphone :", attributeStyle));
        PdfPCell cell_352 = new PdfPCell(new Phrase(scf.getTelet(), attributeStyle));

        PdfPCell cell_361 = new PdfPCell(new Phrase("E-Mail :", attributeStyle));
        PdfPCell cell_362 = new PdfPCell(new Phrase(scf.getAdressemailesp(), attributeStyle));
        
        cell_311.setBorder(Rectangle.NO_BORDER);cell_312.setBorder(Rectangle.NO_BORDER);
        cell_321.setBorder(Rectangle.NO_BORDER);cell_322.setBorder(Rectangle.NO_BORDER);
        cell_331.setBorder(Rectangle.NO_BORDER);cell_332.setBorder(Rectangle.NO_BORDER);
        cell_341.setBorder(Rectangle.NO_BORDER);cell_342.setBorder(Rectangle.NO_BORDER);
        cell_351.setBorder(Rectangle.NO_BORDER);cell_352.setBorder(Rectangle.NO_BORDER);
        cell_361.setBorder(Rectangle.NO_BORDER);cell_362.setBorder(Rectangle.NO_BORDER);
        
        contentPart3.addCell(cell_311);contentPart3.addCell(cell_312);
        contentPart3.addCell(cell_321);contentPart3.addCell(cell_322);
        contentPart3.addCell(cell_331);contentPart3.addCell(cell_332);
        contentPart3.addCell(cell_341);contentPart3.addCell(cell_342);
        contentPart3.addCell(cell_351);contentPart3.addCell(cell_352);
        contentPart3.addCell(cell_361);contentPart3.addCell(cell_362);
        
        document.add(contentPart3);
        document.add(addDefaultEmptyLine(1));
        
        /* Etablissement d'enseignement ou organisme de formation */
        Paragraph cptt4 = new Paragraph("Etablissement d'enseignement ou organisme de formation", noteGreyNormalStyle);
        document.add(cptt4);
        
        PdfPTable contentPart4 = new PdfPTable(2);
        contentPart4.setWidthPercentage(100);
        contentPart4.setWidths(new int[]{150, 820});
		
        PdfPCell cell_411 = new PdfPCell(new Phrase("Nom :", attributeStyle));
        PdfPCell cell_412 = new PdfPCell(new Phrase("ESPRIT École Supérieure Privée d’Ingénierie et de Technologies", attributeStyle));
       
        PdfPCell cell_421 = new PdfPCell(new Phrase("Représenté par :", attributeStyle));
        PdfPCell cell_422 = new PdfPCell(new Phrase("Pr. Tahar Benlakhdar", attributeStyle));
        
        PdfPCell cell_431 = new PdfPCell(new Phrase("En qualité de :", attributeStyle));
        PdfPCell cell_432 = new PdfPCell(new Phrase("Directeur Fondateur", attributeStyle));
        
        PdfPCell cell_441 = new PdfPCell(new Phrase("Adresse :", attributeStyle));
        PdfPCell cell_442 = new PdfPCell(new Phrase("Zone Industrielle Chotrana II-B.P160-2083 - Pôle Technologique-El Ghazala-Ariana", attributeStyle));
        
        PdfPCell cell_4511 = new PdfPCell(new Phrase("Téléphone :", attributeStyle));
        PdfPCell cell_4521 = new PdfPCell(new Phrase("+ 216 70 685 685", attributeStyle));
        
        PdfPCell cell_4512 = new PdfPCell(new Phrase("Fax :", attributeStyle));
        PdfPCell cell_4522 = new PdfPCell(new Phrase("+216 70685 454", attributeStyle));

        PdfPCell cell_461 = new PdfPCell(new Phrase("E-Mail :", attributeStyle));
        PdfPCell cell_462 = new PdfPCell(new Phrase("contact.stage@esprit.tn", attributeStyle));
        
        cell_411.setBorder(Rectangle.NO_BORDER);cell_412.setBorder(Rectangle.NO_BORDER);
        cell_421.setBorder(Rectangle.NO_BORDER);cell_422.setBorder(Rectangle.NO_BORDER);
        cell_431.setBorder(Rectangle.NO_BORDER);cell_432.setBorder(Rectangle.NO_BORDER);
        cell_441.setBorder(Rectangle.NO_BORDER);cell_442.setBorder(Rectangle.NO_BORDER);
        cell_4511.setBorder(Rectangle.NO_BORDER);cell_4521.setBorder(Rectangle.NO_BORDER);
        cell_4512.setBorder(Rectangle.NO_BORDER);cell_4522.setBorder(Rectangle.NO_BORDER);
        cell_461.setBorder(Rectangle.NO_BORDER);cell_462.setBorder(Rectangle.NO_BORDER);
        
        contentPart4.addCell(cell_411);contentPart4.addCell(cell_412);
        contentPart4.addCell(cell_421);contentPart4.addCell(cell_422);
        contentPart4.addCell(cell_431);contentPart4.addCell(cell_432);
        contentPart4.addCell(cell_441);contentPart4.addCell(cell_442);
        contentPart4.addCell(cell_4511);contentPart4.addCell(cell_4521);
        contentPart4.addCell(cell_4512);contentPart4.addCell(cell_4522);
        contentPart4.addCell(cell_461);contentPart4.addCell(cell_462);
        
        document.add(contentPart4);
        document.add(addDefaultEmptyLine(1));
        
        Paragraph cptt23 = new Paragraph("Pour les établissements d’enseignement ou de formation situés à l’étranger, visa " + 
        		"du service culturel, scientifique et de coopération de l’Ambassade de France, ou de " + 
        		"l’organisme français qui facilite la venue du stagiaire (établissement d’enseignement, " + 
        		"organisme de formation, association agréée, agence nationale Leonardo Da Vinci).\r\n"
        		+ "Cet organisme devant être identifié : nom, adresse, téléphone, télécopie et identification du responsable.", attributeStyle);
        cptt23.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt23);
        document.add(addDefaultEmptyLine(3));
        

        /* Association Agree (Le cas Echeant) */
        Paragraph cptt24 = new Paragraph("Association Agree (Le cas Échéant)", noteGreyNormalStyle);
        document.add(cptt24);
        
        PdfPTable contentPart5 = new PdfPTable(2);
        contentPart5.setWidthPercentage(100);
        contentPart5.setWidths(new int[]{150, 820});
		
        PdfPCell cell_511 = new PdfPCell(new Phrase("Nom :", attributeStyle));
        PdfPCell cell_512 = new PdfPCell(new Phrase(".............................."));
       
        PdfPCell cell_521 = new PdfPCell(new Phrase("Représenté par :", attributeStyle));
        PdfPCell cell_522 = new PdfPCell(new Phrase(".............................."));
        
        PdfPCell cell_531 = new PdfPCell(new Phrase("En qualité de :", attributeStyle));
        PdfPCell cell_532 = new PdfPCell(new Phrase(".............................."));
        
        PdfPCell cell_541 = new PdfPCell(new Phrase("Adresse :", attributeStyle));
        PdfPCell cell_542 = new PdfPCell(new Phrase(".............................."));
        
        PdfPCell cell_551 = new PdfPCell(new Phrase("Téléphone :", attributeStyle));
        PdfPCell cell_552 = new PdfPCell(new Phrase(".............................."));
        
        PdfPCell cell_561 = new PdfPCell(new Phrase("E-Mail :", attributeStyle));
        PdfPCell cell_562 = new PdfPCell(new Phrase(".............................."));
        
        cell_511.setBorder(Rectangle.NO_BORDER);cell_512.setBorder(Rectangle.NO_BORDER);
        cell_521.setBorder(Rectangle.NO_BORDER);cell_522.setBorder(Rectangle.NO_BORDER);
        cell_531.setBorder(Rectangle.NO_BORDER);cell_532.setBorder(Rectangle.NO_BORDER);
        cell_541.setBorder(Rectangle.NO_BORDER);cell_542.setBorder(Rectangle.NO_BORDER);
        cell_551.setBorder(Rectangle.NO_BORDER);cell_552.setBorder(Rectangle.NO_BORDER);
        cell_561.setBorder(Rectangle.NO_BORDER);cell_562.setBorder(Rectangle.NO_BORDER);
        
        contentPart5.addCell(cell_511);contentPart5.addCell(cell_512);
        contentPart5.addCell(cell_521);contentPart5.addCell(cell_522);
        contentPart5.addCell(cell_531);contentPart5.addCell(cell_532);
        contentPart5.addCell(cell_541);contentPart5.addCell(cell_542);
        contentPart5.addCell(cell_551);contentPart5.addCell(cell_552);
        contentPart5.addCell(cell_561);contentPart5.addCell(cell_562);
        
        document.add(contentPart5);
        document.add(addDefaultEmptyLine(1));
        
        
        Paragraph cptt31 = new Paragraph("ARTICLE 2 : ETUDES OU FORMATION SUIVIES", noteGreyBoldStyle);
        document.add(cptt31);
        
        PdfPTable contentPart6 = new PdfPTable(2);
        contentPart6.setWidthPercentage(100);
        contentPart6.setWidths(new int[]{350, 620});
		
        PdfPCell cell_611 = new PdfPCell(new Phrase("Nature des études ou de la formation :", attributeStyle));
        PdfPCell cell_612 = new PdfPCell(new Phrase("Ingénierie en " + studentDepartment, attributeStyle));
       
        PdfPCell cell_621 = new PdfPCell(new Phrase("Durée :", attributeStyle));
        PdfPCell cell_622 = new PdfPCell(new Phrase("3 ans d’études en cycle d'ingénieur", attributeStyle));
        
        PdfPCell cell_631 = new PdfPCell(new Phrase("Niveau de la préparation atteint :", attributeStyle));
        PdfPCell cell_632 = new PdfPCell(new Phrase("3ème année " + studentDepartment, attributeStyle));
        
        PdfPCell cell_641 = new PdfPCell(new Phrase("Diplôme préparé ou qualification visée :", attributeStyle));
        PdfPCell cell_642 = new PdfPCell(new Phrase("Diplôme National d’Ingénieur en " + studentDepartment, attributeStyle));
        
        cell_611.setBorder(Rectangle.NO_BORDER);cell_612.setBorder(Rectangle.NO_BORDER);
        cell_621.setBorder(Rectangle.NO_BORDER);cell_622.setBorder(Rectangle.NO_BORDER);
        cell_631.setBorder(Rectangle.NO_BORDER);cell_632.setBorder(Rectangle.NO_BORDER);
        cell_641.setBorder(Rectangle.NO_BORDER);cell_642.setBorder(Rectangle.NO_BORDER);
       
        contentPart6.addCell(cell_611);contentPart6.addCell(cell_612);
        contentPart6.addCell(cell_621);contentPart6.addCell(cell_622);
        contentPart6.addCell(cell_631);contentPart6.addCell(cell_632);
        contentPart6.addCell(cell_641);contentPart6.addCell(cell_642);
        
        document.add(contentPart6);
        document.add(addDefaultEmptyLine(1));
        
        
        Paragraph cptt41 = new Paragraph("ARTICLE 3 : PROGRAMME DU STAGE", noteGreyBoldStyle);
        document.add(cptt41);
        
        Paragraph cptt42 = new Paragraph("Le stage a pour but d’assurer l’application pratique des connaissances théoriques du stagiaire. " + 
        		"L’entreprise d’accueil doit confier au stagiaire, en accord avec l’établissement " + 
        		"d’enseignement ou l’organisme de formation, des tâches et des responsabilités en rapport " + 
        		"direct avec les qualifications et les compétences auxquelles conduit le diplôme préparé ou la " + 
        		"formation suivie.\r\n"
        		+ "\r\nLe contenu du cadre ci-dessous doit être défini conjointement par les " + 
        		"responsables du stagiaire dans l’établissement d’enseignement ou l’organisme de formation et dans l’entreprise.", attributeStyle);
        cptt42.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt42);
        
        Paragraph cptt43 = new Paragraph("Progression dans les apprentissages et situations d’activité dans lesquelles sera placé le stagiaire :\r\n", attributeStyle);
        document.add(cptt43);
        
        Paragraph cptt44 = new Paragraph("\r\nNom, prénom et qualité du responsable du stagiaire dans l’établissement d’enseignement ou l'organisme de formation (ESPRIT) est :", attributeStyle);
        cptt44.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt44);
        
        Paragraph cptt45 = new Paragraph("Majdi GHARBI, responsable des stages\r\n", attributeStyle);
        document.add(cptt45);
        
        Paragraph cptt46 = new Paragraph("\r\nNom, prénom et qualité du responsable du suivi de stage dans l'entreprise :", attributeStyle);
        document.add(cptt46);
        
        Paragraph cptt47 = new Paragraph("............................................................................................................................................................................................", attributeStyle);
        document.add(cptt47);
        
        Paragraph cptt48 = new Paragraph("\r\nLes activités confiées au stagiaire sont décrites en annexes.", attributeStyle);
        document.add(cptt48);
        document.add(addDefaultEmptyLine(1));
        
        
        Paragraph cptt49 = new Paragraph("ARTICLE 4 : ORGANISATION DE LA DUREE DU STAGE", noteGreyBoldStyle);
        document.add(cptt49);
        
        Integer month = 0;
		Integer y1 = conv.get().getDateDebut().getYear() + 1900;
		Integer y2 = conv.get().getDateFin().getYear() + 1900;
		Integer m1 = conv.get().getDateDebut().getMonth() + 1;
		Integer m2 = conv.get().getDateFin().getMonth() + 1;
		if (y1 == y2)
		{
			month = (m2 - m1);
			System.out.println("----------> 1:" + y1 + " - " + y2 + " - " + m1 + " - " + m2);
		}
		else
		{
			int nbryears = (y2 - y1);
			month = (12 - m1) + (((nbryears - 1) * 12) + m2);
		}
        String trainingDuration1 = "Durée du stage: " + month + " mois";
        Paragraph cptt410 = new Paragraph(trainingDuration1, attributeStyle);
        document.add(cptt410);
        
        String trainingDuration2 = "Du: " + formattedDate.format(conv.get().getDateDebut()) + " au " + formattedDate.format(conv.get().getDateFin());
        Paragraph cptt411 = new Paragraph(trainingDuration2, attributeStyle);
        document.add(cptt411);
        
        Paragraph cptt412 = new Paragraph("Elle doit correspondre à celle prévue dans le cadre des études ou de la formation.", attributeStyle);
        document.add(cptt412);
        
        Paragraph cptt413 = new Paragraph("En cas de modification des dates prévues :", attributeStyle);
        document.add(cptt413);
        
        Paragraph cptt414 = new Paragraph("Toute modification des dates du stage donnera lieu à un avenant à la présente convention.", attributeStyle);
        document.add(cptt414);
        
        Paragraph cptt415 = new Paragraph("Le stage peut être renouvelé, par avenant, dans la limite de la durée maximale autorisée.", attributeStyle);
        document.add(cptt415);
        document.add(addDefaultEmptyLine(1));
        
        Paragraph cptt416 = new Paragraph("Lieu(x) où il s’effectue (location) :", attributeStyle);
        document.add(cptt416);
        
        Paragraph cptt417 = new Paragraph("............................................................................................................................................................................................", attributeStyle);
        document.add(cptt417);
        document.add(addDefaultEmptyLine(1));
        
        Paragraph cptt418 = new Paragraph("En cas de lieux multiples, préciser chacun d’entre eux et aussi les dates correspondantes.", attributeStyle);
        document.add(cptt418);
        
        Paragraph cptt419 = new Paragraph("Horaires de présence du stagiaire :", attributeStyle);
        document.add(cptt419);
        document.add(addDefaultEmptyLine(3));
        
        
        PdfPTable contentPart8 = new PdfPTable(3);
        contentPart8.setWidthPercentage(100);
        contentPart8.setWidths(new int[]{150, 400, 400});
		
        PdfPCell cell_811 = new PdfPCell(new Phrase(""));
        PdfPCell cell_812 = new PdfPCell(new Phrase("Matin", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_812, myTableGreyColor1);cell_812.setFixedHeight(20f);
        PdfPCell cell_813 = new PdfPCell(new Phrase("Après-midi", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_813, myTableGreyColor1);
       
        PdfPCell cell_821 = new PdfPCell(new Phrase("Lundi", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_821, myTableGreyColor1);cell_821.setFixedHeight(20f);
        PdfPCell cell_822 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_822, myTableGreyColor4);
        PdfPCell cell_823 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_823, myTableGreyColor4);
        
        PdfPCell cell_831 = new PdfPCell(new Phrase("Mardi", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_831, myTableGreyColor1);cell_831.setFixedHeight(20f);
        PdfPCell cell_832 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_832, myTableGreyColor4);
        PdfPCell cell_833 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_833, myTableGreyColor4);
        
        PdfPCell cell_841 = new PdfPCell(new Phrase("Mercredi", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_841, myTableGreyColor1);cell_841.setFixedHeight(20f);
        PdfPCell cell_842 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_842, myTableGreyColor4);
        PdfPCell cell_843 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_843, myTableGreyColor4);
        
        PdfPCell cell_851 = new PdfPCell(new Phrase("Jeudi", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_851, myTableGreyColor1);cell_851.setFixedHeight(20f);
        PdfPCell cell_852 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_852, myTableGreyColor4);
        PdfPCell cell_853 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_853, myTableGreyColor4);
        
        PdfPCell cell_861 = new PdfPCell(new Phrase("Vendredi", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_861, myTableGreyColor1);cell_861.setFixedHeight(20f);
        PdfPCell cell_862 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_862, myTableGreyColor4);
        PdfPCell cell_863 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_863, myTableGreyColor4);
        
        PdfPCell cell_871 = new PdfPCell(new Phrase("Samedi", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_871, myTableGreyColor1);cell_871.setFixedHeight(20f);
        PdfPCell cell_872 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_872, myTableGreyColor4);
        PdfPCell cell_873 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_873, myTableGreyColor4);
        
        PdfPCell cell_881 = new PdfPCell(new Phrase("Dimanche", noteStyle12));horizentalAndVerticalCellCenterWithBackground(cell_881, myTableGreyColor1);cell_881.setFixedHeight(20f);
        PdfPCell cell_882 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_882, myTableGreyColor4);
        PdfPCell cell_883 = new PdfPCell(new Phrase("de ............ (nombre) h à ............ (nombre) h", attributeStyle));horizentalAndVerticalCellCenterWithBackground(cell_883, myTableGreyColor4);
        
        cell_811.setBorder(Rectangle.BOTTOM);cell_812.setBorder(Rectangle.BOX);cell_813.setBorder(Rectangle.BOX);
        cell_821.setBorder(Rectangle.BOX);cell_822.setBorder(Rectangle.BOX);cell_823.setBorder(Rectangle.BOX);
        cell_831.setBorder(Rectangle.BOX);cell_832.setBorder(Rectangle.BOX);cell_833.setBorder(Rectangle.BOX);
        cell_841.setBorder(Rectangle.BOX);cell_842.setBorder(Rectangle.BOX);cell_843.setBorder(Rectangle.BOX);
        cell_851.setBorder(Rectangle.BOX);cell_852.setBorder(Rectangle.BOX);cell_853.setBorder(Rectangle.BOX);
        cell_861.setBorder(Rectangle.BOX);cell_862.setBorder(Rectangle.BOX);cell_863.setBorder(Rectangle.BOX);
        cell_871.setBorder(Rectangle.BOX);cell_872.setBorder(Rectangle.BOX);cell_873.setBorder(Rectangle.BOX);
        cell_881.setBorder(Rectangle.BOX);cell_882.setBorder(Rectangle.BOX);cell_883.setBorder(Rectangle.BOX);
       
        contentPart8.addCell(cell_811);contentPart8.addCell(cell_812);contentPart8.addCell(cell_813);
        contentPart8.addCell(cell_821);contentPart8.addCell(cell_822);contentPart8.addCell(cell_823);
        contentPart8.addCell(cell_831);contentPart8.addCell(cell_832);contentPart8.addCell(cell_833);
        contentPart8.addCell(cell_841);contentPart8.addCell(cell_842);contentPart8.addCell(cell_843);
        contentPart8.addCell(cell_851);contentPart8.addCell(cell_852);contentPart8.addCell(cell_853);
        contentPart8.addCell(cell_861);contentPart8.addCell(cell_862);contentPart8.addCell(cell_863);
        contentPart8.addCell(cell_871);contentPart8.addCell(cell_872);contentPart8.addCell(cell_873);
        contentPart8.addCell(cell_881);contentPart8.addCell(cell_882);contentPart8.addCell(cell_883);
        
        document.add(contentPart8);
        document.add(addDefaultEmptyLine(1));
        
        
        Paragraph cptt4110 = new Paragraph("Ils ne peuvent en aucun cas excéder 35 heures par semaine.", attributeStyle);
        document.add(cptt4110);
        
        Paragraph cptt4111 = new Paragraph("Les stagiaires mineurs ne peuvent être présents dans l’entreprise avant six heures du matin et " + 
        		"après vingt deux heures du soir. Au-delà de quatre heures et demie d’activité, les stagiaires " + 
        		"mineurs doivent bénéficier d’une pause d’au moins trente minutes.", attributeStyle);
        cptt4111.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt4111);
        document.add(addDefaultEmptyLine(1));
        
        Paragraph art41 = new Paragraph("ARTICLE 5 :ABSENCES", noteGreyBoldStyle);
        document.add(art41);
        
        Paragraph art42 = new Paragraph("Pendant la durée du stage, l'étudiant stagiaire est autorisé à s'absenter pour suivre des cours dans l'établissement d'enseignement.", attributeStyle);
        art42.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art42);
        
        Paragraph art43 = new Paragraph("Les dates de ces cours devront être portées, à l'avance, à la connaissance du maître de stage.", attributeStyle);
        art43.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art43);
        document.add(addDefaultEmptyLine(1));
        
        
        Paragraph art51 = new Paragraph("ARTICLE 6 : RESPECT DU REGLEMENT INTERIEUR", noteGreyBoldStyle);
        document.add(art51);
        
        Paragraph art52 = new Paragraph("Le stagiaire demeure sous statut scolaire. Il reste sous l'autorité et la responsabilité du chef de l'établissement d'enseignement.", attributeStyle);
        art52.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art52);
        
        Paragraph art53 = new Paragraph("Cependant, pendant son stage, le stagiaire est tenu de respecter les conditions de fonctionnement " + 
        		"de l'entreprise d'accueil. Il est donc soumis au règlement intérieur de l'établissement d'accueil, notamment en matière d'hygiène, de sécurité et d'horaires.\r\n", attributeStyle);
        art53.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art53);
        
        Paragraph art54 = new Paragraph("\r\nLe stagiaire s'engage à :", attributeStyle);
        document.add(art54);
        Paragraph art541 = new Paragraph("- Réaliser sa mission et être disponible pour les tâches qui lui sont confiées.", attributeStyle);
        document.add(art541);
        Paragraph art542 = new Paragraph("- Respecter les règles de l'entreprise ainsi que ses codes et sa culture.", attributeStyle);
        document.add(art542);
        Paragraph art543 = new Paragraph("- Respecter les exigences de confidentialité fixées par l'entreprise.", attributeStyle);
        document.add(art543);
        
        Paragraph art55 = new Paragraph("\r\nDurant son stage l'étudiant stagiaire est soumis à la discipline de l'entreprise, notamment en ce qui concerne les visites médicales et les horaires.", attributeStyle);
        art55.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art55);
        
        Paragraph art56 = new Paragraph("Tout manquement à la discipline pourra entraîner la rupture du stage dans les conditions fixées à l'article 10.", attributeStyle);
        document.add(art56);
        
        Paragraph art57 = new Paragraph("\r\nLe stagiaire s'engage :", attributeStyle);
        document.add(art57);
        Paragraph art571 = new Paragraph("- À ne pas divulguer les informations recueillies par lui, sauf accord de l'entreprise.", attributeStyle);
        document.add(art571);
        Paragraph art572 = new Paragraph("- À ne pas faire de copie illicite des logiciels informatique s appartenant à l'entreprise ni implanter dans les systèmes internes à l'entreprise des logiciels de provenance externe.", attributeStyle);
        art572.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art572);
        document.add(addDefaultEmptyLine(1));
        
        Paragraph art71 = new Paragraph("ARTICLE 7 : GRATIFICATION ET AVANTAGES EN NATURE", noteGreyBoldStyle);
        document.add(art71);
        
        Paragraph art72 = new Paragraph("Le stage de formation ne s'effectue pas dans le cadre d'un contrat de travail. Le stagiaire ne "
        		+ "peut donc prétendre à un salaire de la part de l'entreprise qui l'accueille.\r\n\r\n\r\n\r\n\r\n", attributeStyle);
        art72.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art72);
        
        PdfPTable contentPart9 = new PdfPTable(2);
        contentPart9.setWidthPercentage(100);
        contentPart9.setWidths(new int[]{40, 900});
		
        PdfPCell cell_911 = new PdfPCell(new Phrase("7.1 ", noteGreyNormalStyle));
        cell_911.setLeading(5f, 1f);
        
        String parag921 = "Gratification \r\n• Si une gratification est prévue, d'un montant n'excédant pas le seuil de 15 % du plafond horaire de la Sécurité sociale multiplié par le nombre d'heures de travail :\r\n"
        		+ "Le stagiaire percevra une gratification dont le montant n'excédera pas 15% du plafond horaire de "
        		+ "la Sécurité sociale multiplié par le nombre d'heures de travail. Dans ce cadre, la gratification sera exonérée de "
        		+ "cotisations patronales et salariales.\r\n"
        		+ "• Si une gratification est prévue, d'un montant supérieur au seuil de 15 % du plafond horaire de la Sécurité sociale "
        		+ "multiplié par le nombre d'heures de travail :\r\n"
        		+ "Le stagiaire percevra une gratification d'un montant de .................. € brut / mois.\r\n"
        		+ "Dans ce cas, le calcul des cotisations sociales et contributions de sécurité sociale s'effectuera sur la partie "
        		+ "de la gratification excédant le seuil de 15 % du plafond horaire de la sécurité sociale multiplié par le nombre "
        		+ "d'heures de travail.\r\n"
        		+ "• Si aucune gratification n'est prévue à l'avance, et uniquement en cas de stage de moins de 3 mois consécutifs : l'entreprise se réserve la possibilité, en cas de stage satisfaisant, de rémunérer le stagiaire.";
        PdfPCell cell_922 = new PdfPCell(new Phrase(parag921, attributeStyle));
        cell_922.setLeading(5f, 1f);
        
        PdfPCell cell_931 = new PdfPCell(new Phrase("7.2 ", noteGreyNormalStyle));
        cell_931.setLeading(5f, 1f);
        String parag931 = "Avantages\r\n• S'il n'y a pas de prise en charge des frais autres que professionnels :\r\n"
        		+ "Les frais de transport, de nourriture et d'hébergement restent à la charge du stagiaire.\r\n"
        		+ "Néanmoins, les frais de déplacement et d'hébergement engagés par le stagiaire à la demande de "
        		+ "l'entreprise ainsi que les frais de formation éventuellement nécessités par le stage, seront intégralement pris "
        		+ "en charge par celle-ci.\r\n• S'il y a prise en charge de tous les frais :\r\n"
        		+ "L'entreprise devra rembourser le stagiaire, sur justificatifs, des divers frais occasionnés par l'activité qu'elle lui a "
        		+ "confiée. (indiquer la liste des avantages offerts, le cas échéant, par l'entreprise au stagiaire, notamment en ce qui concerne sa "
        		+ "restauration, son transport ou le remboursement des frais qu'il a engagés pour effectuer son stage.)";
        PdfPCell cell_952 = new PdfPCell(new Phrase(parag931, attributeStyle));
        cell_952.setLeading(5f, 1f);
        
        cell_922.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cell_952.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        
        cell_911.setBorder(Rectangle.NO_BORDER);cell_922.setBorder(Rectangle.NO_BORDER);
        cell_931.setBorder(Rectangle.NO_BORDER);cell_952.setBorder(Rectangle.NO_BORDER);
        
        contentPart9.addCell(cell_911);contentPart9.addCell(cell_922);
        contentPart9.addCell(cell_931);contentPart9.addCell(cell_952);
        
        document.add(contentPart9);
        document.add(addDefaultEmptyLine(1));
        
        
        
        Paragraph art81 = new Paragraph("ARTICLE 8 : COUVERTURE SOCIALE", noteGreyBoldStyle);
        document.add(art81);
        
        Paragraph art82 = new Paragraph("Le stagiaire doit être couvert contre les risques maladie-maternité, invalidité et accidents du travail.", attributeStyle);
        document.add(art82);
        
        Paragraph art83 = new Paragraph("Il est aussi bénéficiaire de la législation sur les accidents du travail et les maladies professionnelles en application de l'article L. 412-8 du code de la sécurité sociale.", attributeStyle);
        document.add(art83);
        
        Paragraph art84 = new Paragraph("\r\nDans ce cas, lorsque la gratification qu'il perçoit est égale ou inférieure au seuil de 15 % du " + 
        		"plafond horaire de la sécurité sociale multiplié par le nombre d'heures de travail, la cotisation " + 
        		"due au titre de la législation sur les accidents du travail et les maladies professionnelles est " + 
        		"prise en charge par l'établissement d'enseignement. Lorsque la gratification dépasse ce seuil, " + 
        		"le paiement des cotisations afférentes, sur cette fraction excédentaire, à la protection du " + 
        		"stagiaire, l'affiliation du stagiaire et la déclaration des accidents du travail ou de maladies " + 
        		"professionnelles à la caisse d'assurance maladie du lieu de résidence du stagiaire incombent à l'entreprise d'accueil.", attributeStyle);
        art84.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art84);
        
        Paragraph art85 = new Paragraph("\r\nEn cas d'accident survenant, soit au cours du travail, soit au cours du trajet, le responsable de " + 
        		"l'entreprise s'engage à adresser la déclaration d'accident au chef de l'établissement " + 
        		"d'enseignement dans la journée où l'accident s'est produit ou au plus tard dans les 24 heures.", attributeStyle);
        art85.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art85);
        
        Paragraph art86 = new Paragraph("\r\nLa déclaration du chef de l'établissement d'enseignement ou d'un de ses préposés doit être " + 
        		"faite par lettre recommandée à la caisse primaire d'assurance maladie dont relève " + 
        		"l'établissement, avec demande d'avis de réception, dans les 48 heures non compris les dimanches et jours fériés.", attributeStyle);
        art86.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art86);
        document.add(addDefaultEmptyLine(1));
        
        Paragraph art91 = new Paragraph("ARTICLE 9 : RESPONSABILITE CIVILE", noteGreyBoldStyle);
        document.add(art91);
        
        Paragraph art92 = new Paragraph("Le stagiaire et l’employeur doivent avoir souscrit l’un et l’autre une assurance « responsabilité civile » auprès d’un organisme d’assurance de leur choix.", attributeStyle);
        art92.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art92);
        
        Paragraph art93 = new Paragraph("\r\nResponsabilité civile : Le chef d'entreprise prend les dispositions nécessaires pour garantir sa responsabilité civile chaque fois qu'elle sera engagée.\r\n", attributeStyle);
        art93.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(art93);
        
        
        PdfPTable contentPart10 = new PdfPTable(2);
        contentPart10.setWidthPercentage(100);
        contentPart10.setWidths(new int[]{25, 975});
		
        PdfPCell cell_1011 = new PdfPCell(new Phrase("• ", attributeStyle));
        cell_1011.setLeading(5f, 1f);
        String parag1012 = "En cas de souscription d'une assurance particulière :\r\n" + 
                           "en souscrivant une assurance particulière garantissant sa responsabilité civile à l'égard du stagiaire.";
        PdfPCell cell_1012 = new PdfPCell(new Phrase(parag1012, attributeStyle));
        cell_1012.setLeading(5f, 1f);
        
        PdfPCell cell_1021 = new PdfPCell(new Phrase("• ", attributeStyle));
        cell_1021.setLeading(5f, 1f);
        String parag1022 = "En cas de simple conclusion d'un avenant au contrat d'assurance de l'entreprise ou l'organisme :\r\n" + 
                           "en ajoutant au contrat d'assurance « responsabilité civile entreprise » ou « responsabilité civile professionnelle » "
                           + "déjà souscrit un avenant relatif au stagiaire.\r\n"
                           + "Le stagiaire certifie qu'il possède une assurance couvrant sa responsabilité civile individuelle pendant "
                           + "la durée de son stage, contractée auprès de ............. (nom de la Compagnie d'assurance ou de la Mutuelle).\r\n"
                           + "Cependant, le chef de l'établissement d'enseignement peut contracter une assurance couvrant la responsabilité civile de l'élève pour les dommages qu'il pourrait causer pendant la durée ou à l'occasion de son stage dans l'entreprise.";
        PdfPCell cell_1022 = new PdfPCell(new Phrase(parag1022, attributeStyle));
        cell_1022.setLeading(5f, 1f);
        
        cell_1012.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cell_1022.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        
        cell_1011.setBorder(Rectangle.NO_BORDER);cell_1012.setBorder(Rectangle.NO_BORDER);
        cell_1021.setBorder(Rectangle.NO_BORDER);cell_1022.setBorder(Rectangle.NO_BORDER);
         
        contentPart10.addCell(cell_1011);contentPart10.addCell(cell_1012);
        contentPart10.addCell(cell_1021);contentPart10.addCell(cell_1022);
        
        document.add(contentPart10);
        document.add(addDefaultEmptyLine(1));
        
        
        Paragraph art101 = new Paragraph("ARTICLE 10: INTERRUPTION, RUPTURE", noteGreyBoldStyle);
        document.add(art101);
        
        PdfPTable contentPart11 = new PdfPTable(2);
        contentPart11.setWidthPercentage(100);
        contentPart11.setWidths(new int[]{50, 950});
		
        PdfPCell cell_1111 = new PdfPCell(new Phrase("10.1 ", noteGreyNormalStyle));
        cell_1111.setLeading(5f, 1f);
        String parag1112 = "Rupture à l'initiative du stagiaire : Le stagiaire peut rompre la convention de stage après avoir informé de sa décision son maître de stage ainsi que le responsable pédagogique.";
        PdfPCell cell_1112 = new PdfPCell(new Phrase(parag1112, attributeStyle));
        cell_1112.setLeading(5f, 1f);
        
        PdfPCell cell_1121 = new PdfPCell(new Phrase("10.2 ", noteGreyNormalStyle));
        cell_1121.setLeading(5f, 1f);
        String parag1122 = "Suspension ou rupture pour raisons médicales : Le stage peut être suspendu ou interrompu pour raisons médicales. Dans ce cas, un avenant comportant les aménagements requis ou la rupture de la convention de stage sera conclu.";
        PdfPCell cell_1122 = new PdfPCell(new Phrase(parag1122, attributeStyle));
        cell_1122.setLeading(5f, 1f);
        
        PdfPCell cell_1131 = new PdfPCell(new Phrase("10.3 ", noteGreyNormalStyle));
        cell_1131.setLeading(5f, 1f);
        String parag1132 = "Rupture pour manquement à la discipline : En cas de manquement à la discipline de l'entreprise par le stagiaire, le chef d'entreprise se réserve le droit de mettre fin au stage après en avoir informé le responsable de l'établissement d'enseignement.";
        PdfPCell cell_1132 = new PdfPCell(new Phrase(parag1132, attributeStyle));
        cell_1132.setLeading(5f, 1f);
        
        cell_1112.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cell_1122.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cell_1132.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        
        cell_1111.setBorder(Rectangle.NO_BORDER);cell_1112.setBorder(Rectangle.NO_BORDER);
        cell_1121.setBorder(Rectangle.NO_BORDER);cell_1122.setBorder(Rectangle.NO_BORDER);
        cell_1131.setBorder(Rectangle.NO_BORDER);cell_1132.setBorder(Rectangle.NO_BORDER);
        
        contentPart11.addCell(cell_1111);contentPart11.addCell(cell_1112);
        contentPart11.addCell(cell_1121);contentPart11.addCell(cell_1122);
        contentPart11.addCell(cell_1131);contentPart11.addCell(cell_1132);
        
        document.add(contentPart11);
        document.add(addDefaultEmptyLine(1));
        
        
        Paragraph art111 = new Paragraph("ARTICLE 11 : EVALUATION DU STAGE", noteGreyBoldStyle);
        document.add(art111);
        
        Paragraph art112 = new Paragraph("A l’issue du stage :", attributeStyle);
        document.add(art112);
        
        PdfPTable contentPart12 = new PdfPTable(2);
        contentPart12.setWidthPercentage(100);
        contentPart12.setWidths(new int[]{25, 950});
		
        PdfPCell cell_1211 = new PdfPCell(new Phrase("- ", attributeStyle));
        cell_1211.setLeading(5f, 1f);
        String parag1212 = "le stagiaire est tenu de fournir à l’établissement d’enseignement un rapport de stage dont une copie est communiquée à l’entreprise d’accueil.";
        PdfPCell cell_1212 = new PdfPCell(new Phrase(parag1212, attributeStyle));
        cell_1212.setLeading(5f, 1f);
        
        PdfPCell cell_1221 = new PdfPCell(new Phrase("- ", attributeStyle));
        cell_1221.setLeading(5f, 1f);
        String parag1222 = "le chef d’entreprise délivre à l’intéressé une attestation de stage.\r\n" + 
                           "Cette attestation précise les progrès réalisés au regard des objectifs initiaux et les compétences acquises au cours du stage.";
        PdfPCell cell_1222 = new PdfPCell(new Phrase(parag1222, attributeStyle));
        cell_1222.setLeading(5f, 1f);
        
        cell_1212.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cell_1222.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        
        cell_1211.setBorder(Rectangle.NO_BORDER);cell_1212.setBorder(Rectangle.NO_BORDER);
        cell_1221.setBorder(Rectangle.NO_BORDER);cell_1222.setBorder(Rectangle.NO_BORDER);
        
        contentPart12.addCell(cell_1211);contentPart12.addCell(cell_1212);
        contentPart12.addCell(cell_1221);contentPart12.addCell(cell_1222);
        
        document.add(contentPart12);
        document.add(addDefaultEmptyLine(2));
        
        String rss = "M. Majdi GHARBI\r\n"
	        	   + "Responsable Services Stages - ESPRIT\r\n";
        Paragraph art113 = new Paragraph(rss, noteStyle12);
        document.add(art113);
        
        
        
	    Paragraph art114 = new Paragraph("Tunis, le " + formattedDate.format(new Date()), attributeStyle);
	    document.add(art114);
	    document.add(addDefaultEmptyLine(5));
        
        
        // art114   art115   art116   art117   art118
        PdfPTable contentPart7 = new PdfPTable(2);
        contentPart7.setWidthPercentage(100);
        contentPart7.setWidths(new int[]{500, 500});
		
        PdfPCell cellSign_11 = new PdfPCell(new Phrase("Pour l'Étudiant", noteStyle12));
        PdfPCell cellSign_12 = new PdfPCell(new Phrase("Pour l'Organisme d'Accueil", noteStyle12));
       
        PdfPCell cellSign_21 = new PdfPCell(new Phrase("(Nom et Signature)", attributeResponseStyle));
        PdfPCell cellSign_22 = new PdfPCell(new Phrase("(Nom et Signature du Représentant + Cachet de l'Entreprise)", attributeResponseStyle));
        
        PdfPCell cellSign_31 = new PdfPCell(new Phrase(scf.getFullName(), attributeStyle));
        PdfPCell cellSign_32 = new PdfPCell(new Phrase(conv.get().getResponsable(), attributeStyle));
        
        PdfPCell cellSign_41 = new PdfPCell(new Phrase("Le ..................................................", attributeStyle));
        PdfPCell cellSign_42 = new PdfPCell(new Phrase("Le ..................................................", attributeStyle));
        
        cellSign_11.setBorder(Rectangle.NO_BORDER);cellSign_12.setBorder(Rectangle.NO_BORDER);
        cellSign_21.setBorder(Rectangle.NO_BORDER);cellSign_22.setBorder(Rectangle.NO_BORDER);
        cellSign_31.setBorder(Rectangle.NO_BORDER);cellSign_32.setBorder(Rectangle.NO_BORDER);
        cellSign_41.setBorder(Rectangle.NO_BORDER);cellSign_42.setBorder(Rectangle.NO_BORDER);
       
        contentPart7.addCell(cellSign_11);contentPart7.addCell(cellSign_12);
        contentPart7.addCell(cellSign_21);contentPart7.addCell(cellSign_22);
        contentPart7.addCell(cellSign_31);contentPart7.addCell(cellSign_32);
        contentPart7.addCell(cellSign_41);contentPart7.addCell(cellSign_42);
        
        document.add(contentPart7);
        
    }

    private void addMetaData(Document document)
    {
        document.addTitle("Convention de Stage");
        document.addSubject("PFE");
        document.addKeywords("Stage, PFE, Convention");
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
		table.setWidths(new int[]{300, 500, 80, 130});
		
        PdfPCell cell1 = new PdfPCell(new Phrase(""));
        PdfPCell cell2 = new PdfPCell(new Phrase("\r\n " + "Convention de Stage", titleStyle));
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
    
    
}
