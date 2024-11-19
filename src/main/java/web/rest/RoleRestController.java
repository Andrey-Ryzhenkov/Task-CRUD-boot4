package web.rest;

import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {
    private final UserService userService;

    public RoleRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Set<Role> getAllRoles() {
        return userService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return userService.getRoleById(id);
    }
}