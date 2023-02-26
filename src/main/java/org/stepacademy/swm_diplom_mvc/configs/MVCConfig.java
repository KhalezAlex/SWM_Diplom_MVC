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
        registry.addViewController("/new_event").setViewName("pages/new_event");
        registry.addViewController("/admin").setViewName("pages/admin");
        registry.addViewController("/admin-customer").setViewName("pages/admin_page/admin-customer");
        registry.addViewController("/admin-event").setViewName("pages/admin_page/admin-event");
        registry.addViewController("/admin-profile").setViewName("pages/admin_page/admin-profile");
    }
}
