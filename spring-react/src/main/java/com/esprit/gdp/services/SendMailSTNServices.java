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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.gdp.repository.SettingsRepository;

@Service
public class SendMailSTNServices {

	@Autowired
	SettingsRepository settingsRepository;

	private String mailGoogleReception() {
		String gai = settingsRepository.findGoogleAccountId();
		return gai.substring(0, gai.lastIndexOf("@esprit.tn") + 10);
	}

	private String pwdGoogleReception() {
		String gai = settingsRepository.findGoogleAccountId();
		return gai.substring(gai.indexOf("@esprit.tn") + 10, gai.length());
	}

	/*
	public void sendMailStudent(String subject, String student, String dateSoutenance, String selectedStartHour,
			String selectedEndHour, String meetLink, String nomJP, String nomPE, String nomEXP, String salle,
			String nameRSS) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(student));

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en présentiel dans la salle <font color=blue> " + salle + " </font>" + "(Site ElGhazela) ."

					+ " <br/>Vous êtes invité(e) à vous présenter devant la salle 15min avant le début de votre soutenance. "
					// + " <br/>Les soutenances se déroulent à huis clos. "
					+ " <br/>Je vous souhaite une excellente soutenance."
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en distanciel via  <font color=blue> <a href=\"" + meetLink
					+ "\">le lien Meet</a> </font>" + "."

					+ " <br/>Vous êtes invité(e) à vous présenter via Meet 15min avant le début de votre soutenance. "
					// + " <br/>Les soutenances se déroulent à huis clos. "
					+ " <br/>Je vous souhaite une excellente soutenance."
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
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

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailPair(String subject, String pairMails, String dateSoutenance, String selectedStartHour,
			String selectedEndHour, String meetLink, String nomJP, String nomPE, String nomEXP, String salle,
			String nameRSS) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));
			// message.addRecipient(Message.RecipientType.TO, new InternetAddress(student));

			InternetAddress[] parsecc = InternetAddress.parse(pairMails, true);
			message.addRecipients(Message.RecipientType.TO, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en présentiel dans la salle <font color=blue> " + salle + " </font>" + "(Site ElGhazela) ."

					+ " <br/>Vous êtes invité(e) à vous présenter devant la salle 15min avant le début de votre soutenance. "
					// + " <br/>Les soutenances se déroulent à huis clos. "
					+ " <br/>Je vous souhaite une excellente soutenance."
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en distanciel via  <font color=blue> <a href=\"" + meetLink
					+ "\">le lien Meet</a> </font>" + "."

					+ " <br/>Vous êtes invité(e) à vous présenter via Meet 15min avant le début de votre soutenance. "
					// + " <br/>Les soutenances se déroulent à huis clos. "
					+ " <br/>Je vous souhaite une excellente soutenance."
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
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

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailJuryPresident(String subject, String student, String juryMembers, String dateSoutenance,
			String selectedStartHour, String selectedEndHour, String meetLink, String nomJP, String nomPE,
			String nomEXP, String salle, String sharedStudentESPReport, String nameRSS) {
		// System.out.println("-------------------------> HOSTS STN - 3.1");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// System.out.println("-------------------------> HOSTS STN - 3.2");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		// System.out.println("-------------------------> HOSTS STN - 3.3");

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));

			// System.out.println("-------------------------> HOSTS STN - 3.4");
			InternetAddress[] parsecc = InternetAddress.parse(juryMembers, true);
			message.addRecipients(Message.RecipientType.TO, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membres du jury en présentiel dans la salle <font color=blue> " + salle
					+ " </font>" + "(Site ElGhazela) ."
					+ "<br/>" + "Veuillez cliquer sur <font color=blue> <a href=\""
					+ sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport de l'étudiant(e)</font>."
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membre des jurys en présentiel dans la salle via <font color=blue> <a href=\""
					+ meetLink + "\">le lien Meet</a> </font>." + "<br/>"
					+ "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport de l'étudiant(e)</font>."
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
					+ "<strong><font color=black>Département des stages ESPRIT</font></strong><br/>"
					+ "Fax     : +216 70 685 685<br/>" + "Adresse : Z.I. Chotrana II - B.P. 160 - 2083<br/>"
					+ "Pôle Technologique - El Ghazala, Tunis, Tunisia<br/>"
					+ "Google Maps: <a href=\"https://www.google.tn/maps/place/36%C2%B053'57.6%22N+10%C2%B011'22.1%22E/@36.8993333,10.1872835,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d36.899327!4d10.189464\">36.899327, 10.189464</a><br/><br/>"
					+ "<img src=\"cid:image1\" height=\"70\" width=\"150\">";
			// System.out.println("-------------------------> HOSTS STN - 3.5");
			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds1));
			messageBodyPart.setHeader("Content-ID", "<image1>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);

			// System.out.println("-------------------------> HOSTS STN - 3.6");

			message.setContent(multipart);
			// System.out.println("-------------------------> HOSTS STN - 3.7");
			Transport.send(message);
			// System.out.println("-------------------------> HOSTS STN - 3.8");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		// System.out.println("-------------------------> HOSTS STN - 3.9");

	}

	

	public void sendMailJuryPresidentPair(String subject, String student, String pair, String juryMembers,
			String dateSoutenance, String selectedStartHour, String selectedEndHour, String meetLink, String nomJP,
			String nomPE, String nomEXP, String salle, String sharedStudentESPReport, String nameRSS) {
		// System.out.println("-------------------------> HOSTS STN - 3.1");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// System.out.println("-------------------------> HOSTS STN - 3.2");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		// System.out.println("-------------------------> HOSTS STN - 3.3");

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));

			// System.out.println("-------------------------> HOSTS STN - 3.4");
			InternetAddress[] parsecc = InternetAddress.parse(juryMembers, true);
			message.addRecipients(Message.RecipientType.TO, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE du binôme <strong><font color=#b30000> "
					+ student + " </font></strong> et <strong><font color=#b30000> " + pair + " </font></strong>, "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membres du jury en présentiel dans la salle <font color=blue> " + salle
					+ " </font>" + "(Site ElGhazela) ."
					+ "<br/>" + "Veuillez cliquer sur <font color=blue> <a href=\""
					+ sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport du binôme</font>." + " <br/>"
					+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE du binôme <strong><font color=#b30000> "
					+ student + " </font></strong> et <strong><font color=#b30000> " + pair + " </font></strong>, "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membre des jurys en présentiel dans la salle via <font color=blue> <a href=\""
					+ meetLink + "\">le lien Meet</a> </font>." + "<br/>"
					+ "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport du binôme</font>." + " <br/>"
					+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
					+ "<strong><font color=black>Département des stages ESPRIT</font></strong><br/>"
					+ "Fax     : +216 70 685 685<br/>" + "Adresse : Z.I. Chotrana II - B.P. 160 - 2083<br/>"
					+ "Pôle Technologique - El Ghazala, Tunis, Tunisia<br/>"
					+ "Google Maps: <a href=\"https://www.google.tn/maps/place/36%C2%B053'57.6%22N+10%C2%B011'22.1%22E/@36.8993333,10.1872835,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d36.899327!4d10.189464\">36.899327, 10.189464</a><br/><br/>"
					+ "<img src=\"cid:image1\" height=\"70\" width=\"150\">";
			// System.out.println("-------------------------> HOSTS STN - 3.5");
			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds1));
			messageBodyPart.setHeader("Content-ID", "<image1>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);

			// System.out.println("-------------------------> HOSTS STN - 3.6");

			message.setContent(multipart);
			// System.out.println("-------------------------> HOSTS STN - 3.7");
			Transport.send(message);
			// System.out.println("-------------------------> HOSTS STN - 3.8");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		// System.out.println("-------------------------> HOSTS STN - 3.9");

	}

	public void sendMailJuryPresidentForEMGC(String subject, String student, String juryMembers, String dateSoutenance,
			String selectedStartHour, String selectedEndHour, String meetLink, String nomJP, String nomPE,
			String nomEXP, String salle, String nameRSS) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));

			InternetAddress[] parsecc = InternetAddress.parse(juryMembers, true);
			message.addRecipients(Message.RecipientType.TO, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membre des jurys en présentiel dans la salle <font color=blue> " + salle
					+ " </font>." + " <br/>"
					+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membre des jurys en présentiel dans la salle via <font color=blue> <a href=\""
					+ meetLink + "\">le lien Meet</a> </font>." + " <br/>" + " <br/>"
					+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
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

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	*/
	
