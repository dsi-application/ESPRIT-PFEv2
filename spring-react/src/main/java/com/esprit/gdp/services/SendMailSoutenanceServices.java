package com.esprit.gdp.services;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;



@Service
public class SendMailSoutenanceServices {

	public void sendMail(String subject, String student, String juryMembers, String dateSoutenance,
			String selectedStartHour, String selectedEndHour, String meetLink, String nomJP, String nomPE,
			String nomEXP, String salle) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("saria.essid@gmail.com", "saria2013");
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress("saria.essid@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(student));

			InternetAddress[] parsecc = InternetAddress.parse(juryMembers, true);
			message.addRecipients(Message.RecipientType.CC, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que <strong><font color=grey> vous êtes prêts pour passer votre soutenance </font></strong>, "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en présentiel dans la salle <font color=blue> " + salle + " </font>" + "."

					+ " <br/>Les membres de Jury sont: "
					+ " <br/> &nbsp;&nbsp;<font color=grey> - Président de Jury : </font>&nbsp; M/Mme " + nomJP
					+ " <br/> &nbsp;&nbsp;<font color=grey> - Encadrant Pédagogique : </font> &nbsp;M/Mme " + nomPE
					+ " <br/> &nbsp;&nbsp;<font color=grey> - Expert : </font> &nbsp;M/Mme " + nomEXP
					+ " <br/>Nous vous souhaitons une bonne chance. "
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">";

			String contentMeet = "Nous voulons vous informer que <strong><font color=grey> vous êtes prêts pour passer votre soutenance </font></strong>, "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en distanciel via  <font color=blue> <a href=\"" + meetLink
					+ "\">le lien Meet</a> </font>" + "." + " <br/>Les membres de Jury sont: "
					+ " <br/> &nbsp;&nbsp;<font color=grey> - Président de Jury : </font>&nbsp; M/Mme " + nomJP
					+ " <br/> &nbsp;&nbsp;<font color=grey> - Encadrant Pédagogique : </font> &nbsp;M/Mme " + nomPE
					+ " <br/> &nbsp;&nbsp;<font color=grey> - Expert : </font> &nbsp;M/Mme " + nomEXP
					+ " <br/>Nous vous souhaitons une bonne chance. "
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black>Majdi GHARBI</font></strong><br/>"
					+ "<strong><font color=black>Département des stages ESPRIT</font></strong><br/>"
					+ "Fax     : +216 70 685 685<br/>" + "Adresse : Z.I. Chotrana II - B.P. 160 - 2083<br/>"
					+ "Pôle Technologique - El Ghazala, Tunis, Tunisia<br/>"
					+ "Google Maps: <a href=\"https://www.google.tn/maps/place/36%C2%B053'57.6%22N+10%C2%B011'22.1%22E/@36.8993333,10.1872835,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d36.899327!4d10.189464\">36.899327, 10.189464</a><br/><br/>"
					+ "<img src=\"cid:image1\" height=\"70\" width=\"150\">";

			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds1));
			messageBodyPart.setHeader("Content-ID", "<image1>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);

			// messageBodyPart = new MimeBodyPart();
			// DataSource fds4 = new FileDataSource("C:\\ESP-DOCS\\pfelogo3.png");
			// messageBodyPart.setDataHandler(new DataHandler(fds4));
			// messageBodyPart.setHeader("Content-ID","<image2>");
			// multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
