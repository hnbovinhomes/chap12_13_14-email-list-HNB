package murach.email;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.MessagingException;

import murach.business.User;
import murach.db.UserDB;
import murach.util.MailUtilLocal;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/index.jsp";

        // Lấy giá trị tham số 'action' từ request (Chương 12)
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // Hành động mặc định
        }

        // Xử lý các hành động
        if (action.equals("join")) {
            url = "/index.jsp";    // Trang nhập thông tin đăng ký
        }
        else if (action.equals("add")) {
            // 1. Nhận dữ liệu từ form
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // 2. Tạo đối tượng User và gắn dữ liệu
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            // 3. Lưu đối tượng User vào PostgreSQL bằng JPA (Chương 13)
            UserDB.insert(user);

            // 4. Gửi email xác nhận tự động qua Jakarta Mail (Chương 14)
            String to = email;
            String from = "your_email@gmail.com"; // Địa chỉ email gửi của bạn
            String subject = "Welcome to our Email List";
            String body = "Dear " + firstName + ",\n\n" +
                    "Thanks for joining our email list. We'll send you " +
                    "notifications of new releases.\n\n" +
                    "Have a great day!";
            boolean isHtml = false;

            try {
                MailUtilLocal.sendMail(to, from, subject, body, isHtml);
            } catch (MessagingException e) {
                String message = "Unable to send email. Error: " + e.getMessage();
                this.log(message); // Ghi log vào file log của Tomcat
                e.printStackTrace();
            }

            // 5. Lưu thông tin user vào request object để hiển thị ở thanks.jsp
            request.setAttribute("user", user);
            url = "/thanks.jsp";
        }

        // Chuyển tiếp (forward) request và response đến trang JSP thích hợp (Chương 12)
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