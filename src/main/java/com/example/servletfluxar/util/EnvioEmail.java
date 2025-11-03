package com.example.servletfluxar.util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import jakarta.activation.*;
import java.io.File;

public class EnvioEmail {

    public static void enviarEmail(String destinatario, String assunto, String mensagemHtml) throws Exception {
        enviarEmail(destinatario, assunto, mensagemHtml, null);
    }

    public static void enviarEmail(String destinatario, String assunto, String mensagemHtml, String caminhoArquivo) throws Exception {
        Dotenv dotenv = null;
        Properties props = new Properties();

        // Tenta buscar o .env sem falhar caso o ambiente seja de produção:
        try {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            dotenv = null;
        }

        // Gmail que está no .env que será o remetente do email
        final String remetente = System.getenv("EMAIL") != null
                ? System.getenv("EMAIL")
                : (dotenv != null ? dotenv.get("EMAIL") : null);

        // Senha de aplicativo cadastrada também no .env
        final String senha = System.getenv("SENHA_EMAIL") != null
                ? System.getenv("SENHA_EMAIL")
                : (dotenv != null ? dotenv.get("SENHA_EMAIL") : null);

        // Configurações de propriedades para a conexão SMTP
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Cria uma sessão de autenticação com as credenciais do remetente
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        // Cria a mensagem de e-mail
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remetente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(assunto);

        // Cria o corpo do email com anexo
        Multipart multipart = new MimeMultipart();

        // Parte 1: Corpo HTML
        MimeBodyPart corpoHtml = new MimeBodyPart();
        corpoHtml.setContent(mensagemHtml, "text/html; charset=utf-8");
        multipart.addBodyPart(corpoHtml);

        // Parte 2: Anexo (se fornecido)
        if (caminhoArquivo != null && !caminhoArquivo.isEmpty()) {
            File arquivo = new File(caminhoArquivo);
            if (arquivo.exists()) {
                MimeBodyPart anexo = new MimeBodyPart();
                DataSource source = new FileDataSource(arquivo);
                anexo.setDataHandler(new DataHandler(source));
                anexo.setFileName(arquivo.getName());
                multipart.addBodyPart(anexo);
            } else {
                throw new Exception("Arquivo não encontrado: " + caminhoArquivo);
            }
        }

        // Define o conteúdo da mensagem
        message.setContent(multipart);

        // Enviando o email através do SMTP configurado
        Transport.send(message);
    }
}