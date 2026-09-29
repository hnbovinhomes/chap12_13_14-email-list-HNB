package murach.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {

    // 1. Thay bằng email Gmail của bạn
    private static final String REMITTER_EMAIL = "email-cua-ban@gmail.com";

    // 2. Thay bằng Mật khẩu ứng dụng 16 ký tự của Gmail (App Password)
    private static final String APP_PASSWORD = "xxxx xxxx xxxx xxxx";

    public static void sendMail(String to, String subject, String body)
            throws MessagingException, UnsupportedEncodingException {

        // Cấu hình kết nối tới Google SMTP Server
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Xác thực tài khoản
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITTER_EMAIL, APP_PASSWORD);
            }
        });

        // Tạo nội dung email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(REMITTER_EMAIL, "Email List Service", "UTF-8"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);

        // Thực hiện gửi
        Transport.send(message);
    }
}