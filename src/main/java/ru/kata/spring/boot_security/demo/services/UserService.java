package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    User getById(int id);

    void save(User user);

    void deleteById(int id);

    List<User> findAll();

    User passwordCoder(User user);
}
