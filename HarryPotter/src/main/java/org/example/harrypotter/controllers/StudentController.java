package org.example.harrypotter.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Controller
public class StudentController {
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());
    private HouseService houseService = new HouseServiceImplementation(new HouseRepository());


    @GetMapping("/student")
    public String showStudent(@RequestParam String name, Model model){
        Student student = studentService.getStudentByName(name);
        model.addAttribute("student", student);
        return "student";
    }

    @GetMapping("/students")
    public String ShowStudentsByPatronusAndName(@RequestParam(required = false) String patronus, @RequestParam(required = false) String name, Model model){
        List<Student> students = studentService.getStudents();

        if(name !=null && patronus==null){
            model.addAttribute("students", students.stream().filter(s -> s.getName().toLowerCase().startsWith(name.toLowerCase())).toList());
        }
        if (name !=null && patronus !=null) {
            model.addAttribute("students", students.stream().filter(s -> s.getName().toLowerCase().startsWith(name.toLowerCase())).filter(s-> s.getPatronus().toLowerCase().startsWith(patronus.toLowerCase())).toList());

        }
        if(name==null && patronus !=null){
            model.addAttribute("students", students.stream().filter(s -> s.getPatronus().toLowerCase().startsWith(patronus.toLowerCase())).toList());
        }

        return "studentsbypatronusandname";
    }


}
