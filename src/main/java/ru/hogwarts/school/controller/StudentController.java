package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentInfo(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/filtered")
    public Collection<Student> getStudentByAge(@RequestParam("age") Integer age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/sorted")
    public Collection<Student> getStudentsByAgePeriod(@RequestParam("min") Integer min,
                                                      @RequestParam("max") Integer max) {
        return studentService.findByAgeBetween(min, max);
    }
    @GetMapping("/by-faculty")
    public Collection<Student> getStudentsByFaculty (@RequestParam Long facultyId) {
        return studentService.getByFacultyId(facultyId);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public Student editStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return studentService.editStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }
}
