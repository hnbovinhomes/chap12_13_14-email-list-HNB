package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sqlGateway")
public class SqlGatewayServlet extends HttpServlet {

    // Thay 'yourPassword' thành mật khẩu tài khoản postgres của bạn
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/MurachDB";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "hnb121618";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";

        if (sqlStatement != null && !sqlStatement.trim().isEmpty()) {
            try {
                Class.forName("org.postgresql.Driver");

                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     Statement statement = connection.createStatement()) {

                    boolean isResultSet = statement.execute(sqlStatement);

                    if (isResultSet) {
                        // Trường hợp câu lệnh SELECT -> Hiển thị dạng Bảng HTML
                        ResultSet resultSet = statement.getResultSet();
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        StringBuilder htmlBuilder = new StringBuilder();
                        htmlBuilder.append("<table border='1' style='border-collapse: collapse; margin-top: 10px;'>");

                        htmlBuilder.append("<tr>");
                        for (int i = 1; i <= columnCount; i++) {
                            htmlBuilder.append("<th style='padding: 6px 12px; background-color: #f2f2f2;'>")
                                    .append(metaData.getColumnName(i))
                                    .append("</th>");
                        }
                        htmlBuilder.append("</tr>");

                        while (resultSet.next()) {
                            htmlBuilder.append("<tr>");
                            for (int i = 1; i <= columnCount; i++) {
                                htmlBuilder.append("<td style='padding: 6px 12px;'>")
                                        .append(resultSet.getString(i))
                                        .append("</td>");
                            }
                            htmlBuilder.append("</tr>");
                        }
                        htmlBuilder.append("</table>");
                        sqlResult = htmlBuilder.toString();
                        resultSet.close();
                    } else {
                        // Trường hợp INSERT, UPDATE, DELETE... -> Hiển thị số dòng ảnh hưởng
                        int count = statement.getUpdateCount();
                        if (count >= 0) {
                            sqlResult = "The statement executed successfully.<br>" + count + " row(s) affected.";
                        } else {
                            sqlResult = "The statement executed successfully.";
                        }
                    }
                }
            } catch (Exception e) {
                sqlResult = "<p style='color:red;'>Error executing SQL statement: " + e.getMessage() + "</p>";
            }
        }

        request.setAttribute("sqlResult", sqlResult);
        request.setAttribute("sqlStatement", sqlStatement);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}