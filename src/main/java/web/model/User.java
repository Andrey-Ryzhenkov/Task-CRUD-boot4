package web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // Логин пользователя

    @Column(nullable = false)
    private String firstName; // Имя пользователя

    @Column(nullable = false)
    private String lastName; // Фамилия пользователя

    @Column(unique = true, nullable = false)
    private String email; // Почта пользователя

    @Column(nullable = false)
    private String password; // Пароль пользователя

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles; // Роли пользователя

    // Реализация методов UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    //Возвращает список ролей и прав пользователя (например, ROLE_USER, ROLE_ADMIN).

    @Override
    public String getPassword() {
        return password;
    }
    //Возвращает пароль пользователя (хэшированный).

    @Override
    public String getUsername() {
        return username;
    }
    //Возвращает имя пользователя (идентификатор), которое используется для входа.

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //Указывает, истекла ли учетная запись пользователя. True-Активна

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //Указывает, заблокирована ли учетная запись. True-может войти

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //Указывает, истекли ли учетные данные пользователя (например, срок действия пароля).

    @Override
    public boolean isEnabled() {
        return true;
    }
    //Указывает, активен ли пользователь. Если false, пользователь не может быть аутентифицирован
}
