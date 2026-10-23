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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/selectUsers")
public class SelectUsersServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db_11_402";
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String findAttr = req.getParameter("attribute");
        String operator = req.getParameter("operator");
        String value = req.getParameter("value");
        UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(connection);
        List<User> users = new ArrayList<>();
        if (findAttr != null && operator != null && value != null) {
            int number;
            try {
                number = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Значение должно быть целым числом");
                return;
            }
            switch (findAttr) {
                case "age":
                    users = usersRepositoryJdbc.findAllByAge(operator, number);
                    break;
                case "fines_num":
                    users = usersRepositoryJdbc.findAllByFinesNum(operator, number);
                    break;
                case "exp_years":
                    users = usersRepositoryJdbc.findAllByExpYears(operator, number);
                    break;
                case "cars_num":
                    users = usersRepositoryJdbc.findAllByCarsNum(operator, number);
                    break;
            }
        }
        else {
            users = usersRepositoryJdbc.findAll();
        }
        req.setAttribute("usersForJspPage", users);
        req.getRequestDispatcher("/jsp/selectUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
