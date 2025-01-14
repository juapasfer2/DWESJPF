package com.example.t3practica4.controller;

import com.example.t3practica4.entities.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CounterController {
    @GetMapping("/")
    public String handleRequest(HttpSession session, Model model) {
        Cookie cookie = (Cookie)
                session.getAttribute("counter");
        if (cookie == null) {
            cookie = new Cookie();
        }
        cookie.increment();
        session.setAttribute("counter", cookie);
        model.addAttribute("count", cookie.getCount());
        return "counter";
    }
}
