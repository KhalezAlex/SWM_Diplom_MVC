package org.stepacademy.swm_diplom_mvc.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("pages/home");
        registry.addViewController("/login").setViewName("pages/login");
        registry.addViewController("/register").setViewName("pages/registration");
        registry.addViewController("/profile").setViewName("pages/profile");
    }
}
