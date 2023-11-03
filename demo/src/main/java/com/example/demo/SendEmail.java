package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class SendEmail {
    public void sendEmail(String MessagePassword) {
        // Paramètres de configuration du serveur de messagerie
        String host = "smtp.mailtrap.io";
        String port = "587";
        String username = "5aded24997a318";
        String password = "27ac2e0b598ae5";

        // Propriétés pour la session de messagerie
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Créez une session de messagerie avec authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créez un objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("destinataire@example.com"));
            message.setSubject("Inscription");

            TemplateEngine templateEngine = new TemplateEngine();
            Context context = new Context();
            context.setVariable("MessagePassword", MessagePassword); // Remplacez par la valeur de votre variable MessagePassword

            // Chargez le contenu HTML depuis le fichier
            String htmlContent = null;
            try (BufferedReader br = new BufferedReader(new FileReader("D:\\Java\\COGIP-2\\COGIP\\demo\\src\\main\\resources\\templates\\mail.html"))) {
                htmlContent = br.lines().collect(Collectors.joining(System.lineSeparator()));
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier HTML : " + e.getMessage(), e);
            }

            // Générez le contenu HTML en remplaçant la variable Thymeleaf
            String emailContent = templateEngine.process(htmlContent, context);

            // Créez la partie du message au format HTML
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailContent, "text/html");

            // Créez un message multipart et ajoutez la partie HTML
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Définissez le contenu du message
            message.setContent(multipart);

            // Envoyez l'e-mail
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}
