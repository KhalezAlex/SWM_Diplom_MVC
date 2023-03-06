package org.stepacademy.swm_diplom_mvc.controllers.middleware;

import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.utilities.DBServiceAggregator;

import java.io.IOException;

@Component
@Order(100)
public class HomePageAttrsFilter implements Filter {
    @Autowired
    DBServiceAggregator aggregator;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        setHomePageModelAttrs(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setHomePageModelAttrs(ServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            setHomepageUnAuthModelAttrs(request);
        else {
//Атрибуты, которые нужны, не зависимо от того, аутентифицирован пользователь, или нет
            request.setAttribute("isAuthenticated", !auth.getPrincipal().equals("anonymousUser"));
//список городов для выгрузки в хедер для отображения случайных спортивных событий на сегодня при смене города (js)
            request.setAttribute("cities", aggregator.cityService.findAll());
//Атрибуты, зависящие от того, аутентифицирован ли пользователь
            if (!auth.getPrincipal().equals("anonymousUser"))
                setHomepageAuthModelAttrs(request, auth);
            else
                setHomepageUnAuthModelAttrs(request);
        }
    }

    private void setHomepageAuthModelAttrs(ServletRequest request, Authentication auth) {
//Передаем Логин пользователя
        request.setAttribute("userName", auth.getName());
//Проверяем город в профиле пользователя
        City city = aggregator.customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
//Если город не указан, показываем Москву, не устанавливая атрибут селекта
        request.setAttribute("city", city == null ? aggregator.cityService.findById(1).get() : city);
//Проверка пользователя на наличие роли ADMIN
        request.setAttribute("isAdmin", auth.getAuthorities().toString()
                .contains(aggregator.roleService.findById(1).get().getRole()));
        System.out.println(request);
    }
    private void setHomepageUnAuthModelAttrs(ServletRequest request) {
        request.setAttribute("isAdmin",false);
    }
}
