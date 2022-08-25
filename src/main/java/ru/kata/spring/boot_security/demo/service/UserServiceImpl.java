package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetails;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <User> user =  userDao.findUserByUsername(username); // достаем из базы юзера по пришедшему имени
        if (user.isEmpty() ){
            throw new UsernameNotFoundException(String.format("User  not found", username));
        }
        return new UserDetails(user.get());
    }
 //   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role>, role){

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user =  findUserByUsername(username); // достаем из базы юзера по пришедшему имени
//        if (user==null){
//            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
//        }
//        return org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword());
//    }
//    private Collection<? extends GrantedAuthority > mapRolesToAuthorities(Collection<Role>, role){
//
//    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

//    @Override
//    public void update(Long id, User updatedUser) {
//        userDao.update(id, updatedUser);
//    }

    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
