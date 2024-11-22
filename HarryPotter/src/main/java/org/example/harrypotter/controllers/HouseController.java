package org.example.harrypotter.controllers;

import lombok.Getter;


import org.example.harrypotter.entities.House;
import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.HouseRepository;
import org.example.harrypotter.repositories.StudentRepository;
import org.example.harrypotter.services.HouseService;
import org.example.harrypotter.services.HouseServiceImplementation;
import org.example.harrypotter.services.StudentService;
import org.example.harrypotter.services.StudentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
@Controller
public class HouseController {
    private HouseService houseService = new HouseServiceImplementation(new HouseRepository());
    private StudentService studentService = new StudentServiceImplementation(new StudentRepository());


    @GetMapping("/houses")
    public String listHouses(Model model){
        List<House> houses = houseService.getHouses();
        model.addAttribute("houses", houses);
        return "houses";
    }

    @GetMapping("/house/{name}")
    public String showHouse(@PathVariable String name, Model model){
        List<Student> students = studentService.getStudentsByHouse(name);
        model.addAttribute("house", houseService.getHouseByName(name));
        model.addAttribute("students", students);
        return "houseStudents";
    }

    @GetMapping("/houses/create")
    public String createHouse(Model model){
        model.addAttribute("house", new House());
        return "houseCreate";
    }
    @PostMapping("houses/create")
    public String createHouse(House house){
        houseService.addHouse(house);
        return "redirect:/houses";
    }

    @GetMapping("/house/update/{name}")
    public String updateHouse(@PathVariable String name , Model model){
        model.addAttribute("house", houseService.getHouseByName(name));
        return"updateHouse";
    }

    @PostMapping("/house/update/{name}")
    public String updateHouse(@PathVariable String name, House house){
        houseService.updateHouse(name, house);
        return "redirect:/houses";
    }

    @GetMapping("/house/delete/{name}")
    public String deleteHouse(@PathVariable String name){
        houseService.deleteHouse(name);
        return "redirect:/houses";
    }
    //
    @GetMapping("/house/createstudent/{name}")
    public String createStudent(@PathVariable String name, Model model){
        model.addAttribute("house", houseService.getHouseByName(name));
        model.addAttribute("student", new Student());
        return "createStudentForm";
    }
    @PostMapping("house/createstudent/{name}")
    public String createStudent(@PathVariable String name, Student student){
        student.setHouse(houseService.getHouseByName((name)));
        studentService.addStudentToHouse(name, student);
        return "redirect:/house/{name}";
    }


}
