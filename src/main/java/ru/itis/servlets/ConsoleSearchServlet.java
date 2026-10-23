package ru.itis.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.models.User;
import ru.itis.repository.UsersRepositoryJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@WebServlet("/searchConsole")
public class ConsoleSearchServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "20602070";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db_11_402";

    private Connection connection;

    @Override
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String by = request.getParameter("by");
        String operator = request.getParameter("operator");
        String value = request.getParameter("value");
        response.setContentType("text/plain;charset=UTF-8");

        if (by == null && operator == null && value == null) {
            response.getWriter().println("Пример запроса:");
            response.getWriter().println(request.getContextPath()
                    + "/searchConsole?by=fines_num&operator=%3E&value=3");
            return;
        }

        if (by == null || operator == null || value == null) {
            response.sendError(400, "Укажите by, operator и value");
            return;
        }

        if (!operator.equals("=") && !operator.equals(">") && !operator.equals("<")
                && !operator.equals(">=") && !operator.equals("<=")) {
            response.sendError(400, "Неизвестный оператор");
            return;
        }

        int number;
        try {
            number = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            response.sendError(400, "Значение должно быть целым числом");
            return;
        }

        UsersRepositoryJdbcImpl repository = new UsersRepositoryJdbcImpl(connection);
        List<User> users;

        switch (by) {
            case "fines_num":
                users = repository.findAllByFinesNum(operator, number);
                break;
            case "exp_years":
                users = repository.findAllByExpYears(operator, number);
                break;
            case "cars_num":
                users = repository.findAllByCarsNum(operator, number);
                break;
            case "age":
                users = repository.findAllByAge(operator, number);
                break;
            default:
                response.sendError(400, "Неизвестное поле");
                return;
        }

        for (User user : users) {
            System.out.println(user);
        }
        response.getWriter().println("Результат выведен в консоль. Найдено: " + users.size());
    }
}
