package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;


@RequestMapping("/admin")
@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String pageForAdmin(Model model, Principal principal) {
        StringBuilder roles = new StringBuilder();
        for (Role role : userService.findUserByEmail(principal.getName()).getRoles()) {
            roles.append(role.toString());
            roles.append(" ");
        }
        model.addAttribute("thisUserRole", roles);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("this_user", userService.findUserByEmail(principal.getName()));
        model.addAttribute("new_user", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute @Valid User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("editUser") @Valid User user) {
        userService.update(user.getId(), user);
        return "redirect:/admin";
    }
}
