package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    List<User> findAllByDeletedIsFalse();

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email AND u.deleted = false")
    User findByEmailAndDeletedIsFalse(String email);
}
