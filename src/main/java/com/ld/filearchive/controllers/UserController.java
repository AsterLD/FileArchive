package com.ld.filearchive.controllers;

import com.ld.filearchive.models.User;
import com.ld.filearchive.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsersListPage(Model model) {
        return userService.showUserList(model);
    }

    @GetMapping("/{username}")
    public String showUserInfoPage(@PathVariable String username, Model model) {
        return userService.findUserInfo(model, username, "user/userInfoPage");
    }

    @GetMapping("/search")
    public String searchUserPage(String search, Model model) {
        return userService.findUserList(model, search);
    }

    @GetMapping("/{username}/edit")
    public String editUserPage(@PathVariable String username, Model model) {
        return userService.findUserInfo(model, username, "user/editUserPage");
    }

    @PatchMapping
    public String updateUser(@RequestParam Map<String, String> formContent, User user, Model model) {
        return userService.updateUser(model, formContent, user);
    }

    @DeleteMapping("/{username}")
    public String deleteUser (@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
