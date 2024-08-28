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
        String msg = "";
        String msgSuccess="";
        if(totalFailedTests != 0 || totalSkippedTests != 0){
            msg="<h2 style=\"padding: 10px; border: 1px solid #ddd;background-color: red\">Test failed</h2>\n"
            ;
        }else msgSuccess="<h2 style=\"padding: 10px; border: 1px solid #ddd;background-color: green\">Test success</h2>\n";
        String body = "<h1>Rapport des Tests</h1>\n" +
                "<h2 style='font-family:Arial, sans-serif; color:blue;'>Nombre total de tests:  "+ totalTests + "</h2>\n"  +

                "<table style=\"width: 100%; border-collapse: collapse; margin: 20px 0;\">\n" +
                "        <thead>\n" +
                "            <tr style=\"background-color: #f4f4f4;\">\n" +
                "                <th style=\"padding: 10px; text-align: left; border: 1px solid #ddd;\">Type de Test</th>\n" +
                "                <th style=\"padding: 10px; text-align: left; border: 1px solid #ddd;\">Nombre de test </th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "            <tr style=\"background-color: #d4edda;\">\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd;\">Tests Réussis</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd;\">Nombre total de tests réussis : <span id=\"totalPassedTests\">"+totalPassedTests+"</span></td>\n" +
                "            </tr>\n" +
                "            <tr style=\"background-color: #f8d7da;\">\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd;\">Tests Échoués</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd;\">Nombre total de tests échoués : <span id=\"totalFailedTests\">"+totalFailedTests+"</span></td>\n" +
                "            </tr>\n" +
                "            <tr style=\"background-color: #fff3cd;\">\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd;\">Tests Ignorés</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd;\">Nombre total de tests ignorés : <span id=\"totalSkippedTests\">"+totalSkippedTests+"</span></td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>" +
                      msg + msgSuccess
                +
                "    <h2>Vue d'ensemble</h2>\n" +
                "    <p>\n" +
                "        Le scénario de test automatisé pour l'application web <a href=\"https://bo.distri2b.lu\">https://bo.distri2b.lu</a>, couvrant les principales fonctionnalités de gestion des agents. Les tests ont été conçus pour vérifier la connexion, l'ajout, le filtrage, la pagination et la modification des agents. Les résultats indiquent si chaque fonctionnalité est opérationnelle et conforme aux attentes.\n" +
                "    </p>\n" +
                "    \n" +
                "    <h2>Détails des Tests</h2>\n" +
                "    <ol>\n" +
                "        <li>\n" +
                "            <strong>Connexion au Système</strong>\n" +
                "            <ul>\n" +
                "                <li><strong>Objectif :</strong> Vérifier la capacité à se connecter à l'application.</li>\n" +
                "                <li><strong>Résultat :</strong> <span class=\"resultat\">Réussi.</span> L'élément de tableau de bord a été trouvé après la connexion, confirmant une connexion réussie.</li>\n" +
                "            </ul>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <strong>Ajout d'un Agent</strong>\n" +
                "            <ul>\n" +
                "                <li><strong>Objectif :</strong> Ajouter un nouvel agent et vérifier la confirmation.</li>\n" +
                "                <li><strong>Résultat :</strong> <span class=\"resultat\">Réussi.</span> L'agent a été ajouté avec succès, et le message de confirmation a été correctement affiché.</li>\n" +
                "            </ul>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <strong>Filtrage des Agents</strong>\n" +
                "            <ul>\n" +
                "                <li><strong>Objectif :</strong> Filtrer les agents par nom d'utilisateur et vérifier les résultats.</li>\n" +
                "                <li><strong>Résultat :</strong> <span class=\"resultat\">Réussi.</span> Le filtre a correctement renvoyé le nombre attendu d'agents.</li>\n" +
                "            </ul>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <strong>Pagination des Agents</strong>\n" +
                "            <ul>\n" +
                "                <li><strong>Objectif :</strong> Naviguer à travers les pages pour trouver un agent spécifique.</li>\n" +
                "                <li><strong>Résultat :</strong> <span class=\"resultat\">Réussi.</span> L'agent recherché a été trouvé après navigation et pagination à travers les pages.</li>\n" +
                "            </ul>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <strong>Modification d'un Agent</strong>\n" +
                "            <ul>\n" +
                "                <li><strong>Objectif :</strong> Modifier les détails d'un agent existant et vérifier les changements.</li>\n" +
                "                <li><strong>Résultat :</strong> <span class=\"resultat\">Réussi.</span> Les modifications ont été correctement enregistrées et le message de succès a été affiché.</li>\n" +
                "            </ul>\n" +
                "        </li>\n" +
                "    </ol>\n" +
                "    \n" +
                "    <h2>Conclusion</h2>\n" +
                "    <p>\n" +
                "        Les tests ont été exécutés avec succès, confirmant que les principales fonctionnalités de l'application sont opérationnelles. Les interactions avec l'application ont été vérifiées pour la connexion, l'ajout, le filtrage, la pagination et la modification des agents. Les résultats sont conformes aux attentes, et les tests ont fourni des confirmations adéquates pour chaque fonctionnalité testée.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        Dans le cas où certains tests auraient échoué, les résultats défaillants seront documentés en détail dans le rapport, accompagné de captures d'écran des parties échouées.\n" +
                "    </p>" ;



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
