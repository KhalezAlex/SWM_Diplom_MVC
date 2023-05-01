package org.stepacademy.swm_diplom_mvc.controllers.middleware;

import jakarta.servlet.*;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.DBServiceCity;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;

@Component
@Order(100)
public class HeaderAttributesFilter implements Filter {
    @Autowired
    DBServiceCity cityService;
    @Autowired
    DBServiceCustomer customerService;
    @Autowired
    DBServiceRole roleService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        setHomePageModelAttrs(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setHomePageModelAttrs(ServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            setHomepageUnAuthModelAttrs(request);
        } else {
        //Атрибуты, которые нужны, не зависимо от того, аутентифицирован пользователь, или нет
            request.setAttribute("isAuthenticated", !auth.getPrincipal().equals("anonymousUser"));
        //список городов для выгрузки в хедер для отображения случайных спортивных событий на сегодня при смене города (js)
            request.setAttribute("cities", cityService.findAll());
        //Атрибуты, зависящие от того, аутентифицирован ли пользователь
            if (!auth.getPrincipal().equals("anonymousUser")) {
                setHomepageAuthModelAttrs(request, auth);
            } else {
                setHomepageUnAuthModelAttrs(request);
            }
        }
    }

    private void setHomepageAuthModelAttrs(ServletRequest request, Authentication auth) {
    //Передаем Логин пользователя
        request.setAttribute("userName", auth.getName());
    //Проверяем город в профиле пользователя
        City city = customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
    //Если город не указан, показываем Москву, не устанавливая атрибут селекта
        request.setAttribute("city", city == null ? cityService.findById(1).get().getName() : city.getName());
    //Проверка пользователя на наличие роли ADMIN
        request.setAttribute("isAdmin", auth.getAuthorities().toString()
                                .contains(roleService.findById(1).get().getRole()));
    }

    private void setHomepageUnAuthModelAttrs(ServletRequest request) {
        request.setAttribute("isAdmin", false);
        request.setAttribute("city", "Москва");
    }
}
