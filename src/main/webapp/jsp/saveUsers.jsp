<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Сохранение пользователей</title>
</head>
<body>
    <h2>Введите данные</h2>
    <div>
        <form action="./saveUsers" method="post">
            <input name="first_name" placeholder="Имя" required><br><br>
            <input name="last_name" placeholder="Фамилия" required><br><br>
            <input name="age" type="number" placeholder="Возраст" min="0" max="120" required><br><br>
            <input name="fines_num" type="number" placeholder="Штрафы" min="0" value="0"><br><br>
            <input name="exp_years" type="number" placeholder="Стаж" min="0" value="0"><br><br>
            <input name="cars_num" type="number" placeholder="Машин" min="0" value="0"><br><br>

            <label>
                <input type="checkbox" name="last user"> Сохранить всех и завершить
            </label><br><br>

            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>
        <h3>Добавляемые пользователи:</h3>
        <table>
            <tr>
                <th>ID</th>
                <th>FIRST NAME</th>
                <th>LAST NAME</th>
                <th>AGE</th>
                <th>FINES NUM</th>
                <th>EXP YEARS</th>
                <th>CARS NUM</th>
            </tr>
            <c:forEach items="${usersForJspPage}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.finesNum}</td>
                    <td>${user.expYears}</td>
                    <td>${user.carsNum}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
