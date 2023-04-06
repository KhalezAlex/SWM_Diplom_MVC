package org.stepacademy.swm_diplom_mvc.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.dbUserDetails.DBUserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/styles/templates/*.css",
//                "/styles/*.css", "/styles/header/*.css", "/styles/profile/*.css",
                "/images/*", "/images/activity_pics/*",
                "/scripts/*.js", "/scripts/profile/*.js", "/scripts/home/*.js",
                "https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.js");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin", "/admin_customer/*", "/admin_profile/*", "/admin_event/*",
                                "/admin_home/*").hasRole("ADMIN")
                        .requestMatchers("/new_event", "/event/save").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/profile", "/profile/update", "/logout",
                                "/profile/activity/*").authenticated()
                        .requestMatchers("/customer/register", "/register",
                                "/service/generateBase", "/service/tags", "/service/events").anonymous()
                        .requestMatchers("/", "/webjars/**", "/*", "/home/events/onLoadEvents").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                                .permitAll()
                        .defaultSuccessUrl("/")
                );
        return http.build();
    }


    @Bean
    public PasswordEncoder encoder() {
// стандартный кодировщик Spring
        return new BCryptPasswordEncoder();
    }


// КОНФИГУРАЦИЯ ДЛЯ ПОДКЛЮЧЕНИЯ БД
    @Bean
    public UserDetailsService userDetailsService() {
        return new DBUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(encoder())
                .and()
                .authenticationProvider(authenticationProvider())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }
}
