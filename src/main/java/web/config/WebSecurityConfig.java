package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import web.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity// Включает поддержку Spring Security.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;// Объявляется обработчик успешной аутентификации.
    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, CustomUserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    // Шифрование паролей
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключение CSRF
                .authorizeRequests()
                    // Разрешаем доступ к статическим ресурсам
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/", "/login").permitAll() // Доступ ко всем
                    .antMatchers("/admin/**").hasRole("ADMIN") // Админ-доступ
                    .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Доступ для USER/ADMIN
                    .anyRequest().authenticated() // Остальные запросы требуют аутентификации
                .and()
                .formLogin()
                    .successHandler(successUserHandler) // Обработчик успешного входа
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login") // После выхода перенаправление на логин
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // Используем CustomUserDetailsService
    }
}