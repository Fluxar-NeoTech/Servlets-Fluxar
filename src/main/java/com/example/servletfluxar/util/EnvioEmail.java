package com.example.servletfluxar.util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EnvioEmail {
    private static final Dotenv dotenv = Dotenv.load();

    public static void enviarEmail(String destinatario, String assunto, String mensagemHtml) throws Exception {
//       gmail que está no .env que será o remetente do email
        String remetente = dotenv.get("EMAIL");

//        Senha de aplicativo cadastrada também no .env (não é a senha real do email)
        String senha = dotenv.get("SENHA_EMAIL");

//         Configurações de propriedades para a conexão SMTP (protocolo de envio de e-mails)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

//          Cria uma sessão de autenticação com as credenciais do remetente
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

//          Cria a mensagem de e-mail
        Message message = new MimeMessage(session);

//        Definindo informações acerca do remetente, destinatário, assunto e mensagem com corpo de html:
        message.setFrom(new InternetAddress(remetente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(assunto);
        message.setContent(mensagemHtml, "text/html; charset=utf-8");

//        Enviando o email atráves do SMTP configurado:
        Transport.send(message);
    }
}