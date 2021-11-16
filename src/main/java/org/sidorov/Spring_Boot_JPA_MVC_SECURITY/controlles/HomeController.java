package org.sidorov.Spring_Boot_JPA_MVC_SECURITY.controlles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/")
public class HomeController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLogin(){
        return "login";
    }
}
