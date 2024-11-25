package ru.kata.spring.boot_security.demo.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainRestController(RoleService roleService, UserService userService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> Users = userService.getAllUsers();
        System.out.println(Users.isEmpty());
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable("id") long id,
                                     @Valid @RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity pageDelete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        System.out.println(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserPage(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        System.out.println(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
