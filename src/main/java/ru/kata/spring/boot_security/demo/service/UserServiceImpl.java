package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userDao.getById(id);
        user.setDeleted(true);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getPassword().isEmpty()) {
            user.setPassword(userDao.getById(user.getId()).getPassword());
        }
        if (user.getRoles() == null) {
            user.setRoles(userDao.getById(user.getId()).getRoles());
        }
        userDao.save(user);
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        Optional<User> optional = userDao.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;

        //return userDao.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAllByDeletedIsFalse();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmailAndDeletedIsFalse(email);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmailAndDeletedIsFalse(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
