package ru.itis.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.models.User;
import ru.itis.repository.SettingsRepositoryJdbcImpl;
import ru.itis.repository.UsersRepositoryJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/saveBatch")
public class SaveBatchUsersServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int count = new SettingsRepositoryJdbcImpl(connection).getUserCount();
        List<User> users = new ArrayList<>();

        try {
            for (int i = 0; i < count; i++) {
                String firstName = request.getParameter("first_name_" + i);
                String lastName = request.getParameter("last_name_" + i);

                if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
                    response.sendError(400, "Заполните имя и фамилию");
                    return;
                }

                User user = User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .age(Integer.parseInt(request.getParameter("age_" + i)))
                        .finesNum(Integer.parseInt(request.getParameter("fines_num_" + i)))
                        .expYears(Integer.parseInt(request.getParameter("exp_years_" + i)))
                        .carsNum(Integer.parseInt(request.getParameter("cars_num_" + i)))
                        .build();
                if (user.getAge() < 0 || user.getFinesNum() < 0 || user.getExpYears() < 0 || user.getCarsNum() < 0) {
                    response.sendError(400, "Числа не должны быть отрицательными");
                    return;
                }
                users.add(user);
            }
        } catch (NumberFormatException e) {
            response.sendError(400, "Все числовые поля должны содержать целые числа");
            return;
        }

        new UsersRepositoryJdbcImpl(connection).save(users);
        response.sendRedirect(request.getContextPath() + "/success.html");
    }
}
