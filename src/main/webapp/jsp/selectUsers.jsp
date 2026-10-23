<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Select Users by Attr</title>
</head>
<body>
    <h1>Страница фильтрации пользователей</h1>
    <form action="./selectUsers" method="get">
        <select name="attribute">
            <option value="age">Возраст</option>
            <option value="fines_num">Кол-во штрафов</option>
            <option value="exp_years">Опыт в годах</option>
            <option value="cars_num">Кол-во автомобилей</option>
        </select>

        <select name="operator">
            <option value="=">=</option>
            <option value="<"><</option>
            <option value=">">></option>
        </select>

        <input name="value" type="number" placeholder="Значение">

        <input type="submit" value="Find">
    </form>

    <div>
        <p>К сохранению:</p>
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
