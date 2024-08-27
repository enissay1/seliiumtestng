package lu.distri2b;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

import static lu.distri2b.CustomTestListener.*;

public class MailtrapEmailExample {

    public static void sendEmailWithAttachment(File reportFile) {
        // Paramètres de Mailtrap
        String smtpHost = "127.0.0.1"; //"smtp.mailtrap.io";
        int smtpPort = 1025; // Port Mailtrap pour SMTP
        String username = ""; // Remplacez par votre username Mailtrap
        String password = ""; // Remplacez par votre password Mailtrap
        String fromEmail = "from@example.com";
        String toEmail = "to@example.com";
        String subject = "Test Report";
        String body = "<h2 style='font-family:Arial, sans-serif; color:blue;'>Statistiques des tests:</h2>\n" +
                "<p style='font-family:Verdana, sans-serif; font-size:14px;'>Nombre total de tests: " + totalTests + "</p>\n" +
                "<p style='font-family:Verdana, sans-serif; font-size:14px;color:green;'>Nombre total de tests réussis: " + totalPassedTests + "</p>\n" +
                "<p style='font-family:Verdana, sans-serif; font-size:14px; color:red;'>Nombre total de tests échoués: " + totalFailedTests + "</p>\n" +
                "<p style='font-family:Verdana, sans-serif; font-size:14px; color:orange'>Nombre total de tests ignorés: " + totalSkippedTests + "</p>\n\n" +
                "<p style='font-family:Arial, sans-serif; '>Veuillez trouver en pièce jointe le rapport de test.</p>";



        // Propriétés SMTP pour JavaMail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Créer une partie pour le texte de l'email
            BodyPart messageBodyPart = new MimeBodyPart();
            //messageBodyPart.setText(body);
            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            // Créer une partie pour la pièce jointe
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(reportFile);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(reportFile.getName());

            // Créer un multipart pour combiner les parties
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Ajouter le multipart au message
            message.setContent(multipart);

            // Envoyer l'email
            Transport.send(message);

            System.out.println("Email envoyé avec succès!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
