package ru.kata.spring.boot_security.demo.services;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {

    User findUserByEmail(String email);

    void save(User user);

    void deleteById(int id);

    @Transactional
    void update(int id, User updateUser);

    List<User> findAll();

    User passwordCoder(User user);
}
