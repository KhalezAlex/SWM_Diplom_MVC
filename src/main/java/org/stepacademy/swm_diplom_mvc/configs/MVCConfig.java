package org.stepacademy.swm_diplom_mvc.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("pages/UI/home");
        registry.addViewController("/login").setViewName("pages/UI/login");
        registry.addViewController("/register").setViewName("pages/UI/registration");
        registry.addViewController("/profile").setViewName("pages/UI/profile");
        registry.addViewController("/new_event").setViewName("pages/UI/new_event");
        registry.addViewController("/search").setViewName("pages/UI/search");
        registry.addViewController("/admin").setViewName("pages/admin/admin");
        registry.addViewController("/admin-customer").setViewName("pages/admin/admin-customer");
        registry.addViewController("/admin-event").setViewName("pages/admin/admin-event");
    }
}
