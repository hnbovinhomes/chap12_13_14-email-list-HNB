package murach.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {

    public static void sendMail(String to, String from, String subject, String body, boolean isHtml)
            throws MessagingException, UnsupportedEncodingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-relay.brevo.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.connectiontimeout", "10000");

        // Lấy thông tin bảo mật từ biến môi trường của hệ thống (Render/Local)
        final String username = System.getenv("BREVO_SMTP_USER");
        final String password = System.getenv("BREVO_SMTP_PASS");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, "Email List Service", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        if (isHtml) {
            message.setContent(body, "text/html; charset=utf-8");
        } else {
            message.setText(body);
        }

        Transport.send(message);
    }
}