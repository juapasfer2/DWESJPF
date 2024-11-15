package org.example.harrypotter.controllers;

import org.example.harrypotter.entities.House;
import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.HouseRepository;
import org.example.harrypotter.repositories.StudentRepository;
import org.example.harrypotter.services.HouseService;
import org.example.harrypotter.services.HouseServiceImplementation;
import org.example.harrypotter.services.StudentService;
import org.example.harrypotter.services.StudentServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Random;

@Controller
public class IndexController {
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());
    private HouseService houseService = new HouseServiceImplementation(new HouseRepository());

    @GetMapping("/")
    public String index(Model model) {
        List<House> casas = houseService.getHouses();
        Random random = new Random();
        int x = random.nextInt(casas.size());
        model.addAttribute("casa", casas.get(x));

        List<Student> students = studentService.getStudents();
        int y = random.nextInt(students.size());
        model.addAttribute("student", students.get(y));
        return "index";
    }
}
