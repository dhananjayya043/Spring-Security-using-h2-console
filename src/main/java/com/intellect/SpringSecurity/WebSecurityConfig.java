package com.intellect.SpringSecurity;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired()
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
            .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
            .anyRequest().authenticated() // Require authentication for all other requests
        ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic() // Enable basic authentication
        .and()
        .csrf().disable() // Disable CSRF for the H2 console
        .headers().frameOptions().sameOrigin(); // Allow frames from the same origin

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        if (!userDetailsManager.userExists("user1")) {
            userDetailsManager.createUser(
                User.withUsername("user1")
                .password(passwordEncoder().encode("password1"))
                .roles("USER")
                .build());
        } else {
            System.out.println("user1 already exists");
        }

        if (!userDetailsManager.userExists("admin")) {
            userDetailsManager.createUser(
                User.withUsername("admin")
                .password(passwordEncoder().encode("password2"))
                .roles("ADMIN")
                .build());
        }

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
