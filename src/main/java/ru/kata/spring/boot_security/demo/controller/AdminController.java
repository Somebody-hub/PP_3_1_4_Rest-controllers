package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/users")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("")
    public String printUsers(ModelMap model, @AuthenticationPrincipal UserDetails actualUser) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("actualUser", actualUser);
        return "admin/allUsers";
    }

    @PutMapping("")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute("user") User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return "redirect:/users";
        } else {
            userService.addUser(user);
            return "redirect:/users";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/userInfo")
    public String getUserPage(Principal principal, ModelMap model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute(user);
        return "/user/userInfo";
    }

}
