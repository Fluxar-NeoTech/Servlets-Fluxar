package util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class EmailService {
    private static final Dotenv dotenv = Dotenv.load();

    public static void enviarEmail(String destinatario, String assunto, String mensagemHtml) throws Exception {
        String remetente = dotenv.get("EMAIL"); // @gmail.com que está no .env
        String senha = dotenv.get("SENHA_EMAIL"); // SENHA DE APP do Gmail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remetente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(assunto);
        message.setContent(mensagemHtml, "text/html; charset=utf-8");

        Transport.send(message);
    }
}