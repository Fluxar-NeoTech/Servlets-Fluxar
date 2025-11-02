package com.example.servletfluxar.util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EnvioEmail {

    public static void enviarEmail(String destinatario, String assunto, String mensagemHtml) throws Exception {
//        Declaração de variáveis - Remetente e senha não estão aqui pois precisam ser final:
        Dotenv dotenv = null;
        Properties props = new Properties();
        Session session = null;
        Message message = null;

//        Tenta buscar o .env sem falhar caso o ambiente seja de produção:
        try {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            dotenv = null;
        }


//       gmail que está no .env que será o remetente do email
        final String remetente = System.getenv("EMAIL") != null
                ? System.getenv("EMAIL")
                : (dotenv != null ? dotenv.get("EMAIL") : null);

//        Senha de aplicativo cadastrada também no .env (não é a senha real do email)
        final String senha = System.getenv("SENHA_EMAIL") != null
                ? System.getenv("SENHA_EMAIL")
                : (dotenv != null ? dotenv.get("SENHA_EMAIL") : null);

//         Configurações de propriedades para a conexão SMTP (protocolo de envio de e-mails)
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

//          Cria uma sessão de autenticação com as credenciais do remetente
        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

//          Cria a mensagem de e-mail
        message = new MimeMessage(session);

//        Definindo informações acerca do remetente, destinatário, assunto e mensagem com corpo de html:
        message.setFrom(new InternetAddress(remetente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(assunto);
        message.setContent(mensagemHtml, "text/html; charset=utf-8");

//        Enviando o email atráves do SMTP configurado:
        Transport.send(message);
    }
}