package ru.itis.repository;

import ru.itis.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements IUserRepository {

    private static final String SELECT_ALL = "select * from driver";
    private static final String INSERT_USER = "insert into driver "
            + "(first_name, last_name, age, fines_num, exp_years, cars_num) values (?, ?, ?, ?, ?, ?)";

    private final Connection connection;

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            addUsers(users, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public List<User> findAllByAge(String operator, int value) {
        return findByField("age", operator, value);
    }

    @Override
    public List<User> findAllByFinesNum(String operator, int value) {
        return findByField("fines_num", operator, value);
    }

    @Override
    public List<User> findAllByExpYears(String operator, int value) {
        return findByField("exp_years", operator, value);
    }

    @Override
    public List<User> findAllByCarsNum(String operator, int value) {
        return findByField("cars_num", operator, value);
    }

    private List<User> findByField(String field, String operator, int value) {
        if (!operator.equals("=") && !operator.equals(">") && !operator.equals("<")
                && !operator.equals(">=") && !operator.equals("<=")) {
            throw new IllegalArgumentException("Неизвестный оператор");
        }
        String sql = SELECT_ALL + " where " + field + " " + operator + " ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                addUsers(users, resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private void addUsers(List<User> users, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            User user = User.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .age(resultSet.getInt("age"))
                    .finesNum(resultSet.getInt("fines_num"))
                    .expYears(resultSet.getInt("exp_years"))
                    .carsNum(resultSet.getInt("cars_num"))
                    .build();
            users.add(user);
        }
    }

    @Override
    public int save(List<User> users) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            for (User user : users) {
                setUser(statement, user);
                statement.addBatch();
            }
            return statement.executeBatch().length;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            setUser(statement, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUser(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setInt(3, user.getAge());
        statement.setInt(4, user.getFinesNum());
        statement.setInt(5, user.getExpYears());
        statement.setInt(6, user.getCarsNum());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(User entity) {
    }

    @Override
    public void remove(User entity) {
    }

    @Override
    public void removeById(Long id) {
    }
}
