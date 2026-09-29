# Sử dụng Tomcat chính thức chạy trên nền Java 17
FROM tomcat:10.1-jdk17

# Xóa các ứng dụng mặc định sẵn có của Tomcat để tránh xung đột
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR sau khi build vào thư mục webapps và đổi tên thành ROOT.war
# (Để app chạy trực tiếp ngay ở domain chính mà không cần thêm tên path)
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng 8080 mặc định của Tomcat
EXPOSE 8080

# Khởi động Tomcat
CMD ["catalina.sh", "run"]