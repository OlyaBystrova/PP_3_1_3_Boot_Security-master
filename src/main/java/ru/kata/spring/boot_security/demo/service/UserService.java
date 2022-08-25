package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {

    Optional<User> findUserByUsername(String username);

    User getById(int id);

    void save(User user);

//    void update(Long id, User updatedUser);

    void deleteById(int id);

    List<User> findAll();
}
