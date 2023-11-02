package com.example.demo;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
    public void sendEmail () {
        System.out.println("passe");
        // Paramètres de configuration du serveur de messagerie
        String host = "sandbox.smtp.mailtrap.io";
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
            message.setSubject("Sujet de l'e-mail");
            message.setText("Contenu de l'e-mail");

            // Envoyez l'e-mail
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        // Point d'entrée principal de l'application
        // Vous pouvez appeler sendEmail ici si vous le souhaitez
        SendEmail emailSender = new SendEmail();
        emailSender.sendEmail();
    }
}
