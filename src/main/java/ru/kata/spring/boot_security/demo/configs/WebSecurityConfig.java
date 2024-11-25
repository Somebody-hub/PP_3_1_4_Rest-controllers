package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, @Lazy UserServiceImpl userServiceImpl) {
        this.successUserHandler = successUserHandler;
        this.userServiceImpl = userServiceImpl;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(formLogin -> formLogin
                        .successHandler(successUserHandler)
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/index",  "/login", "/error").permitAll()
                        .requestMatchers("/").hasAnyRole("ADMIN", "USER").anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .logout(logout -> logout.permitAll());
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userServiceImpl);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

}