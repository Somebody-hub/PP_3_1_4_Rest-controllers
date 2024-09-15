package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;


@Controller
@RequestMapping("/Admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("")
    public String printUsers(ModelMap model) {
        model.addAttribute("usersList", userService.getAllUsers());
        return "Admin/allUsers";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "Admin/editUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id,
                             @RequestParam(name = "selectedRoles", required = false) Set<String> selectedRoles) {
        user.setRoles(roleService.getSelectedRoles(selectedRoles));
        userService.updateUser(id, user);
        return "redirect:/Admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "Admin/createUser";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(name = "selectedRoles", required = false) Set<String> selectedRoles) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return "redirect:/Admin";
        }
        user.setRoles(roleService.getSelectedRoles(selectedRoles));
        userService.addUser(user);
        return "redirect:/Admin";
    }

    @RequestMapping(value = "/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/Admin";
    }

    @GetMapping(value = "/userInfo")
    public String getUserPage(Principal principal, ModelMap model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute(user);
        return "/User/userInfo";
    }

}
