package murach.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {

    public static void sendMail(String to, String from, String subject, String body, boolean isHtml)
            throws MessagingException, UnsupportedEncodingException {

        // 1. Cấu hình Properties cho Brevo SMTP Server (Dùng Port 587 với STARTTLS)
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-relay.brevo.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Bật STARTTLS cho cổng 587

        // Cấu hình timeout để tránh bị treo ứng dụng khi mạng chập chờn
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.connectiontimeout", "10000");

        // 2. Thông tin tài khoản SMTP từ Brevo của bạn
        final String username = "baooha9600@gmail.com"; // Ví dụ: baooha9600@gmail.com
        final String password = "smtp-relay.brevo.com"; // Đoạn mã SMTP key dài

        // 3. Tạo Session xác thực
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Bật debug để in log chi tiết nếu cần kiểm tra
        session.setDebug(true);

        // 4. Khởi tạo và điền thông tin Email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, "Email List Service", "UTF-8"));
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