<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Murach's Java Servlets and JSP - Email List</title>
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
            align-items: flex-start;
            min-height: 100vh;
            padding: 40px 20px;
        }

        .container {
            background-color: #ffffff;
            width: 100%;
            max-width: 600px;
            padding: 32px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
        }

        h1 {
            color: #0d9488;
            font-size: 26px;
            font-weight: 700;
            margin-bottom: 8px;
        }

        p.subtitle {
            color: #6b7280;
            font-size: 15px;
            margin-bottom: 24px;
        }

        .form-group {
            margin-bottom: 16px;
        }

        label {
            display: block;
            font-weight: 600;
            font-size: 14px;
            color: #374151;
            margin-bottom: 6px;
        }

        input[type="text"], input[type="email"] {
            width: 100%;
            padding: 10px 14px;
            font-size: 14px;
            color: #1f2937;
            background-color: #f9fafb;
            border: 1.5px solid #d1d5db;
            border-radius: 8px;
            outline: none;
            transition: all 0.2s ease-in-out;
        }

        input[type="text"]:focus, input[type="email"]:focus {
            border-color: #0d9488;
            background-color: #ffffff;
            box-shadow: 0 0 0 4px rgba(13, 148, 136, 0.15);
        }

        .btn-submit {
            background-color: #0d9488;
            color: white;
            font-weight: 600;
            font-size: 15px;
            padding: 10px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            margin-top: 10px;
            width: 100%;
            transition: background-color 0.2s ease;
        }

        .btn-submit:hover {
            background-color: #0f766e;
        }

        .message {
            color: #dc2626;
            font-size: 14px;
            margin-bottom: 16px;
            font-weight: 500;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Join our email list</h1>
        <p class="subtitle">To join our email list, enter your name and email address below.</p>

        <% if (request.getAttribute("message") != null) { %>
            <div class="message"><%= request.getAttribute("message") %></div>
        <% } %>

        <form action="emailList" method="post">
            <input type="hidden" name="action" value="add">

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" required>
            </div>

            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
            </div>

            <input type="submit" value="Join Now" class="btn-submit">
        </form>
    </div>

</body>
</html>