	public void sendMailJuryPresidentGC(String subject, String student, String juryMembers, String dateSoutenance,
			String selectedStartHour, String selectedEndHour, String meetLink, String nomJP, String nomPE,
			String nomEXP, String salle, String sharedStudentESPReport, String sharedStudentTechnicalFolder,
			String nameRSS) {
		// System.out.println("-------------------------> HOSTS STN - 3.1");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// System.out.println("-------------------------> HOSTS STN - 3.2");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		// System.out.println("-------------------------> HOSTS STN - 3.3");

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));

			// System.out.println("-------------------------> HOSTS STN - 3.4");
			InternetAddress[] parsecc = InternetAddress.parse(juryMembers, true);
			message.addRecipients(Message.RecipientType.TO, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membres du jury en présentiel dans la salle <font color=blue> " + salle
					+ " </font>" + "(Site ElGhazela) ." 
					+ "<br/>" + "Veuillez cliquer sur <font color=blue> <a href=\""
					+ sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport de l'étudiant(e)</font>."
					+ "<br/>" + "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentTechnicalFolder
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Dossier Technique de l'étudiant(e)</font>."
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membre des jurys en présentiel dans la salle via <font color=blue> <a href=\""
					+ meetLink + "\">le lien Meet</a> </font>." + "<br/>"
					+ "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport de l'étudiant(e)</font>."
					+ " <br/>" + "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentTechnicalFolder
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Dossier Technique de l'étudiant(e)</font>."
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
					+ "<strong><font color=black>Département des stages ESPRIT</font></strong><br/>"
					+ "Fax     : +216 70 685 685<br/>" + "Adresse : Z.I. Chotrana II - B.P. 160 - 2083<br/>"
					+ "Pôle Technologique - El Ghazala, Tunis, Tunisia<br/>"
					+ "Google Maps: <a href=\"https://www.google.tn/maps/place/36%C2%B053'57.6%22N+10%C2%B011'22.1%22E/@36.8993333,10.1872835,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d36.899327!4d10.189464\">36.899327, 10.189464</a><br/><br/>"
					+ "<img src=\"cid:image1\" height=\"70\" width=\"150\">";
			// System.out.println("-------------------------> HOSTS STN - 3.5");
			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds1));
			messageBodyPart.setHeader("Content-ID", "<image1>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);

			// System.out.println("-------------------------> HOSTS STN - 3.6");

			message.setContent(multipart);
			// System.out.println("-------------------------> HOSTS STN - 3.7");
			Transport.send(message);
			// System.out.println("-------------------------> HOSTS STN - 3.8");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		// System.out.println("-------------------------> HOSTS STN - 3.9");

	}
	
	public void sendMailToScolarity(String subject, String student, String dateSoutenance, String selectedStartHour,
			String selectedEndHour, String salle, String nameRSS) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress("bechir.khemiri@esprit.tn"));
			message.addRecipient(Message.RecipientType.CC, new InternetAddress("mohamedali.bouakline@esprit.tn"));

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que l'étudiant <strong><font color=#b30000> " + student
					+ " </font></strong> va passer sa soutenance de PFE " + "le <font color=blue> " + dateSoutenance
					+ " </font> " + "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> "
					+ selectedEndHour + "</font> dans la salle <font color=blue> " + salle + " </font>." + "<br/>"
					+ "Prière de prendre en considération cette réservation.";
			content = contentNormal;

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
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

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailToScolarityForPair(String subject, String student, String pair, String dateSoutenance,
			String selectedStartHour, String selectedEndHour, String salle, String nameRSS) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress("bechir.khemiri@esprit.tn"));
			message.addRecipient(Message.RecipientType.CC, new InternetAddress("mohamedali.bouakline@esprit.tn"));

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que le binôme <strong><font color=#b30000> " + student
					+ " </font></strong> et <strong><font color=#b30000> " + pair
					+ " </font></strong> va passer sa soutenance de PFE " + "le <font color=blue> " + dateSoutenance
					+ " </font> " + "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> "
					+ selectedEndHour + "</font> dans la salle <font color=blue> " + salle + " </font>." + "<br/>"
					+ "Prière de prendre en considération cette réservation.";
			content = contentNormal;

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
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

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	/********************************** ADD RSS + CPS ***/
	public void sendMailPairWithRSSandCPS(String subject, String pairMails, String respMembers, String dateSoutenance, String selectedStartHour, String selectedEndHour, String meetLink ,String nomJP, String nomPE, String nomEXP, String salle, String nameRSS)
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(mailGoogleReception(),pwdGoogleReception());
			}
		});

		try
		{
			MimeMessage message = new MimeMessage(session);
	        message.setSubject(subject);
	        message.setFrom(new InternetAddress(mailGoogleReception()));
	        //message.addRecipient(Message.RecipientType.TO, new InternetAddress(student));
	        
	        InternetAddress[] parseto = InternetAddress.parse(pairMails, true);
			message.addRecipients(Message.RecipientType.TO, parseto);
	        
			InternetAddress[] parsecc = InternetAddress.parse(respMembers, true);
			message.addRecipients(Message.RecipientType.CC, parsecc);
			
	        MimeMultipart multipart = new MimeMultipart("related");

	        BodyPart messageBodyPart = new MimeBodyPart();
	        String header = "Bonjour,<br/><br/>";
	        
	        
	        
	        String content = null;
	        String contentNormal = 
	        		"Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
	        		+ "le <font color=blue> " + dateSoutenance + " </font> "
	        		+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
	        		+ " </font> en présentiel dans la salle <font color=blue> " + salle + " </font>" + "(Site ElGhazela) ."
	        		
	        		+ " <br/>Vous êtes invité(e) à vous présenter devant la salle 15min avant le début de votre soutenance. "
	        		//+ " <br/>Les soutenances se déroulent à huis clos. "
	        		+ " <br/>Je vous souhaite une excellente soutenance."
	        	    + " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
	        	    + " <br/>"
	        		+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
	        		+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M " + nomJP + " </font>"
	        		+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M " + nomPE + " </font>"
	        		+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M " + nomEXP + " </font> <br/><br/>";
	        		
	        		
	        		String contentMeet = 
	        				"Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
	        				+ "le <font color=blue> " + dateSoutenance + " </font> "
	        				+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
	        				+ " </font> en distanciel via  <font color=blue> <a href=\"" + meetLink + "\">le lien Meet</a> </font>" + "."
	        			
	        				+ " <br/>Vous êtes invité(e) à vous présenter via Meet 15min avant le début de votre soutenance. "
			        		//+ " <br/>Les soutenances se déroulent à huis clos. "
			        		+ " <br/>Je vous souhaite une excellente soutenance."
			        	    + " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
			        	    + " <br/>"
			        		+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
			        		+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M " + nomJP + " </font>"
			        		+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M " + nomPE + " </font>"
			        		+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M " + nomEXP + " </font> <br/><br/>";
			        		
	        		
	        		
	        		if(meetLink.equalsIgnoreCase("NOMEET"))
	        		{
	        			content = contentNormal;
	        		}
	        		else
	        		{
	        			content = contentMeet;
	        		}
	        
	        
	        String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
			        		+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
			        		+ "<strong><font color=black>Département des stages ESPRIT</font></strong><br/>"
			        		+ "Fax     : +216 70 685 685<br/>"
			        		+ "Adresse : Z.I. Chotrana II - B.P. 160 - 2083<br/>"
			        		+ "Pôle Technologique - El Ghazala, Tunis, Tunisia<br/>"
			        		+ "Google Maps: <a href=\"https://www.google.tn/maps/place/36%C2%B053'57.6%22N+10%C2%B011'22.1%22E/@36.8993333,10.1872835,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d36.899327!4d10.189464\">36.899327, 10.189464</a><br/><br/>"
			        		+ "<img src=\"cid:image1\" height=\"70\" width=\"150\">";
	        
	        String allContent = header + content + footer;
	        
	        messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
	        multipart.addBodyPart(messageBodyPart);
	        
	        messageBodyPart = new MimeBodyPart();
	        DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
	        messageBodyPart.setDataHandler(new DataHandler(fds1));
	        messageBodyPart.setHeader("Content-ID","<image1>");
	        messageBodyPart.setFileName("ESPRIT.png");
	        multipart.addBodyPart(messageBodyPart);
	        
	        message.setContent(multipart);
			
			Transport.send(message);
		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
	
	}

	public void sendMailJuryPresidentPairWithRSSandCPS(String subject, String student, String pair, String juryMembers, String respMembers,
			String dateSoutenance, String selectedStartHour, String selectedEndHour, String meetLink ,
			String nomJP, String nomPE, String nomEXP, String salle, String sharedStudentESPReport, String nameRSS)
	{
//		System.out.println("-------------------------> HOSTS STN - 3.1");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
//		System.out.println("-------------------------> HOSTS STN - 3.2");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(mailGoogleReception(),pwdGoogleReception());
			}
		});

