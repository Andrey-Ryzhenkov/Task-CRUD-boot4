package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void addUser(User user, Set<Role> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифруем пароль
        user.setRoles(roles); // Устанавливаем роли
        userDao.addUser(user); // Сохраняем пользователя
    }

    @Override
    public void updateUser(User user, Set<Role> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифруем пароль
        user.setRoles(roles); // Обновляем роли
        userDao.updateUser(user); // Обновляем пользователя
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        this.userDao.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles(); // Получаем все роли
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username); // Получаем пользователя по логину
    }

    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id); // Получаем роль по ID
    }
}
