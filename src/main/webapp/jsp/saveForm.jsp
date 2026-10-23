<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавление пользователей</title>
</head>
<body>
<h2>Добавление пользователей</h2>

<form action="${pageContext.request.contextPath}/saveBatch" method="post">
    <c:forEach begin="0" end="${count - 1}" var="i">
        <fieldset>
            <legend>Пользователь ${i + 1}</legend>
            Имя: <input name="first_name_${i}" required><br>
            Фамилия: <input name="last_name_${i}" required><br>
            Возраст: <input type="number" name="age_${i}" min="0" required><br>
            Количество штрафов: <input type="number" name="fines_num_${i}" min="0" required><br>
            Стаж вождения: <input type="number" name="exp_years_${i}" min="0" required><br>
            Количество машин: <input type="number" name="cars_num_${i}" min="0" required><br>
        </fieldset>
        <br>
    </c:forEach>
    <button type="submit">Сохранить</button>
</form>
</body>
</html>
