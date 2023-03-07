package com.esprit.gdp.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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


public class ConventionTN_PDF
{
	
	BaseColor myRedColor = new BaseColor(204, 0, 0);
	BaseColor myTableGreyColor1 = new BaseColor(179, 179, 179);
    BaseColor myTableGreyColor2 = new BaseColor(204, 204, 204);
    BaseColor myTableGreyColor3 = new BaseColor(230, 230, 230);
    BaseColor myGreyColor = new BaseColor(102, 102, 102);
    
    Font titleStyle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font nbrConvStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, myRedColor);
    Font noteStyle12 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    Font noteGreyBoldStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myGreyColor);
    Font noteGreyNormalStyle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeStyleBold = new Font(Font.FontFamily.HELVETICA, Font.BOLD, 11);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.BLACK);
    
    Font boldGrayField = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.GRAY);
    
    
    public ConventionTN_PDF(Optional<Convention> conv, String path, String studentFullName, String studentOption, String studentDepartment)
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
	    	addContent(document, conv, studentFullName, studentOption, studentDepartment);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, Optional<Convention> conv, String studentFullName, String studentOption, String studentDepartment) throws DocumentException, MalformedURLException, IOException
    {
    	
    	DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
    	document.add(addDefaultEmptyLine(1));
    	
    	Paragraph cpttl = new Paragraph("Entre :", noteGreyBoldStyle);
    	addEmptyLine(cpttl, 1);
        document.add(cpttl);
        
        // Add BreakLine
        addEmptyLine(cpttl, 1);
        
        
        // Number 1
    	PdfPTable contentPart1 = new PdfPTable(2);
		contentPart1.setWidthPercentage(100);
		contentPart1.setWidths(new int[]{25, 900});
		
		PdfPCell cp1cella_11 = new PdfPCell(new Phrase("1- ", noteGreyBoldStyle));
        PdfPCell cp1cella_12 = new PdfPCell(new Phrase("L’ÉCOLE SUPERIEURE PRIVÉE D'INGENIERIE ET DE TECHNOLOGIES , ESPRIT", attributeStyle));
        PdfPCell cp1cella_21 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cella_22 = new PdfPCell(new Phrase("Représentée par son Directeur Fondateur Pr. Tahar Benlakhdar ", attributeStyle));
        PdfPCell cp1cella_31 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cella_32 = new PdfPCell(new Phrase("Ci-après dénommée ESPRIT,", attributeStyle));
        
        cp1cella_11.setBorder(Rectangle.NO_BORDER);cp1cella_12.setBorder(Rectangle.NO_BORDER);
        cp1cella_21.setBorder(Rectangle.NO_BORDER);cp1cella_22.setBorder(Rectangle.NO_BORDER);
        cp1cella_31.setBorder(Rectangle.NO_BORDER);cp1cella_32.setBorder(Rectangle.NO_BORDER);
        
        contentPart1.addCell(cp1cella_11);contentPart1.addCell(cp1cella_12);
        contentPart1.addCell(cp1cella_21);contentPart1.addCell(cp1cella_22);
        contentPart1.addCell(cp1cella_31);contentPart1.addCell(cp1cella_32);
        
        document.add(contentPart1);
        //document.add(addDefaultEmptyLine(1));
        
        
        // Number 2
        /* Number 2.1 */
        PdfPTable contentPart21 = new PdfPTable(3);
        contentPart21.setWidthPercentage(100);
        contentPart21.setWidths(new int[]{30, 200, 740});
		
        PdfPCell cp1cellz_11 = new PdfPCell(new Phrase("2- ", noteGreyBoldStyle));
        PdfPCell cp1cellz_12 = new PdfPCell(new Phrase("La Société :", attributeStyle));
        PdfPCell cp1cellz_13 = new PdfPCell(new Phrase(conv.get().getEntrepriseAccueilConvention().getDesignation(), attributeStyle));
        
        PdfPCell cp1cellz_21 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_22 = new PdfPCell(new Phrase("Adresse :", attributeStyle));
        PdfPCell cp1cellz_23 = new PdfPCell(new Phrase(conv.get().getAddress(), attributeStyle));
        
        PdfPCell cp1cellz_31 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_32 = new PdfPCell(new Phrase("Représenté(e) par :", attributeStyle));
        PdfPCell cp1cellz_33 = new PdfPCell(new Phrase(conv.get().getResponsable(), attributeStyle));
        
        PdfPCell cp1cellz_41 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_42 = new PdfPCell(new Phrase("Adresse Électronique :", attributeStyle));
        PdfPCell cp1cellz_43 = new PdfPCell(new Phrase(conv.get().getMail(), attributeStyle));
        
        cp1cellz_11.setBorder(Rectangle.NO_BORDER);cp1cellz_12.setBorder(Rectangle.NO_BORDER);cp1cellz_13.setBorder(Rectangle.NO_BORDER);
        cp1cellz_21.setBorder(Rectangle.NO_BORDER);cp1cellz_22.setBorder(Rectangle.NO_BORDER);cp1cellz_23.setBorder(Rectangle.NO_BORDER);
        cp1cellz_31.setBorder(Rectangle.NO_BORDER);cp1cellz_32.setBorder(Rectangle.NO_BORDER);cp1cellz_33.setBorder(Rectangle.NO_BORDER);
        cp1cellz_41.setBorder(Rectangle.NO_BORDER);cp1cellz_42.setBorder(Rectangle.NO_BORDER);cp1cellz_43.setBorder(Rectangle.NO_BORDER);
        
        contentPart21.addCell(cp1cellz_11);contentPart21.addCell(cp1cellz_12);contentPart21.addCell(cp1cellz_13);
        contentPart21.addCell(cp1cellz_21);contentPart21.addCell(cp1cellz_22);contentPart21.addCell(cp1cellz_23);
        contentPart21.addCell(cp1cellz_31);contentPart21.addCell(cp1cellz_32);contentPart21.addCell(cp1cellz_33);
        contentPart21.addCell(cp1cellz_41);contentPart21.addCell(cp1cellz_42);contentPart21.addCell(cp1cellz_43);
        
        document.add(contentPart21);
        
        /* Number 2.2 */
    	PdfPTable contentPart22 = new PdfPTable(2);
    	contentPart22.setWidthPercentage(100);
    	contentPart22.setWidths(new int[]{25, 900});
    		
    	PdfPCell cp1celle_11 = new PdfPCell(new Phrase(""));
        PdfPCell cp1celle_12 = new PdfPCell(new Phrase("Ci-après dénommé(e) l’Entreprise,", attributeStyle));
            
        cp1celle_11.setBorder(Rectangle.NO_BORDER);cp1celle_12.setBorder(Rectangle.NO_BORDER);
            
        contentPart22.addCell(cp1celle_11);contentPart22.addCell(cp1celle_12);
            
        document.add(contentPart22);
        //document.add(addDefaultEmptyLine(1));

        
        // Number 3
        PdfPTable contentPart32 = new PdfPTable(3);
        contentPart32.setWidthPercentage(100);
        contentPart32.setWidths(new int[]{30, 460, 480});
        
        PdfPCell cp1cellr_11 = new PdfPCell(new Phrase("3- ", noteGreyBoldStyle));
        PdfPCell cp1cellr_12 = new PdfPCell(new Phrase("L’Étudiant (e) ", attributeStyle));
        PdfPCell cp1cellr_13 = new PdfPCell(new Phrase(studentFullName, attributeStyle));
        
    	PdfPCell cp1cellt_11 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellt_12 = new PdfPCell(new Phrase("Inscrit(e) à ESPRIT en troisième année option :", attributeStyle));
        PdfPCell cp1cellt_13 = new PdfPCell(new Phrase(studentOption.replaceAll("_01", ""), attributeStyle));
        
        PdfPCell cp1cellt_21 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellt_22 = new PdfPCell(new Phrase("En vue d’obtenir le Diplôme National d’Ingénieur en :", attributeStyle));
        PdfPCell cp1cellt_23 = new PdfPCell(new Phrase(studentDepartment, attributeStyle));
        
        cp1cellr_11.setBorder(Rectangle.NO_BORDER);cp1cellr_12.setBorder(Rectangle.NO_BORDER);cp1cellr_13.setBorder(Rectangle.NO_BORDER);
        cp1cellt_11.setBorder(Rectangle.NO_BORDER);cp1cellt_12.setBorder(Rectangle.NO_BORDER);cp1cellt_13.setBorder(Rectangle.NO_BORDER);
        cp1cellt_21.setBorder(Rectangle.NO_BORDER);cp1cellt_22.setBorder(Rectangle.NO_BORDER);cp1cellt_23.setBorder(Rectangle.NO_BORDER);
         
        contentPart32.addCell(cp1cellr_11);contentPart32.addCell(cp1cellr_12);contentPart32.addCell(cp1cellr_13);
        contentPart32.addCell(cp1cellt_11);contentPart32.addCell(cp1cellt_12);contentPart32.addCell(cp1cellt_13);
        contentPart32.addCell(cp1cellt_21);contentPart32.addCell(cp1cellt_22);contentPart32.addCell(cp1cellt_23);
            
        document.add(contentPart32);
        
        /* Number 3.3 */
    	PdfPTable contentPart33 = new PdfPTable(2);
    	contentPart33.setWidthPercentage(100);
    	contentPart33.setWidths(new int[]{30, 930});
    		
    	PdfPCell cp1celly_11 = new PdfPCell(new Phrase(""));
        PdfPCell cp1celly_12 = new PdfPCell(new Phrase("Ci-après dénommé l’Étudiant,", attributeStyle));
            
        cp1celly_11.setBorder(Rectangle.NO_BORDER);cp1celly_12.setBorder(Rectangle.NO_BORDER);
            
        contentPart33.addCell(cp1celly_11);contentPart33.addCell(cp1celly_12);
            
        document.add(contentPart33);
        
        document.add(addDefaultEmptyLine(1));
        
        
        // Number 4
        Paragraph cptt2 = new Paragraph("Il a été arrêté et convenu ce qui suit :", noteGreyBoldStyle);
        document.add(cptt2);
        document.add(addDefaultEmptyLine(1));
        
        /* Number 4.1 */
        Paragraph cptt31 = new Paragraph("ARTICLE 1 : Objet de la Convention", noteGreyNormalStyle);
        document.add(cptt31);
        addEmptyLine(cptt31, 1);
       
        Paragraph cptt32 = new Paragraph("La présente convention règle les rapports entre ESPRIT, l’Entreprise et l’Étudiant "
						        	   + "pour ce qui concerne le Projet de Fin d’Etudes (ci-après désigné par « PFE ») que "
						        	   + "l’Étudiant est appelé à effectuer au sein de l’Entreprise.", attributeStyle);
        cptt32.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt32);
        addEmptyLine(cptt32, 1);
        document.add(addDefaultEmptyLine(1));
        
        /* Number 4.2 */
        Paragraph cptt41 = new Paragraph("ARTICLE 2 : Objet du PFE", noteGreyNormalStyle);
        document.add(cptt41);
        addEmptyLine(cptt41, 1);
       
        Paragraph cptt42 = new Paragraph("Le PFE a pour objet essentiel l'application pratique de l'enseignement dispensé à ESPRIT, il "
        		+ "s'effectue sous la direction d'un Encadrant Technique de l'Entreprise et d'un Encadrant Pédagogique d’ESPRIT.", attributeStyle);
        cptt42.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt42);
        addEmptyLine(cptt42, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 4.3 */
        Paragraph cptt51 = new Paragraph("ARTICLE 3 : Programme", noteGreyNormalStyle);
        document.add(cptt51);
        addEmptyLine(cptt51, 1);
       
        Paragraph cptt52 = new Paragraph("Le programme du PFE, fera l’objet d’une étude par le comité de validation d’ESPRIT en "
        		                       + "concertation avec l’Encadrant de lEntreprise.", attributeStyle);
        cptt52.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt52);
        addEmptyLine(cptt52, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 4.4 */
        Paragraph cptt61 = new Paragraph("ARTICLE 4 : Statut de l’Étudiant", noteGreyNormalStyle);
        document.add(cptt61);
        addEmptyLine(cptt61, 1);
       
        Paragraph cptt62 = new Paragraph("Pendant la durée de son séjour en Entreprise, l’Étudiant conserve son statut d'Étudiant."
        		                       + "Toutefois Il doit se conformer au règlement intérieur de l’Entreprise et à ses règles d’usage.", attributeStyle);
        cptt62.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt62);
        addEmptyLine(cptt62, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 4.5 */
        Paragraph cptt71 = new Paragraph("ARTICLE 5 : Résiliation", noteGreyNormalStyle);
        document.add(cptt71);
        addEmptyLine(cptt71, 1);
        
        /* Number 4.5.1 */
        PdfPTable contentPart451 = new PdfPTable(2);
        contentPart451.setWidthPercentage(100);
        contentPart451.setWidths(new int[]{35, 900});
    	PdfPCell cp1cellu_11 = new PdfPCell(new Phrase("5.1 ", noteGreyNormalStyle));
    	cp1cellu_11.setLeading(5f, 1f);
        PdfPCell cp1cellu_12 = new PdfPCell(new Phrase("Rupture à l'initiative du Stagiaire \r\n"
        		+ "Le Stagiaire peut rompre la convention de stage après avoir obtenu l'accord express et non équivoque du maître de stage et informé le Responsable des Stages d'Esprit.", attributeStyle));
        cp1cellu_12.setLeading(5f, 1f);
        cp1cellu_12.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cp1cellu_11.setBorder(Rectangle.NO_BORDER);cp1cellu_12.setBorder(Rectangle.NO_BORDER);
        contentPart451.addCell(cp1cellu_11);contentPart451.addCell(cp1cellu_12);       
        document.add(contentPart451);
        
        /* Number 4.5.2 */
        PdfPTable contentPart453 = new PdfPTable(2);
        contentPart453.setWidthPercentage(100);
        contentPart453.setWidths(new int[]{35, 900});
    	PdfPCell cp1cello_11 = new PdfPCell(new Phrase("5.2 ", noteGreyNormalStyle));
    	cp1cello_11.setLeading(5f, 1f);
        PdfPCell cp1cello_12 = new PdfPCell(new Phrase("Suspension ou Rupture pour raisons médicales \r\nLe stage peut être suspendu ou interrompu pour raisons médicales. "
        		                                     + "Dans ce cas, un avenant comportant les aménagements requis où la rupture de "
        		                                     + "la convention de stage sera conclu.", attributeStyle));
        cp1cello_12.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cp1cello_12.setLeading(5f, 1f);
        cp1cello_11.setBorder(Rectangle.NO_BORDER);cp1cello_12.setBorder(Rectangle.NO_BORDER);
        contentPart453.addCell(cp1cello_11);contentPart453.addCell(cp1cello_12);
        document.add(contentPart453);
       
        /* Number 4.5.3 */
        PdfPTable contentPart455 = new PdfPTable(2);
        contentPart455.setWidthPercentage(100);
        contentPart455.setWidths(new int[]{35, 900});
    	PdfPCell cp1cellq_11 = new PdfPCell(new Phrase("5.3 ", noteGreyNormalStyle));
    	cp1cellq_11.setLeading(5f, 1f);
        PdfPCell cp1cellq_12 = new PdfPCell(new Phrase("Rupture pour manquement à la discipline\r\nEn cas de manquement à la discipline de l'Entreprise par le Stagiaire, le Chef d'Entreprise "
        		+ "se réserve le droit de mettre fin au stage après en avoir informé le Responsable de l'Établissement d'Enseignement.", attributeStyle));
        cp1cellq_12.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cp1cellq_12.setLeading(5f, 1f);
        cp1cellq_11.setBorder(Rectangle.NO_BORDER);cp1cellq_12.setBorder(Rectangle.NO_BORDER);        
        contentPart455.addCell(cp1cellq_11);contentPart455.addCell(cp1cellq_12);       
        document.add(contentPart455);
        //document.add(addDefaultEmptyLine(1));
        
        
        /* Number 6 */
        Paragraph cptt81 = new Paragraph("\r\nARTICLE 6 : Protection Sociale", noteGreyNormalStyle);
        document.add(cptt81);
        addEmptyLine(cptt81, 1);
       
        Paragraph cptt82 = new Paragraph("L’Étudiant conserve sa protection sociale dans le cadre du contrat d’assurance "
        		+ "souscrit par ESPRIT au profit de ses étudiants. En cas d'accident dans l'Entreprise ou au cours du "
        		+ "trajet, l'Entreprise informe immédiatement le Service de Scolarité d’ESPRIT.", attributeStyle);
        cptt82.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt82);
        addEmptyLine(cptt82, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 7 */
        Paragraph cptt91 = new Paragraph("ARTICLE 7 : Évaluation", noteGreyNormalStyle);
        document.add(cptt91);
        addEmptyLine(cptt91, 1);
       
        Paragraph cptt92 = new Paragraph("Les modalités de validation du PFE sont définies dans le cadre des modalités "
        		+ "de contrôle des connaissances. L'activité de l'Étudiant fera l'objet d'une évaluation. Une fiche "
        		+ "d’évaluation est remplie par l’Organisme d’Accueil et le Responsable Pédagogique et remise à ESPRIT.", attributeStyle);
        cptt92.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt92);
        addEmptyLine(cptt92, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 8*/
        Paragraph cptt101 = new Paragraph("ARTICLE 8 : Rrapport de Stage", noteGreyNormalStyle);
        document.add(cptt101);
        addEmptyLine(cptt101, 1);
       
        Paragraph cptt102 = new Paragraph("À la fin du stage, l’Étudiant déposera à l’Entreprise un rapport de stage. "
        		+ "De même l’Étudiant doit déposer à ESPRIT un rapport de stage visé par l’Entreprise et le Responsable "
        		+ "Pédagogique qui aura assuré le suivi de l'Étudiant.", attributeStyle);
        cptt102.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt102);
        
        Paragraph cptt103 = new Paragraph("Le rapport de stage fera l'objet d'une soutenance orale devant un jury et d'une notation. "
        		+ "Cette soutenance est publique sauf dérogation pour cause de confidentialité, sur demande motivée du "
        		+ "Responsable de l'Entreprise." + 
        		"À la fin du stage, l’Entreprise délivrera une attestation à l’Étudiant précisant la nature du PFE "
        		+ "et sa durée.", attributeStyle);
        cptt103.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt103);
        addEmptyLine(cptt103, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 9 */
        Paragraph cptt111 = new Paragraph("ARTICLE 9 : Confidentialité", noteGreyNormalStyle);
        document.add(cptt111);
        addEmptyLine(cptt111, 1);
       
        Paragraph cptt112 = new Paragraph("L’Étudiant sera tenu, aussi bien pendant la durée du PFE qu'après celui-ci, "
        		+ "à observer le secret professionnel à l'égard des tiers pour tout ce qui concerne son activité pendant "
        		+ "son PFE et d'une façon plus générale pour tout ce dont il aurait eu connaissance directement ou "
        		+ "indirectement à l'occasion de celui-ci, le contenu des documents qu'il aurait rédigés lui-même, y "
        		+ "compris le rapport du PFE, ou qui pourrait lui être remis par l'Entreprise restant bien entendu la "
        		+ "propriété de celle-ci.", attributeStyle);
        cptt112.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt112);
        addEmptyLine(cptt112, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 10 */
        Paragraph cptt121 = new Paragraph("ARTICLE 10 : Consentement", noteGreyNormalStyle);
        document.add(cptt121);
        addEmptyLine(cptt121, 1);
       
        Paragraph cptt122 = new Paragraph("La présente convention est préalablement portée à la connaissance de l’Étudiant "
        		+ "ou de son Représentant légal, s'il est mineur, pour consentement express relatif aux clauses "
        		+ "ci-dessus énoncées.", attributeStyle);
        cptt122.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt122);
        addEmptyLine(cptt122, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 11 */
        Paragraph cptt131 = new Paragraph("ARTICLE 11 : Organisation du PFE", noteGreyNormalStyle);
        document.add(cptt131);
        addEmptyLine(cptt131, 1);
       
        String convsDD = formattedDate.format(conv.get().getDateDebut());
		String convsDF = formattedDate.format(conv.get().getDateFin());
		
		Paragraph azertyy = new Paragraph("");
		azertyy.add(new Phrase("Le PFE se déroulera selon l’organisation suivante :\r\n", attributeStyle));
		azertyy.add(new Phrase("- Dates de PFE : Le PFE aura lieu du ", attributeStyle));
        azertyy.add(new Phrase(convsDD, boldGrayField));
        azertyy.add(new Phrase(" au ", attributeStyle));
        azertyy.add(new Phrase(convsDF, boldGrayField));
        azertyy.add(new Phrase(" et expirera au plus tard à la fin de l'année universitaire en cours. En cas de prolongation pour raison exceptionnelle réalisée avant l'obtention du diplôme et au delà des dates initialement prévues, un avenant devra être établi et signé par les parties contractantes.\r\n", attributeStyle));
        azertyy.add(new Phrase("- Adresse de PFE : Le PFE se déroulera à l'adresse de l’Entreprise.", attributeStyle));
        azertyy.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(azertyy);
        
        document.add(addDefaultEmptyLine(1));
        
        PdfPTable contentPartNb112 = new PdfPTable(2);
        contentPartNb112.setWidthPercentage(100);
        contentPartNb112.setWidths(new int[]{350, 600});
    	PdfPCell cp1cellf_11 = new PdfPCell(new Phrase("Département : ", attributeStyle));
        PdfPCell cp1cellf_12 = new PdfPCell(new Phrase(" ........................................................."));
        PdfPCell cp1cellf_21 = new PdfPCell(new Phrase("Service : ", attributeStyle));
        PdfPCell cp1cellf_22 = new PdfPCell(new Phrase(" ........................................................."));
        PdfPCell cp1cellf_31 = new PdfPCell(new Phrase("Encadrant Technique dans l'Entreprise : ", attributeStyle));
        PdfPCell cp1cellf_32 = new PdfPCell(new Phrase(" ........................................................."));
        PdfPCell cp1cellf_41 = new PdfPCell(new Phrase("Encadrant Pédagogique d’ESPRIT : ", attributeStyle));
        PdfPCell cp1cellf_42 = new PdfPCell(new Phrase(" ........................................................."));
        cp1cellf_11.setBorder(Rectangle.NO_BORDER);cp1cellf_12.setBorder(Rectangle.NO_BORDER);
        cp1cellf_21.setBorder(Rectangle.NO_BORDER);cp1cellf_22.setBorder(Rectangle.NO_BORDER);
        cp1cellf_31.setBorder(Rectangle.NO_BORDER);cp1cellf_32.setBorder(Rectangle.NO_BORDER);
        cp1cellf_41.setBorder(Rectangle.NO_BORDER);cp1cellf_42.setBorder(Rectangle.NO_BORDER);
        contentPartNb112.addCell(cp1cellf_11);contentPartNb112.addCell(cp1cellf_12);
        contentPartNb112.addCell(cp1cellf_21);contentPartNb112.addCell(cp1cellf_22);
        contentPartNb112.addCell(cp1cellf_31);contentPartNb112.addCell(cp1cellf_32);
        contentPartNb112.addCell(cp1cellf_41);contentPartNb112.addCell(cp1cellf_42);
        document.add(contentPartNb112);
        document.add(addDefaultEmptyLine(1));
        
        PdfPTable signatureTBL = new PdfPTable(5);
        signatureTBL.setWidthPercentage(100);    // table.setTotalWidth(1000);
        signatureTBL.setWidths(new int[]{300, 20, 300, 20, 300});
        
        String signDrPath = "C:\\ESP-DOCS\\Logos\\conventionSign.png";
        Image signDrIcon = Image.getInstance(signDrPath);
        signDrIcon.scaleAbsolute(130f, 57f);
        
        Phrase ph = new Phrase("");
        ph.add(new Phrase("Directeur de l’Entreprise \r\n ou Organisme d'Accueil\r\n", noteStyle12));
        ph.add(new Phrase("(Nom et Signature du Representant \r\n+ Cachet de l'Entreprise)", attributeResponseStyle)); 
        PdfPCell cell_11 = new PdfPCell(ph);horizentalAndVerticalAndHeightCellCenter(cell_11, 50f);cell_11.setBackgroundColor(myTableGreyColor1);
        PdfPCell cell_12 = new PdfPCell(new Phrase(""));
        PdfPCell cell_13 = new PdfPCell(new Phrase("Directeur d'ESPRIT \r\n P/Pr. Tahar Benlakhdar", noteStyle12));horizentalAndVerticalAndHeightCellCenter(cell_13, 40f);cell_13.setBackgroundColor(myTableGreyColor1);
        PdfPCell cell_14 = new PdfPCell(new Phrase(""));
        PdfPCell cell_15 = new PdfPCell(new Phrase("Étudiant\r\n" + studentFullName, noteStyle12));horizentalAndVerticalAndHeightCellCenter(cell_15, 40f);cell_15.setBackgroundColor(myTableGreyColor1);

        PdfPCell cell_21 = new PdfPCell(new Phrase("À ........................ le ........................", noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cell_21, 12f);cell_21.setBackgroundColor(myTableGreyColor2);
        PdfPCell cell_22 = new PdfPCell(new Phrase(""));
        PdfPCell cell_23 = new PdfPCell(new Phrase("À Tunis le " + formattedDate.format(new Date()), noteStyle12)); horizentalAndVerticalCellCenter(cell_23);cell_23.setBackgroundColor(myTableGreyColor2);
        PdfPCell cell_24 = new PdfPCell(new Phrase(""));
        PdfPCell cell_25 = new PdfPCell(new Phrase("À ........................ le ........................", noteStyle12));horizentalAndVerticalCellCenter(cell_25);cell_25.setBackgroundColor(myTableGreyColor2);

        PdfPCell cell_31 = new PdfPCell(new Phrase("", noteStyle12));cell_31.setBackgroundColor(myTableGreyColor3);
        PdfPCell cell_32 = new PdfPCell(new Phrase(""));
        PdfPCell cell_33 = new PdfPCell(signDrIcon);cell_33.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cell_34 = new PdfPCell(new Phrase(""));
        PdfPCell cell_35 = new PdfPCell(new Phrase("", noteStyle12));cell_35.setBackgroundColor(myTableGreyColor3);
        
        cell_11.setBorder(Rectangle.NO_BORDER);cell_12.setBorder(Rectangle.NO_BORDER);cell_13.setBorder(Rectangle.NO_BORDER);cell_14.setBorder(Rectangle.NO_BORDER);cell_15.setBorder(Rectangle.NO_BORDER);
        cell_21.setBorder(Rectangle.NO_BORDER);cell_22.setBorder(Rectangle.NO_BORDER);cell_23.setBorder(Rectangle.NO_BORDER);cell_24.setBorder(Rectangle.NO_BORDER);cell_25.setBorder(Rectangle.NO_BORDER);
        cell_31.setBorder(Rectangle.NO_BORDER);cell_32.setBorder(Rectangle.NO_BORDER);cell_33.setBorder(Rectangle.NO_BORDER);cell_34.setBorder(Rectangle.NO_BORDER);cell_35.setBorder(Rectangle.NO_BORDER);
        
        signatureTBL.addCell(cell_11);signatureTBL.addCell(cell_12);signatureTBL.addCell(cell_13);signatureTBL.addCell(cell_14);signatureTBL.addCell(cell_15);
        signatureTBL.addCell(cell_21);signatureTBL.addCell(cell_22);signatureTBL.addCell(cell_23);signatureTBL.addCell(cell_24);signatureTBL.addCell(cell_25);
        signatureTBL.addCell(cell_31);signatureTBL.addCell(cell_32);signatureTBL.addCell(cell_33);signatureTBL.addCell(cell_34);signatureTBL.addCell(cell_35);
            
        document.add(signatureTBL);
        
    }

    private void addMetaData(Document document)
    {
        document.addTitle("Convention de Stage Tunisie");
        document.addSubject("PFE");
        document.addKeywords("Stage, PFE, Convention");
        document.addAuthor("Saria ESSID");
        document.addCreator("Saria ESSID");
    }

    private void addTitlePage(Document document) throws DocumentException, MalformedURLException, IOException
    {
//        Paragraph cpttl = new Paragraph("Convention de Stage", titleStyle); 
//        cpttl.setAlignment(Element.ALIGN_CENTER);
//        addEmptyLine(cpttl, 1);
//        addEmptyLine(cpttl, 1);
//        document.add(cpttl);
    	
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
    
    private void addEmptyLine(Paragraph paragraph, int number)
    {
        for (int i = 0; i < number; i++)
        {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    private void horizentalAndVerticalAndHeightCellCenter(PdfPCell cell, float fixedHeight)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	cell.setFixedHeight(fixedHeight);
    }
    
    private void horizentalAndVerticalCellCenter(PdfPCell cell)
    {
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    }
    
}
