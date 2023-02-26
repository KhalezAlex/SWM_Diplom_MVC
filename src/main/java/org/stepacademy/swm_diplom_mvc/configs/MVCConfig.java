package org.stepacademy.swm_diplom_mvc.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("pages/UX/home");
        registry.addViewController("/login").setViewName("pages/UX/login");
        registry.addViewController("/register").setViewName("pages/UX/registration");
        registry.addViewController("/profile").setViewName("pages/UX/profile");
        registry.addViewController("/new_event").setViewName("pages/UX/new_event");
        registry.addViewController("/admin").setViewName("pages/admin/admin");
        registry.addViewController("/admin-customer").setViewName("pages/admin/admin-customer");
        registry.addViewController("/admin-event").setViewName("pages/admin/admin-event");
        registry.addViewController("/admin-profile").setViewName("pages/admin/admin-profile");
    }
}
