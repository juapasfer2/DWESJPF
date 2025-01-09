package com.example.t3practica4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.Cookie;
import org.springframework.ui.Model;

@Controller
public class NombreController {

    @GetMapping("/nombre")
    public String mostrarFormulario() {
        return "nombre";
    }

    @PostMapping("/guardarNombre")
    public String guardarNombre(@RequestParam("nombre") String nombre, HttpServletResponse response) {
        // Creamos una cookie con nombre
        Cookie cookie = new Cookie("nombre", nombre);
        //cookie.setMaxAge(60 * 60 * 24); // La cookie dura 1 día
        response.addCookie(cookie);
        return "redirect:/mostrarNombre";
    }

    @GetMapping("/mostrarNombre")
    public String mostrarNombre(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        String nombre = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("nombre".equals(cookie.getName())) {
                    nombre = cookie.getValue();
                    break;
                }
            }
        }
        if (nombre == null) {
            return "redirect:/nombre";
        }

        model.addAttribute("nombre", nombre);
        return "mostrarNombre";
    }
}
