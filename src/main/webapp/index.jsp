<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f9f9f9; }
        h2 { color: #333; }
        ul { list-style-type: none; padding: 0; }
        li { margin: 10px 0; }
        a {
            display: inline-block; padding: 10px 20px;
            background-color: #007bff; color: white;
            text-decoration: none; border-radius: 5px;
            transition: background-color 0.3s;
        }
        a:hover { background-color: #0056b3; }
    </style>
</head>
<body>
<h2>Добро пожаловать!</h2>
<p>Выберите действие:</p>
<ul>
    <li><a href="./saveUsers">➕ Добавить водителей (пошагово)</a></li>
    <li><a href="./save">➕ Добавить пачкой (N из БД → redirect)</a></li>
    <li><a href="./selectUsers">🔍 Найти водителей (JSP)</a></li>
    <li><a href="./searchConsole">🖥 Поиск в консоль</a></li>
    <li><a href="./users">📋 Все водители</a></li>
</ul>
</body>
</html>
