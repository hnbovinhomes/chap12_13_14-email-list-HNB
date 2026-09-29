package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import murach.business.User;
import murach.db.UserDB;
import murach.util.MailUtilRender;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String url = "/index.jsp";

        // Lấy action từ form
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // Hành động mặc định
        }

        // Xử lý các action
        if (action.equals("join")) {
            url = "/index.jsp";    // Trang nhập form
        }
        else if (action.equals("add")) {
            // 1. Lấy dữ liệu từ form HTML
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // 2. Tạo đối tượng User
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            String message;
            if (firstName == null || lastName == null || email == null ||
                    firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {

                message = "Vui lòng điền đầy đủ tất cả các trường thông tin.";
                url = "/index.jsp";
            }
            else {
                message = "";

                // 3. Lưu thông tin người dùng vào PostgreSQL trên Render
                UserDB.insert(user);

                // 4. Gửi email xác nhận tự động bằng HTTP API (lách luật chống chặn port SMTP của Render)
                try {
                    String subject = "Xác nhận đăng ký nhận tin thành công";
                    String body = "Xin chào " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
                            + "Cảm ơn bạn đã đăng ký tham gia danh sách email của chúng tôi!\n"
                            + "Thông tin đăng ký của bạn đã được ghi nhận thành công trên hệ thống.\n\n"
                            + "Trân trọng,\nEmail List Team";

                    // Gọi hàm gửi mail qua Brevo REST API (Thay "email_da_verify_tren_brevo@gmail.com" bằng email đã xác thực trên tài khoản Brevo của bạn)
                    MailUtilRender.sendMail(user.getEmail(), "baooha9600@gmail.com", subject, body, false);

                } catch (Exception e) {
                    // In lỗi ra log console nếu việc gửi email gặp sự cố
                    System.err.println("Lỗi khi gửi email qua HTTP API: " + e.getMessage());
                    e.printStackTrace();
                }

                url = "/thanks.jsp";
            }

            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }

        // Chuyển hướng view tương ứng
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}