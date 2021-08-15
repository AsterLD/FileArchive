package com.ld.filearchive.services;

import com.ld.filearchive.models.Role;
import com.ld.filearchive.models.User;
import com.ld.filearchive.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.*;
import java.util.stream.Collectors;

/*
 * Класс UserService, хранит в себе логику, для работы UserController.
 * loadUserByUsername - Возращает пользователя, если такого пользователя не существует, выбрасывает исключение,
 * addUser - Если пользователя с таким username нет, ему присваивается роль USER и он сохраняется в БД,
 * showUserList - Отображает список всех пользователей,
 * findUserInfo - Отображает конкретного пользователя, поиск осуществляется по username,
 * findUserList - Отображает пользователей, соответствующих критериям поиска, (поиск осуществляется по имени (username),
 * updateUser - Обновляет данные пользователя, с помощью слияния данных с формы, с данными из БД,
 * deleteUser - Удаляет пользователя из БД.
 */

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public String addUser(Model model, User user) {
        User userFromDb = userRepo.findUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь уже существует!");
            return "registration/registrationPage";
        }
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/login";
    }

    public String showUserList(Model model) {
        model.addAttribute("userList", userRepo.findAll());
        return "user/userListPage";
    }

    public String findUserInfo(Model model, String username, String page) {
        model.addAttribute("user", userRepo.findUserByUsername(username));
        return page;
    }

    public String findUserList(Model model, String username) {
        model.addAttribute("userList", userRepo.findUsersByUsernameContains(username));
        return "user/userListPage";
    }

    public String updateUser(Model model, Map<String, String> formContent, User user) {
        User userFromDb = userRepo.findUserByUserId(user.getUserId());
        if (Objects.equals(userRepo.findUserByUsername(user.getUsername()), null)
                && !userFromDb.getUserId().equals(user.getUserId())) {
            model.addAttribute("message", "Пользователь с таким именем уже существует!");
            model.addAttribute("user", userFromDb);
            return "user/editUserPage";
        }
        userFromDb.setEnabled(formContent.containsKey("isEnabled"));
        userFromDb.userMerge(user);
        userFromDb.getRoles().clear();
        Set<String> userRoles = formContent.keySet();
        userRoles.retainAll(Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet()));
        userRoles.forEach(s -> userFromDb.getRoles().add(Role.valueOf(s)));
        userRepo.save(userFromDb);
        return "redirect:/users";
    }

    public String deleteUser (String username) {
        User user = userRepo.findUserByUsername(username);
        if (user != null) {
            userRepo.delete(user);
        }
        return "redirect:/users";
    }
}
