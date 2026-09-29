<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Murach's Java Servlets and JSP - Cảm ơn</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Segoe UI', -apple-system, BlinkMacSystemFont, Roboto, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .card {
            background-color: #ffffff;
            width: 100%;
            max-width: 500px;
            padding: 36px 32px;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
            text-align: center;
        }

        .icon-success {
            width: 64px;
            height: 64px;
            background-color: #e6fffa;
            color: #0d9488;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 32px;
            margin: 0 auto 20px;
            font-weight: bold;
        }

        h1 {
            color: #111827;
            font-size: 24px;
            font-weight: 700;
            margin-bottom: 8px;
        }

        p.subtitle {
            color: #6b7280;
            font-size: 15px;
            margin-bottom: 28px;
        }

        .info-box {
            background-color: #f9fafb;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            padding: 20px;
            text-align: left;
            margin-bottom: 28px;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px dashed #e5e7eb;
        }

        .info-row:last-child {
            border-bottom: none;
        }

        .info-label {
            font-weight: 600;
            color: #6b7280;
            font-size: 14px;
        }

        .info-value {
            font-weight: 600;
            color: #111827;
            font-size: 14px;
            word-break: break-all;
        }

        .btn-back {
            display: inline-block;
            width: 100%;
            padding: 12px 24px;
            background-color: #0d9488;
            color: #ffffff;
            text-decoration: none;
            font-weight: 600;
            font-size: 15px;
            border-radius: 8px;
            transition: background-color 0.2s ease, transform 0.1s ease;
        }

        .btn-back:hover {
            background-color: #0f766e;
        }

        .btn-back:active {
            transform: scale(0.98);
        }
    </style>
</head>
<body>

    <div class="card">
        <div class="icon-success">✓</div>
        <h1>Cảm ơn bạn đã đăng ký!</h1>
        <p class="subtitle">Thông tin của bạn đã được lưu thành công vào cơ sở dữ liệu.</p>

        <div class="info-box">
            <div class="info-row">
                <span class="info-label">Email</span>
                <span class="info-value">${user.email}</span>
            </div>
            <div class="info-row">
                <span class="info-label">First Name</span>
                <span class="info-value">${user.firstName}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Last Name</span>
                <span class="info-value">${user.lastName}</span>
            </div>
        </div>

        <a href="emailList?action=join" class="btn-back">Quay lại trang đăng ký</a>
    </div>

</body>
</html>