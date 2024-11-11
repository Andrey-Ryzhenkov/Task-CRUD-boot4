package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    User getUser(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    List<User> getAllUsers();

}
