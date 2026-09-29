package murach.util;

import jakarta.mail.MessagingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MailUtilRender {

    public static void sendMail(String to, String from,
                                String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        try {
            // 1. Đọc API Key từ Biến môi trường trên Render hoặc Local
            String apiKey = System.getenv("BREVO_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                throw new Exception("Không tìm thấy biến môi trường BREVO_API_KEY!");
            }

            // 2. Làm sạch dữ liệu truyền vào để tránh lỗi cú pháp JSON
            String cleanFrom = from.trim();
            String cleanTo = to.trim();
            String cleanSubject = subject.replace("\"", "\\\"");
            String cleanBody = body.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "");

            // 3. Đóng gói dữ liệu thành chuỗi JSON payload
            String jsonPayload = "{"
                    + "\"sender\":{\"email\":\"" + cleanFrom + "\"},"
                    + "\"to\":[{\"email\":\"" + cleanTo + "\"}],"
                    + "\"subject\":\"" + cleanSubject + "\","
                    + (bodyIsHTML ? "\"htmlContent\":\"" : "\"textContent\":\"") + cleanBody + "\""
                    + "}";

            // 4. Gọi Brevo API thông qua cổng HTTPS chuẩn (Cổng 443 - Không bao giờ bị chặn)
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("Accept", "application/json")
                    .header("api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // 5. Gửi request và kiểm tra mã phản hồi từ server Brevo
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new MessagingException("Lỗi Brevo API (Mã " + response.statusCode() + "): " + response.body());
            }

        } catch (Exception e) {
            throw new MessagingException("Không thể gửi mail qua HTTP API: " + e.getMessage(), e);
        }
    }
}