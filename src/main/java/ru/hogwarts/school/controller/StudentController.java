package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

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
    public Student getStudentsById(@PathVariable("id") Long id) {
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
    public Collection<Student> getStudentsByFaculty(@RequestParam Long facultyId) {
        return studentService.getByFacultyId(facultyId);
    }

    @GetMapping("/amount")
    public int getAmountOfStudents() {
        return studentService.getAmountOfStudents();
    }

    @GetMapping("/average-age")
    public double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/last-five")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
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
