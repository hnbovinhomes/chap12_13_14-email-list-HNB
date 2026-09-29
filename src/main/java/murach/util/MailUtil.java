package murach.util;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailUtil {

    public static void sendMail(String to, String from, String subject, String body, boolean isHtml)
            throws MessagingException {

        // 1. Cấu hình Properties cho Google SMTP Server (Port 465 SSL)
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Thêm cấu hình timeout để tránh bị treo ứng dụng khi mạng chậm
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.connectiontimeout", "10000");

        // 2. Thông tin tài khoản Gmail gửi thư
        final String username = "baooha9600@gmail.com";
        final String password = "mrdn kjme sevy fbtn"; // App Password 16 ký tự

        // 3. Tạo Session xác thực
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Bật debug để xem log chi tiết khi gửi mail trên Render
        session.setDebug(true);

        // 4. Khởi tạo và điền thông tin Email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        if (isHtml) {
            message.setContent(body, "text/html; charset=utf-8");
        } else {
            message.setText(body);
        }

        // 5. Tiến hành gửi mail
        Transport.send(message);
    }
}