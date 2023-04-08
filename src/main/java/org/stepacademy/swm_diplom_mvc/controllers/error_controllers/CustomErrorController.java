package org.stepacademy.swm_diplom_mvc.controllers.error_controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    DBServiceRole roleService;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Authentication auth) {
        setHandleErrorAttrs(request,auth);
        return "/pages/UI/error";
    }

    private void setHandleErrorAttrs(HttpServletRequest request, Authentication auth) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null)
            request.setAttribute("error", Integer.parseInt(status.toString()));
        request.setAttribute("isAdmin", auth.getAuthorities().toString()
                .contains(roleService.findById(1).get().getRole()));
        request.setAttribute("userName", auth.getName());
        request.setAttribute("isAuthenticated", !auth.getPrincipal().equals("anonymousUser"));
    }
}