//		System.out.println("-------------------------> HOSTS STN - 3.3");
		
		try
		{
			MimeMessage message = new MimeMessage(session);
	        message.setSubject(subject);
	        message.setFrom(new InternetAddress(mailGoogleReception()));
	        
//	        System.out.println("-------------------------> HOSTS STN - 3.4");
			InternetAddress[] parseto = InternetAddress.parse(juryMembers, true);
			message.addRecipients(Message.RecipientType.TO, parseto);
			
			InternetAddress[] parsecc = InternetAddress.parse(respMembers, true);
			message.addRecipients(Message.RecipientType.CC, parsecc);


	        MimeMultipart multipart = new MimeMultipart("related");

	        BodyPart messageBodyPart = new MimeBodyPart();
	        String header = "Bonjour,<br/><br/>";
	        
	        String content = null;
	        String contentNormal = 
	        		"Nous voulons vous informer que vous êtes invités à la soutenance de  PFE du binôme <strong><font color=#b30000> " + student + " </font></strong> et <strong><font color=#b30000> " + pair + " </font></strong>, "
	        		+ "le <font color=blue> " + dateSoutenance + " </font> "
	        		+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
	        		+ " </font> en tant que membres du jury en présentiel dans la salle <font color=blue> " + salle + " </font>" + "(Site ElGhazela) ."
	        		+ "<br/>"
    				+ "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentESPReport + "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport du binôme</font>."
    				+ " <br/>"
	        		+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
	        		+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M " + nomJP + " </font>"
	        		+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M " + nomPE + " </font>"
	        		+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M " + nomEXP + " </font> <br/><br/>";
	        		
	        		
	        String contentMeet = 
	        		"Nous voulons vous informer que vous êtes invités à la soutenance de  PFE du binôme <strong><font color=#b30000> " + student + " </font></strong> et <strong><font color=#b30000> " + pair + " </font></strong>, "
	        		+ "le <font color=blue> " + dateSoutenance + " </font> "
	        		+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
	        		+ " </font> en tant que membre des jurys en présentiel dans la salle via <font color=blue> <a href=\"" + meetLink + "\">le lien Meet</a> </font>."
	        		+ "<br/>"
	        		+ "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentESPReport + "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport du binôme</font>."
	        		+ " <br/>"
	        		+ " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
	        		+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M " + nomJP + " </font>"
	        		+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M " + nomPE + " </font>"
	        		+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M " + nomEXP + " </font> <br/><br/>";
	        		
	        		
	        			
	        if(meetLink.equalsIgnoreCase("NOMEET"))
	        {
	      			content = contentNormal;
	        }
	        else
	        {
	        	content = contentMeet;
	        }
	        
	        String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
			        		+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
			        		+ "<strong><font color=black>Département des stages ESPRIT</font></strong><br/>"
			        		+ "Fax     : +216 70 685 685<br/>"
			        		+ "Adresse : Z.I. Chotrana II - B.P. 160 - 2083<br/>"
			        		+ "Pôle Technologique - El Ghazala, Tunis, Tunisia<br/>"
			        		+ "Google Maps: <a href=\"https://www.google.tn/maps/place/36%C2%B053'57.6%22N+10%C2%B011'22.1%22E/@36.8993333,10.1872835,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d36.899327!4d10.189464\">36.899327, 10.189464</a><br/><br/>"
			        		+ "<img src=\"cid:image1\" height=\"70\" width=\"150\">";
//	        System.out.println("-------------------------> HOSTS STN - 3.5");
	        String allContent = header + content + footer;
	        
	        messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
	        multipart.addBodyPart(messageBodyPart);
	        
	        messageBodyPart = new MimeBodyPart();
	        DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
	        messageBodyPart.setDataHandler(new DataHandler(fds1));
	        messageBodyPart.setHeader("Content-ID","<image1>");
	        messageBodyPart.setFileName("ESPRIT.png");
	        multipart.addBodyPart(messageBodyPart);
	        
//	        System.out.println("-------------------------> HOSTS STN - 3.6");
	        
	        message.setContent(multipart);
//	        System.out.println("-------------------------> HOSTS STN - 3.7");
			Transport.send(message);
//			System.out.println("-------------------------> HOSTS STN - 3.8");
		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
//		System.out.println("-------------------------> HOSTS STN - 3.9");
	
	}
	
	public void sendMailStudentWithRSSandCPS(String subject, String student, String respMembers, String dateSoutenance, String selectedStartHour,
			String selectedEndHour, String meetLink, String nomJP, String nomPE, String nomEXP, String salle,
			String nameRSS) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(student));

			InternetAddress[] parsecc = InternetAddress.parse(respMembers, true);
			message.addRecipients(Message.RecipientType.CC, parsecc);
			
			
			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en présentiel dans la salle <font color=blue> " + salle + " </font>" + "(Site ElGhazela) ."

					+ " <br/>Vous êtes invité(e) à vous présenter devant la salle 15min avant le début de votre soutenance. "
					// + " <br/>Les soutenances se déroulent à huis clos. "
					+ " <br/>Je vous souhaite une excellente soutenance."
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Conformément à l'invitation envoyée, <strong><font color=grey> votre soutenance PFE aura lieu </font></strong> "
					+ "le <font color=blue> " + dateSoutenance + " </font> " + "de <font color=blue> "
					+ selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en distanciel via  <font color=blue> <a href=\"" + meetLink
					+ "\">le lien Meet</a> </font>" + "."

					+ " <br/>Vous êtes invité(e) à vous présenter via Meet 15min avant le début de votre soutenance. "
					// + " <br/>Les soutenances se déroulent à huis clos. "
					+ " <br/>Je vous souhaite une excellente soutenance."
					+ " <img goomoji=\"1f393\" data-goomoji=\"1f393\" style=\"margin:0 0.2ex;vertical-align:middle;max-height:24px\" alt=\"🎓\" src=\"https://mail.google.com/mail/e/1f393\" data-image-whitelisted=\"\" class=\"CToWUd\">"
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
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

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailJuryPresidentWithRSSandCPS(String subject, String student, String juryMembers, String respMembers, String dateSoutenance,
			String selectedStartHour, String selectedEndHour, String meetLink, String nomJP, String nomPE,
			String nomEXP, String salle, String sharedStudentESPReport, String nameRSS) {
		// System.out.println("-------------------------> HOSTS STN - 3.1");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// System.out.println("-------------------------> HOSTS STN - 3.2");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		// System.out.println("-------------------------> HOSTS STN - 3.3");

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));

			// System.out.println("-------------------------> HOSTS STN - 3.4");
			InternetAddress[] parseto = InternetAddress.parse(juryMembers, true);
			message.addRecipients(Message.RecipientType.TO, parseto);
			
			InternetAddress[] parsecc = InternetAddress.parse(respMembers, true);
			message.addRecipients(Message.RecipientType.TO, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String content = null;
			String contentNormal = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membres du jury en présentiel dans la salle <font color=blue> " + salle
					+ " </font>" + "(Site ElGhazela) ."
					+ "<br/>" + "Veuillez cliquer sur <font color=blue> <a href=\""
					+ sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport de l'étudiant(e)</font>."
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			String contentMeet = "Nous voulons vous informer que vous êtes invités à la soutenance de  PFE de <strong><font color=#b30000> "
					+ student + " </font></strong>, " + "le <font color=blue> " + dateSoutenance + " </font> "
					+ "de <font color=blue> " + selectedStartHour + " </font> à <font color=blue> " + selectedEndHour
					+ " </font> en tant que membre des jurys en présentiel dans la salle via <font color=blue> <a href=\""
					+ meetLink + "\">le lien Meet</a> </font>." + "<br/>"
					+ "Veuillez cliquer sur <font color=blue> <a href=\"" + sharedStudentESPReport
					+ "\"> ce lien</a> </font> pour consulter <font color=grey> le Rapport de l'étudiant(e)</font>."
					+ " <br/>" + " <br/> <strong><font color=#b30000> La composition du jury:  </font></strong>"
					+ " <br/> <strong><font color=grey> - Président du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomJP + " </font>"
					+ " <br/> <strong><font color=grey> - Encadrant Académique: </font></strong> <font color=#404040> Mme/M "
					+ nomPE + " </font>"
					+ " <br/> <strong><font color=grey> - Membre du Jury: </font></strong> <font color=#404040> Mme/M "
					+ nomEXP + " </font> <br/><br/>";

			if (meetLink.equalsIgnoreCase("NOMEET")) {
				content = contentNormal;
			} else {
				content = contentMeet;
			}

			String footer = "<br/><br/>Cordialement / Best Regards.<br/>***********************************<br/>"
					+ "<strong><font color=black> " + nameRSS + " </font></strong><br/>"
					+ "<strong><font color=black>Département des stages ESPRIT</font></strong><br/>"
					+ "Fax     : +216 70 685 685<br/>" + "Adresse : Z.I. Chotrana II - B.P. 160 - 2083<br/>"
					+ "Pôle Technologique - El Ghazala, Tunis, Tunisia<br/>"
					+ "Google Maps: <a href=\"https://www.google.tn/maps/place/36%C2%B053'57.6%22N+10%C2%B011'22.1%22E/@36.8993333,10.1872835,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d36.899327!4d10.189464\">36.899327, 10.189464</a><br/><br/>"
					+ "<img src=\"cid:image1\" height=\"70\" width=\"150\">";
			// System.out.println("-------------------------> HOSTS STN - 3.5");
			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds1));
			messageBodyPart.setHeader("Content-ID", "<image1>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);

			// System.out.println("-------------------------> HOSTS STN - 3.6");

			message.setContent(multipart);
			// System.out.println("-------------------------> HOSTS STN - 3.7");
			Transport.send(message);
			// System.out.println("-------------------------> HOSTS STN - 3.8");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		// System.out.println("-------------------------> HOSTS STN - 3.9");

	}



}
