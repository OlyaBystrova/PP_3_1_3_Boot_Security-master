package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void save(User user) { userRepository.save(passwordCoder(user)); }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        User user = userRepository.getById(id);
        if (updateUser.getPassword().equals(user.getPassword())) {
            userRepository.save(updateUser);
        } else {
            String pass = passwordEncoder.encode(updateUser.getPassword());
            updateUser.setPassword(pass);
            userRepository.save(updateUser);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User passwordCoder(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
