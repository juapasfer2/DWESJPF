package com.example.t3practica4.controller;

import com.example.t3practica4.entities.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class NombreController {

    // Muestra el formulario
    @GetMapping("/nombre")
    public String mostrarFormulario() {
        return "nombre";
    }

    @PostMapping("/guardarNombre")
    public String guardarNombre(@RequestParam("nombre") String nombre, HttpSession session) {
        Cookie cookie = (Cookie) session.getAttribute("cookie");
        if (cookie == null) {
            cookie = new Cookie();
        }
        cookie.setNombre(nombre);
        session.setAttribute("cookie", cookie);
        return "redirect:/mostrarNombre";
    }


    @GetMapping("/mostrarNombre")
    public String mostrarNombre(HttpSession session, Model model) {
        Cookie cookie = (Cookie) session.getAttribute("cookie");
        if (cookie == null || cookie.getNombre() == null) {
            return "redirect:/nombre";
        }
        cookie.increment();
        model.addAttribute("nombre", cookie.getNombre());
        model.addAttribute("count", cookie.getCount());
        session.setAttribute("cookie", cookie);
        return "mostrarNombre";
    }
}
