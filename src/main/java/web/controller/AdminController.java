package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String redirect(Model model) {
        return "redirect:/admin/users";
    }
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());// Получаем всех пользователей
        return "admin/allUsers";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());// Новый пользователь для формы
        model.addAttribute("roles", userService.getAllRoles()); // Все роли для выбора
        return "admin/addUser";// Страница добавления пользователя
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("roles") Set<Long> roleIds) { // Получаем роли как идентификаторы
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = userService.getRoleById(roleId); // Получаем роли по ID
            roles.add(role);
        }
        userService.addUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/editUser";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") Set<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = userService.getRoleById(roleId);
            roles.add(role);
        }
        userService.updateUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}