package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void addUser(User user, Set<Role> roles);
    void updateUser(User user, Set<Role> roles);
    void deleteUser(Long id);
    User getUser(Long id);
    List<User> getAllUsers();
    Set<Role> getAllRoles();
    User getUserByUsername(String username);
    Role getRoleById(Long roleId);
}
