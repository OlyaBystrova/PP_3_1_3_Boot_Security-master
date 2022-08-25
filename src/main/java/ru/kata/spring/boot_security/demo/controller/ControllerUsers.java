package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class ControllerUsers {

    private final UserService userService;

    @Autowired
    public ControllerUsers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user){
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "new";
        userService.save(user);
        return "redirect:/users/list";
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id){
//        model.addAttribute("user", userService.getById((long) id));
//        return "edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//        if (bindingResult.hasErrors())
//            return "edit";
//
//        userService.update((long) id, user);
//        return "redirect:/users/list";
//    }

     @DeleteMapping("/{id}")
     public String delete(@PathVariable("id") int id){
        userService.deleteById(id);
         return "redirect:/users/list";
     }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());

        return "show";
    }
}
