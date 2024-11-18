package web.controller;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userPage(Authentication authentication, Model model) {
        // Получаем текущего авторизованного пользователя по имени (username)
        User user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("user", user); // Передаем пользователя в модель
        return "user/user"; // Страница с информацией о пользователе
    }

}
