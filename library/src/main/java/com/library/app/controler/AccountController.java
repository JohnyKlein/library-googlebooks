package com.library.app.controler;

import com.library.app.utils.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AccountController {

    @GetMapping(value="/login")
    public String login() {
        if (Auth.isValidToken()) {
            return "books";
        }
        return "login";
    }

}
