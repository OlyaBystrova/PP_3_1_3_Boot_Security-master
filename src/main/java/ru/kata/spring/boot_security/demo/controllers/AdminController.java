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
//    private final RoleServiceImpl userServiceImpl;


// Все пользователи из списка
    @GetMapping()
    public String pageForAdmin(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

//    @PostMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("roles", roleService.findAllRoles());
//        return "newuser";
//    }
//
//    @PostMapping
//    public String create(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/admin";
//    }


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
//    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
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

//    @PatchMapping("{id}")
//    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") int id,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "edituser";
//        } else {
////            user.setRoles(roleService.findRoleById(roles));
//            userService.save(user);
//            return "redirect:/admin";
//        }
//    }

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

//    @PutMapping("/edit")
//    public String pageEdit(@RequestParam("role")ArrayList<Integer> roles,
//                           @Valid User user,
//                           @PathVariable("id") int id,
//                           BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "edituser";
//        } else {
//            user.setRoles(roleService.findRoleById(roles));
//            userService.save(user);
//            return "redirect:/admin";
//        }
//    }

//    @PatchMapping(params = "/edit")
////    @RequestMapping(value = "/admin/edit", method = {RequestMethod.PATCH})
////      @RequestMapping(value = "/edit",
////        produces = "application/json",
////        method=RequestMethod.PATCH)
//    public String pageEdit(@RequestParam("role")ArrayList<Integer> roles,
//                           @Valid User user,
//                           BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "edituser";
//        } else {
//            user.setRoles(roleService.findRoleById(roles));
//            userService.save(user);
//            return "redirect:/admin";
//        }
//    }


///////////////


//    @PostMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("users", new User());
//        model.addAttribute("roles", roleService.findAllRoles());
//        return "new";
//    }


//    @PostMapping()
//    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
//        if (bindingResult.hasErrors())
//            return "new";
//        userService.save(user);
//        return "redirect:/admin";
//    }

//////////////


// Получаем пользователя по id и передаем в представление
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.getById(id));
//        return "show";
//    }

//    @GetMapping("/new")
//    public String newUser(@ModelAttribute("user") User user){
//        return "new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
//        if (bindingResult.hasErrors())
//            return "new";
//        userService.save(user);
//        return "redirect:/admin";
//    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id){
//        model.addAttribute("user", userService.getById(id));
//        return "edit";
//    }

//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//        if (bindingResult.hasErrors())
//            return "edit";
//
//        userService.update((long) id, user);
//        return "redirect:/users/list";
//    }

//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id){
//        userService.deleteById(id);
//        return "redirect:/users/list";
//    }

//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        userService.deleteById(id);
//        return "redirect:/users/list";
//    }

//    @GetMapping("/showUserInfo")
//    public String showUserInfo() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
//        System.out.println(userDetails.getUsername());
//
//        return "user";
//    }

}
