package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {

    User findUserByEmail(String email);

    User getById(int id);

    void save(User user);

    void deleteById(int id);

    List<User> findAll();

    User passwordCoder(User user);
}
