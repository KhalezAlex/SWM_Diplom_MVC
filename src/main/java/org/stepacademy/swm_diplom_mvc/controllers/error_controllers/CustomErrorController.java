package org.stepacademy.swm_diplom_mvc.controllers.error_controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(){
//        return "pages/UX/error";
        return "layouts/error/error";
    }
}
