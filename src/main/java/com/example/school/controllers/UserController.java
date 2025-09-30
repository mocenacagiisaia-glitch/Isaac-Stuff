package com.example.school.controllers;

import com.example.school.models.UserEntity;
import com.example.school.services.UserService; // Ensure you have a UserService to handle business logic
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService; // Inject your UserService

    @GetMapping("/admin/delete-account")
    public String listUsers(Model model) {
        List<UserEntity> users = userService.getAllUsers(); // Fetch users from the service
        model.addAttribute("users", users);
        return "user_list"; // Return the name of your Thymeleaf template
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin/delete-account"; // Redirect to the user list after deletion
    }

}
