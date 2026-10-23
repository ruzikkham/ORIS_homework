package ru.itis.repository;

import ru.itis.models.User;

import java.util.List;

public interface IUserRepository extends ICrudRepository<User>{
    List<User> findAllByAge(String operator, int value);
    List<User> findAllByFinesNum(String operator, int value);
    List<User> findAllByExpYears(String operator, int value);
    List<User> findAllByCarsNum(String operator, int value);
    int save(List<User> users);
}
