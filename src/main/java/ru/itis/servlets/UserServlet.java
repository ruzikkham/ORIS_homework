package ru.itis.servlets;

import ru.itis.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private List<User> users;

    @Override
    public void init() {
        users = new ArrayList<>();
        User userOne = User.builder()
                .id(1L)
                .firstName("A")
                .lastName("A")
                .age(40)
                .build();

        User userTwo = User.builder()
                .id(2L)
                .firstName("B")
                .lastName("B")
                .age(20)
                .build();

        User userThree = User.builder()
                .id(3L)
                .firstName("C")
                .lastName("C")
                .age(30)
                .build();

        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("usersForJsp", users);
        req.getRequestDispatcher("/jsp/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
