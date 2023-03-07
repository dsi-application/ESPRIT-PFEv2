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


public class ConventionEN_PDF
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
    Font noteGreyNormalStyle = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, myGreyColor);
    Font attributeStyle = new Font(Font.FontFamily.HELVETICA, 10);
    Font attributeResponseStyle = new Font(Font.FontFamily.HELVETICA, 9);
    Font boldGrayField = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.GRAY);
    
    public ConventionEN_PDF(Optional<Convention> conv, String path, String studentFullName, String studentOption, String studentDepartment)
    {
        try
        {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(path));
//            document.open();
//            addMetaData(document);
//            addTitlePage(document);
//            document.close();
        	

        	String studentDepartmentEN = "";
		
			if(studentDepartment.equalsIgnoreCase("Informatique"))
			{
				studentDepartmentEN = "Computer Science";
			}
			if(studentDepartment.equalsIgnoreCase("Télécoms"))
			{
				studentDepartmentEN = "Telecoms";
			}
			if(studentDepartment.equalsIgnoreCase("Electromécanique"))
			{
				studentDepartmentEN = "Electromechanical Engineering";
			}
			if(studentDepartment.equalsIgnoreCase("Génie civil"))
			{
				studentDepartmentEN = "Civil Engineering";
			}
            
        	System.out.println("-------------------2203----------------> LOL: Generate PDF " + studentDepartmentEN);
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            addMetaData(document);
            
    		document.open();
    		
	    	addTitlePage(document);
	    	addContent(document, conv, studentFullName, studentOption, studentDepartmentEN);
	    	document.newPage();
    		
            document.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addContent(Document document, Optional<Convention> conv, String studentFullName, String studentOption, String studentDepartmentEN) throws DocumentException, MalformedURLException, IOException
    {
    	
    	DateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");  
		
    	document.add(addDefaultEmptyLine(2));
    	
    	Paragraph cpttl = new Paragraph("This agreement governs the relations between :", noteGreyBoldStyle);
    	addEmptyLine(cpttl, 1);
        document.add(cpttl);
        
        // Add BreakLine
        addEmptyLine(cpttl, 1);
        
        
        // Number 1
    	PdfPTable contentPart1 = new PdfPTable(2);
		contentPart1.setWidthPercentage(100);
		contentPart1.setWidths(new int[]{25, 900});
		
		PdfPCell cp1cella_11 = new PdfPCell(new Phrase("1- ", noteGreyBoldStyle));
		cp1cella_11.setLeading(5f, 1f);
        PdfPCell cp1cella_12 = new PdfPCell(new Phrase("ESPRIT School of Engineering represented by its Director-Founder Professor Tahar BENLAKHDAR, hereinafter referred to as ESPRIT", attributeStyle));
        cp1cella_12.setLeading(5f, 1f);
        cp1cella_12.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        cp1cella_11.setBorder(Rectangle.NO_BORDER);cp1cella_12.setBorder(Rectangle.NO_BORDER);
        
        contentPart1.addCell(cp1cella_11);contentPart1.addCell(cp1cella_12);
        
        document.add(contentPart1);
        
        
        // Number 2
        /* Number 2.1 */
        PdfPTable contentPart21 = new PdfPTable(3);
        contentPart21.setWidthPercentage(100);
        contentPart21.setWidths(new int[]{30, 170, 770});
		
        PdfPCell cp1cellz_11 = new PdfPCell(new Phrase("2- ", noteGreyBoldStyle));
        PdfPCell cp1cellz_12 = new PdfPCell(new Phrase("Company :", attributeStyle));
        PdfPCell cp1cellz_13 = new PdfPCell(new Phrase(conv.get().getEntrepriseAccueilConvention().getDesignation(), attributeStyle));
        
        PdfPCell cp1cellz_21 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_22 = new PdfPCell(new Phrase("Address :", attributeStyle));
        PdfPCell cp1cellz_23 = new PdfPCell(new Phrase(conv.get().getAddress(), attributeStyle));
        
        PdfPCell cp1cellz_31 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_32 = new PdfPCell(new Phrase("Represented by :", attributeStyle));
        PdfPCell cp1cellz_33 = new PdfPCell(new Phrase(conv.get().getResponsable(), attributeStyle));
        
        PdfPCell cp1cellz_41 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_42 = new PdfPCell(new Phrase("E-Mail :", attributeStyle));
        PdfPCell cp1cellz_43 = new PdfPCell(new Phrase(conv.get().getMail(), attributeStyle));
        
        PdfPCell cp1cellz_51 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellz_52 = new PdfPCell(new Phrase("Phone :", attributeStyle));
        
        String phoneNbr = conv.get().getTelephone();
        String abbrNbrPh = "--";
        if(!phoneNbr.equalsIgnoreCase("--------"))
        {
        	abbrNbrPh = phoneNbr;
        }
        PdfPCell cp1cellz_53 = new PdfPCell(new Phrase(abbrNbrPh, attributeStyle));
        
        cp1cellz_11.setBorder(Rectangle.NO_BORDER);cp1cellz_12.setBorder(Rectangle.NO_BORDER);cp1cellz_13.setBorder(Rectangle.NO_BORDER);
        cp1cellz_21.setBorder(Rectangle.NO_BORDER);cp1cellz_22.setBorder(Rectangle.NO_BORDER);cp1cellz_23.setBorder(Rectangle.NO_BORDER);
        cp1cellz_31.setBorder(Rectangle.NO_BORDER);cp1cellz_32.setBorder(Rectangle.NO_BORDER);cp1cellz_33.setBorder(Rectangle.NO_BORDER);
        cp1cellz_41.setBorder(Rectangle.NO_BORDER);cp1cellz_42.setBorder(Rectangle.NO_BORDER);cp1cellz_43.setBorder(Rectangle.NO_BORDER);
        cp1cellz_51.setBorder(Rectangle.NO_BORDER);cp1cellz_52.setBorder(Rectangle.NO_BORDER);cp1cellz_53.setBorder(Rectangle.NO_BORDER);
        
        contentPart21.addCell(cp1cellz_11);contentPart21.addCell(cp1cellz_12);contentPart21.addCell(cp1cellz_13);
        contentPart21.addCell(cp1cellz_21);contentPart21.addCell(cp1cellz_22);contentPart21.addCell(cp1cellz_23);
        contentPart21.addCell(cp1cellz_31);contentPart21.addCell(cp1cellz_32);contentPart21.addCell(cp1cellz_33);
        contentPart21.addCell(cp1cellz_41);contentPart21.addCell(cp1cellz_42);contentPart21.addCell(cp1cellz_43);
        contentPart21.addCell(cp1cellz_51);contentPart21.addCell(cp1cellz_52);contentPart21.addCell(cp1cellz_53);
        
        document.add(contentPart21);
        
        
        // Number 3
        PdfPTable contentPart32 = new PdfPTable(2);
        contentPart32.setWidthPercentage(100);
        contentPart32.setWidths(new int[]{30, 920});
        
        PdfPCell cp1cellr_11 = new PdfPCell(new Phrase("3- ", noteGreyBoldStyle));
        String studentContent = "StudentCJ: " + studentFullName + " enrolled in 5th Year.";
        PdfPCell cp1cellr_12 = new PdfPCell(new Phrase(studentContent, attributeStyle));
        
    	PdfPCell cp1cellt_11 = new PdfPCell(new Phrase(""));
        PdfPCell cp1cellt_12 = new PdfPCell(new Phrase("Pursuing The National Engineering Diploma in " + studentDepartmentEN + " hereinafter referred to as the StudentCJ.", attributeStyle));
        
        cp1cellr_11.setBorder(Rectangle.NO_BORDER);cp1cellr_12.setBorder(Rectangle.NO_BORDER);
        cp1cellt_11.setBorder(Rectangle.NO_BORDER);cp1cellt_12.setBorder(Rectangle.NO_BORDER);
         
        contentPart32.addCell(cp1cellr_11);contentPart32.addCell(cp1cellr_12);
        contentPart32.addCell(cp1cellt_11);contentPart32.addCell(cp1cellt_12);
            
        document.add(contentPart32);
        document.add(addDefaultEmptyLine(2));
        
        
        // Number 4
        Paragraph cptt2 = new Paragraph("The following has been agreed to and decided upon :", noteGreyBoldStyle);
        document.add(cptt2);
        document.add(addDefaultEmptyLine(1));
        
        /* Number 4.1 */
        Paragraph cptt31 = new Paragraph("ARTICLE 1: Purpose of the Agreement :", noteGreyNormalStyle);
        document.add(cptt31);
        addEmptyLine(cptt31, 1);
       
        Paragraph cptt32 = new Paragraph("This Agreement outlines the relationship between ESPRIT, the company and the student with regard "
        		                       + "to the End of Studies or Graduation Project (hereinafter referred to as «ESP») that the "
        				               + "student is required to perform within the company.", attributeStyle);
        cptt32.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt32);
        addEmptyLine(cptt32, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 4.2 */
        Paragraph cptt41 = new Paragraph("ARTICLE 2: Purpose of the «ESP» or Graduation Project :", noteGreyNormalStyle);
        document.add(cptt41);
        addEmptyLine(cptt41, 1);
       
        Paragraph cptt42 = new Paragraph("The primary objective of the “ESP” or Graduation Project is the practical application of teaching at ESPRIT. "
        		+ "It is under the direction of the company's technical supervisor and an educational"
        		+ "supervisor from ESPRIT.", attributeStyle);
        cptt42.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt42);
        addEmptyLine(cptt42, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 4.3 */
        Paragraph cptt51 = new Paragraph("ARTICLE 3: Internship Program", noteGreyNormalStyle);
        document.add(cptt51);
        addEmptyLine(cptt51, 1);
       
        Paragraph cptt52 = new Paragraph("The Internship Program, appended to this Agreement, is put forward by the Company and approved by ESPRIT, "
        		+ "based on the school curriculum and the student's area of specialization.", attributeStyle);
        cptt52.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt52);
        addEmptyLine(cptt52, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 4.4 */
        Paragraph cptt61 = new Paragraph("ARTICLE 4: Intern's status and duties", noteGreyNormalStyle);
        document.add(cptt61);
        addEmptyLine(cptt61, 1);
       
        Paragraph cptt62 = new Paragraph("For the duration of the internship, the trainee remains a fully enrolled student ESPRIT. In addition [he/she] "
        		+ "is obliged to respect all of Company's local regulations.In the event of any breach of discipline, the Head of Company reserves the right "
        		+ "to terminate the course after having informed the Academic Registrar of ESPRIT.", attributeStyle);
        cptt62.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt62);
        addEmptyLine(cptt62, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 4.5 */
        Paragraph cptt71 = new Paragraph("ARTICLE 5: Termination", noteGreyNormalStyle);
        document.add(cptt71);
        addEmptyLine(cptt71, 1);
        
        Paragraph cptt72 = new Paragraph("The student may, at his/her own discretion, interrupt his/her ESP lest causing loss of benefit. The " + 
        		"ESP may be suspended or terminated by agreement of the company, the student and the " + 
        		"instructor in charge, in case of breach of the obligations of Article 4.\r\n" + 
        		"In case of internship termination for any reason whatsoever, the company will report in writing to " + 
        		"the educational supervisor in charge of the student.\r\n\r\n", attributeStyle);
        cptt72.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt72);
        addEmptyLine(cptt72, 1);
        
        Paragraph cptt73 = new Paragraph("In case of non-compliance with the terms of the agreement and its annexes, the agreement may " + 
        		"be terminated by the company or by ESPRIT by registered letter with proof of delivery to the " + 
        		"student.", attributeStyle);
        cptt73.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt73);
        addEmptyLine(cptt73, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 6 */
        Paragraph cptt81 = new Paragraph("\r\nARTICLE 6: Appraisal", noteGreyNormalStyle);
        document.add(cptt81);
        addEmptyLine(cptt81, 1);
       
        Paragraph cptt82 = new Paragraph("The validation process of the ESP or Graduation Project is defined as part of the testing " + 
        		"procedures. The activity of the student will be assessed. An evaluation form is completed by the " + 
        		"host organization and the Academic Supervisor and delivered to ESPRIT.", attributeStyle);
        cptt82.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt82);
        addEmptyLine(cptt82, 1);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 7 */
        Paragraph cptt91 = new Paragraph("ARTICLE 7: Internship Report", noteGreyNormalStyle);
        document.add(cptt91);
        addEmptyLine(cptt91, 1);
       
        Paragraph cptt92 = new Paragraph("At the end of the internship, the student will submit an internship report to ESPRIT, endorsed by " + 
        		"the Company and the Academic Supervisor who has ensured the student's follow-up. " + 
        		"A copy of this report will be forwarded to the company subject to the respect of the student's " + 
        		"copyright.", attributeStyle);
        cptt92.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt92);
        addEmptyLine(cptt92, 1);
        
        Paragraph cptt93 = new Paragraph("The internship report will be defended before a jury and subsequently graded. This defence is " + 
        		"public unless an exception is granted due to lack of confidentiality, on reasoned request of the company official.", attributeStyle);
        cptt93.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt93);
        addEmptyLine(cptt93, 1);
        
        Paragraph cptt94 = new Paragraph("At the end of the internship, the company will issue a certificate to the student specifying the nature and duration of the ESP.", attributeStyle);
        cptt94.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt94);
        addEmptyLine(cptt94, 1);
        
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 8*/
        Paragraph cptt101 = new Paragraph("ARTICLE 8: (Non-Disclosure)", noteGreyNormalStyle);
        document.add(cptt101);
        addEmptyLine(cptt101, 1);
       
        Paragraph cptt102 = new Paragraph("The student will be required, throughout and after the period of the ESP's Internship, to observe " + 
        		"professional secrecy with regard to third parties for everything related to his/her activity during the " + 
        		"internship and more generally of which he/she has become aware directly or indirectly in the " + 
        		"course of carrying it out; the content of documents that have been penned by the student " + 
        		"himself/herself, including the ESP report, or any item which could have been lent to him/her by the " + 
        		"company remains by all means the property of the latter.", attributeStyle);
        cptt102.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt102);
        document.add(addDefaultEmptyLine(1));
        
        
        /* Number 9 */
        Paragraph cptt111 = new Paragraph("ARTICLE 9: Consent", noteGreyNormalStyle);
        document.add(cptt111);
        addEmptyLine(cptt111, 1);
       
        Paragraph cptt112 = new Paragraph("This agreement is first brought to the attention of the student or his legal representative, if minor, " + 
        		"to express consent for the terms set out above.", attributeStyle);
        cptt112.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt112);
        addEmptyLine(cptt112, 1);
        document.add(addDefaultEmptyLine(1));
        
        /* Number 10 */
        Paragraph cptt131 = new Paragraph("ARTICLE 10: (ESP or Graduation Project Outline)", noteGreyNormalStyle);
        document.add(cptt131);
        addEmptyLine(cptt131, 1);
       
        Paragraph cptt132 = new Paragraph("", attributeStyle);
        cptt132.add(new Phrase("The ESP will take place according to the following organization:\r\n"
        		+ "ESP Internship dates: The ESP will take place from ", attributeStyle));
        
        cptt132.add(new Phrase(formattedDate.format(conv.get().getDateDebut()), boldGrayField));
        cptt132.add(new Phrase(" through ", attributeStyle));
        cptt132.add(new Phrase(formattedDate.format(conv.get().getDateFin()), boldGrayField));
        cptt132.add(new Phrase(" and shall expire no later than the end of the academic year.\r\n"
        		+ "In case of extension for exceptional reasons conducted before graduation and beyond the original planned dates, an amendment must "
        		+ "be prepared and signed by the contracting parties.\r\nBusiness Site Address: The internship takes place at the business address.", attributeStyle));
        cptt132.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(cptt132);
        document.add(addDefaultEmptyLine(1));
        
        
        PdfPTable contentPartNb112 = new PdfPTable(2);
        contentPartNb112.setWidthPercentage(100);
        contentPartNb112.setWidths(new int[]{350, 600});
    	PdfPCell cp1cellf_11 = new PdfPCell(new Phrase("Department : ", attributeStyle));
        PdfPCell cp1cellf_12 = new PdfPCell(new Phrase(" ........................................................."));
        PdfPCell cp1cellf_21 = new PdfPCell(new Phrase("Unit : ", attributeStyle));
        PdfPCell cp1cellf_22 = new PdfPCell(new Phrase(" ........................................................."));
        PdfPCell cp1cellf_31 = new PdfPCell(new Phrase("Business site Supervisor : ", attributeStyle));
        PdfPCell cp1cellf_32 = new PdfPCell(new Phrase(" ........................................................."));
        PdfPCell cp1cellf_41 = new PdfPCell(new Phrase("ESPRIT Academic Mentor or Supervisor : ", attributeStyle));
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
        document.add(addDefaultEmptyLine(2));
        
        String signDrPath = "C:\\ESP-DOCS\\Logos\\conventionSign.png";
        Image signDrIcon = Image.getInstance(signDrPath);
        signDrIcon.scaleAbsolute(130f, 57f);
        
        PdfPTable signatureTBL = new PdfPTable(5);
        signatureTBL.setWidthPercentage(100);    // table.setTotalWidth(1000);
        signatureTBL.setWidths(new int[]{300, 20, 300, 20, 300});
        PdfPCell cell_11 = new PdfPCell(new Phrase("Signed for and on behalf of\r\nCompany", noteStyle12));horizentalAndVerticalAndHeightCellCenter(cell_11, 30f);cell_11.setBackgroundColor(myTableGreyColor1);
        PdfPCell cell_12 = new PdfPCell(new Phrase(""));
        PdfPCell cell_13 = new PdfPCell(new Phrase("Signed for and on behalf of\r\nESPRIT", noteStyle12));horizentalAndVerticalAndHeightCellCenter(cell_13, 30f);cell_13.setBackgroundColor(myTableGreyColor1);
        PdfPCell cell_14 = new PdfPCell(new Phrase(""));
        PdfPCell cell_15 = new PdfPCell(new Phrase("Signed by the Intern", noteStyle12));horizentalAndVerticalAndHeightCellCenter(cell_15, 30f);cell_15.setBackgroundColor(myTableGreyColor1);
        
        PdfPCell cell_21 = new PdfPCell(new Phrase("In ........................ on ........................", noteStyle12));horizentalAndVerticalAndHeightCellCenter(cell_21, 22f);cell_21.setBackgroundColor(myTableGreyColor2);
        PdfPCell cell_22 = new PdfPCell(new Phrase(""));
        PdfPCell cell_23 = new PdfPCell(new Phrase("In Tunis on " + formattedDate.format(new Date()), noteStyle12)); horizentalAndVerticalAndHeightCellCenter(cell_23, 22f);cell_23.setBackgroundColor(myTableGreyColor2);
        PdfPCell cell_24 = new PdfPCell(new Phrase(""));
        PdfPCell cell_25 = new PdfPCell(new Phrase("In ........................ on ........................", noteStyle12));horizentalAndVerticalAndHeightCellCenter(cell_25, 22f);cell_25.setBackgroundColor(myTableGreyColor2);

        PdfPCell cell_31 = new PdfPCell(new Phrase(conv.get().getEntrepriseAccueilConvention().getDesignation(), noteStyle12));cell_31.setBackgroundColor(myTableGreyColor3);horizentalAndVerticalAndHeightCellCenter(cell_31, 32f);
        PdfPCell cell_32 = new PdfPCell(new Phrase(""));
        PdfPCell cell_33 = new PdfPCell(new Phrase("Professor Tahar BENLAKHDAR \r\n Director and Founder of ESPRIT", noteStyle12));cell_33.setBackgroundColor(myTableGreyColor3);horizentalAndVerticalAndHeightCellCenter(cell_33, 32f);
        PdfPCell cell_34 = new PdfPCell(new Phrase(""));
        PdfPCell cell_35 = new PdfPCell(new Phrase(studentFullName, noteStyle12));cell_35.setBackgroundColor(myTableGreyColor3);horizentalAndVerticalAndHeightCellCenter(cell_35, 32f);
        
        PdfPCell cell_41 = new PdfPCell(new Phrase(""));cell_41.setFixedHeight(60f);cell_41.setBackgroundColor(myTableGreyColor4);
        PdfPCell cell_42 = new PdfPCell(new Phrase(""));
        PdfPCell cell_43 = new PdfPCell(signDrIcon);cell_43.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell cell_44 = new PdfPCell(new Phrase(""));
        PdfPCell cell_45 = new PdfPCell(new Phrase(""));cell_45.setBackgroundColor(myTableGreyColor4);
        
        cell_11.setBorder(Rectangle.NO_BORDER);cell_12.setBorder(Rectangle.NO_BORDER);cell_13.setBorder(Rectangle.NO_BORDER);cell_14.setBorder(Rectangle.NO_BORDER);cell_15.setBorder(Rectangle.NO_BORDER);
        cell_21.setBorder(Rectangle.NO_BORDER);cell_22.setBorder(Rectangle.NO_BORDER);cell_23.setBorder(Rectangle.NO_BORDER);cell_24.setBorder(Rectangle.NO_BORDER);cell_25.setBorder(Rectangle.NO_BORDER);
        cell_31.setBorder(Rectangle.NO_BORDER);cell_32.setBorder(Rectangle.NO_BORDER);cell_33.setBorder(Rectangle.NO_BORDER);cell_34.setBorder(Rectangle.NO_BORDER);cell_35.setBorder(Rectangle.NO_BORDER);
        cell_41.setBorder(Rectangle.NO_BORDER);cell_42.setBorder(Rectangle.NO_BORDER);cell_43.setBorder(Rectangle.NO_BORDER);cell_44.setBorder(Rectangle.NO_BORDER);cell_45.setBorder(Rectangle.NO_BORDER);
        
        signatureTBL.addCell(cell_11);signatureTBL.addCell(cell_12);signatureTBL.addCell(cell_13);signatureTBL.addCell(cell_14);signatureTBL.addCell(cell_15);
        signatureTBL.addCell(cell_21);signatureTBL.addCell(cell_22);signatureTBL.addCell(cell_23);signatureTBL.addCell(cell_24);signatureTBL.addCell(cell_25);
        signatureTBL.addCell(cell_31);signatureTBL.addCell(cell_32);signatureTBL.addCell(cell_33);signatureTBL.addCell(cell_34);signatureTBL.addCell(cell_35);
        signatureTBL.addCell(cell_41);signatureTBL.addCell(cell_42);signatureTBL.addCell(cell_43);signatureTBL.addCell(cell_44);signatureTBL.addCell(cell_45);
            
        document.add(signatureTBL);
        
    }

    private void addMetaData(Document document)
    {
        document.addTitle("Internship Agreement");
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
        PdfPCell cell2 = new PdfPCell(new Phrase("\r\n " + "Internship Agreement", titleStyle));
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
        
//        Paragraph cpttl = new Paragraph("Internship Agreement", titleStyle); 
//        cpttl.setAlignment(Element.ALIGN_CENTER);
//        addEmptyLine(cpttl, 1);
//        addEmptyLine(cpttl, 1);
//        document.add(cpttl);
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
