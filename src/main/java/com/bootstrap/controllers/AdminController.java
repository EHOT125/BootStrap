package com.bootstrap.controllers;

import com.bootstrap.model.User;
import com.bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping(value = "list")
    public String adminPage(Model model, Principal principal) {
        User user = new User();
        List<User> list = userService.getUsers();
        model.addAttribute("addNewUser", user);
        model.addAttribute("listUser", list);
        model.addAttribute("loggedUser", userService.loadUserByUsername(principal.getName()));
        return "admin/all_users";
    }

    @GetMapping(value = "/createUser")
    public String createOrUpdateUser(@ModelAttribute("user") User user) {
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(bCrypt(user.getPassword()));
        } else {
            user.setPassword(user.getPassword());
        }
        userService.createOrUpdateUser(user);
        return "redirect:/admin/list";
    }

    @GetMapping("anigilation_user_in_hell/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/list";
    }

    private String bCrypt(String hash) {
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        return crypt.encode(hash);
    }
}

