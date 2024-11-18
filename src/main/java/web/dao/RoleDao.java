package web.dao;

import web.model.Role;
import java.util.Set;

public interface RoleDao {
    Set<Role> getAllRoles();
    Role getRoleById(Long id);
}