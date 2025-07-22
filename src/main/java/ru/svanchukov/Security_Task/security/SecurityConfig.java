package ru.svanchukov.Security_Task.security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.svanchukov.Security_Task.jwt.JwtAuthenticationFilter;
import ru.svanchukov.Security_Task.jwt.JwtService;
import ru.svanchukov.Security_Task.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()  // Используем authorizeHttpRequests вместо authorizeRequests
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()  // Открытые для всех маршруты
                .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Только для пользователей с ролью ADMIN
                .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                .and()
                .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
