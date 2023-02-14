package org.stepacademy.swm_diplom_mvc.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private static final String[] endpointsForAll = {"/" ,"/login", "/home_page", "/register",
            "/onLoad", "/error_page", "/checkLoginForRegistration"};
    private static final String[] endpointsForAdmin = {"/admin_page"};
    private static final String[] endpointsForAuthenticated = {"/profile", "/loadCitiesCountries", "/updateProfile"};


}
