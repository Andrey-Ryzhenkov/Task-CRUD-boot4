package web.rest;

import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public String addUser(@RequestBody User user) {
        if(userService.getUserByUsername(user.getUsername()) != null) {
            userService.addUser(user, user.getRoles());
            return "User added successfully";
        }else {
            return "User not added, because username already exists";
        }

    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.getUser(id);
        if (existingUser == null) {
            return "User not found";
        }
        user.setId(id);
        userService.updateUser(user, user.getRoles());
        return "User updated successfully";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}