package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class INIT {

    private UserService userService;
    private RoleService roleService;


    @Autowired
    public INIT(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");
        roleService.addRole(admin);
        roleService.addRole(user);
        userService.addUser(new User("admin", "admin", 20, "admin", "admin", Set.of(admin, user)));
        userService.addUser(new User("user", "user", 20, "user", "user", Set.of(user)));


    }
}
