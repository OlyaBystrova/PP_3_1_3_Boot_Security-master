package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import javax.validation.Valid;
import java.util.ArrayList;

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
    public String pageForAdmin(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model){
        model.addAttribute("listRoles",roleService.findAllRoles());
        return "newuser";
    }

    @PostMapping("/new")
    public String create(@RequestParam("role") ArrayList<Integer> roles,
                             @ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newuser";
        }
        if (userService.findUserByUsername(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("username", "username",
                    String.format("User with name \"%s\" is already exist!", user.getUsername())));
            return "newuser";
        }
        user.setRoles(roleService.findRoleById(roles));
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("listRoles",roleService.findAllRoles());
        return "edituser";
    }

    @PatchMapping("{id}")
    public String update(@RequestParam("role") ArrayList<Integer> roles,
                         @PathVariable("id") int id,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edituser";
        }
        user.setRoles(roleService.findRoleById(roles));
        userService.save(user);
        return "redirect:/admin";
    }
}
