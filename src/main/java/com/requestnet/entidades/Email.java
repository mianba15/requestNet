package com.requestnet.entidades;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    
    public boolean enviarCorreo (String para, String asunto, String mensaje ) {
        
        boolean enviado = false;
        
        final String de = "requestnet.info@gmail.com";
        final String clave = "1nf0r3qUestn3T";

        String[] mails;
        mails = para.split(";");
        String host = "smtp.gmail.com";
        
        Properties props = new Properties();

        // Defining properties
        props.put("mail.smtp.host", host);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.user", de);
        props.put("mail.password", clave);
        props.put("mail.port", "587");
        
        // Authorized the Session object.
        Session sesion = Session.getInstance(props, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(de, clave);
            }
        });
        
        try {
            
            MimeMessage message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(de));

            for (int i = 0; i < mails.length; i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mails[i]));
            }

            message.setSubject(asunto);
            message.setText(mensaje);
            
            Transport transport = sesion.getTransport("smtp");
            transport.connect(host,de,clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            enviado = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return enviado;
    }
}
