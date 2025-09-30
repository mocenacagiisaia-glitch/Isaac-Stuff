package com.example.school.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.school.controllers.CustomLoginSuccessHandler;
import com.example.school.repositories.ParentRepository; // Import the ParentRepository
import com.example.school.repositories.StudentRepository;
import com.example.school.services.TeacherService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomLoginSuccessHandler customLoginSuccessHandler) throws Exception {
        http
            .csrf().disable() 
            .authorizeRequests()
                .requestMatchers("/auth/login", "/auth/register", "/css/**").permitAll() 
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/teacher/**").hasAuthority("TEACHER")
                .requestMatchers("/parent/**").hasAuthority("PARENT")
                .requestMatchers("/student/**").hasAuthority("STUDENT")

                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler) // Use the bean here
                .failureUrl("/auth/login?error=true")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout=true")
                .permitAll();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler(TeacherService teacherService, ParentRepository parentRepository,StudentRepository studentRepository) {
        return new CustomLoginSuccessHandler(teacherService, parentRepository, studentRepository); // Pass both services to the handler
    }
}
