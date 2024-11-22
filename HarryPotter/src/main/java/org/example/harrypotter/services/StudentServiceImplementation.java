package org.example.harrypotter.services;

import org.example.harrypotter.entities.Student;
import org.example.harrypotter.repositories.StudentRepository;

import java.util.List;

public class StudentServiceImplementation implements StudentService {
    private StudentRepository studentRepository;

    public StudentServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.getStudents();
    }

    public Student getStudentByName(String name) {
        return studentRepository.getStudent(name);
    }

    public List<Student> getStudentsByHouse(String house) {
        return studentRepository.getStudentsByHouse(house);
    }

    @Override
    public void addStudentToHouse(String houseName, Student student){
        studentRepository.addStudentToHouse(houseName,student);
    }
    @Override
    public void updateStudent(String name, String houseName, Student student){
        studentRepository.updateStudent(name, houseName, student);
    }
}