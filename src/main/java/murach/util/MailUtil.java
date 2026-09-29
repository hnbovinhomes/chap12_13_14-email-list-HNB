package murach.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {

    private static final String REMITTER_EMAIL = "email-cua-ban@gmail.com";
    private static final String APP_PASSWORD = "xxxx xxxx xxxx xxxx"; // 16 ký tự App Password

    public static void sendMail(String to, String subject, String body)
            throws MessagingException, UnsupportedEncodingException {

        Properties props = new Properties();

        // Cấu hình chuyển từ Port 587 TLS sang Port 465 SSL
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true"); // Bật SSL bắt buộc cho port 465
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.timeout", "5000"); // Timeout 5 giây nếu không kết nối được
        props.put("mail.smtp.connectiontimeout", "5000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITTER_EMAIL, APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(REMITTER_EMAIL, "Email List Service", "UTF-8"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}