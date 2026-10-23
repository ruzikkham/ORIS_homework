package ru.itis.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.models.User;
import ru.itis.repository.UsersRepositoryJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/saveUsers")
public class SaveUsersServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "20602070";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db_11_402";
    private Connection connection;
    private List<User> users;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            users = new ArrayList<>();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("usersForJspPage", users);
        req.getRequestDispatcher("/jsp/saveUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Integer age = getInt(req.getParameter("age"),0);
        Integer finesNum = getInt(req.getParameter("fines_num"),0);
        Integer expYears = getInt(req.getParameter("exp_years"),0);
        Integer carsNum = getInt(req.getParameter("cars_num"),0);

        if (!(firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                    age < 0 || finesNum < 0 || expYears < 0 || carsNum < 0)) {
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .age(age)
                    .finesNum(finesNum)
                    .expYears(expYears)
                    .carsNum(carsNum)
                    .build();
            users.add(user);
        }


        boolean isLastUser = req.getParameter("last user") != null;
        if (isLastUser) {
            UsersRepositoryJdbcImpl usersRepository = new UsersRepositoryJdbcImpl(connection);
            int savedRowsNum = usersRepository.save(users);

            System.out.println("savedRowsNum: " + savedRowsNum);
            if  (savedRowsNum > 0) {
                users.clear();
            }

            resp.sendRedirect("./selectUsers");
            return;
        }
        req.setAttribute("usersForJspPage", users);
        req.getRequestDispatcher("/jsp/saveUsers.jsp").forward(req, resp);
    }

    private Integer getInt(String val, int def) {
        if (val == null || val.trim().isEmpty()) {return def;}
        try {
            Integer num = Integer.valueOf(val);
            if (0<=num) {return num;}
            else {return def;}
        }
        catch (NumberFormatException e) {return def;}
    }
}
