package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public Collection<Faculty> getAllFaculties(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank()) {
            return facultyService.getFacultiesByName(name);
        }
        if (color != null && !color.isBlank()) {
            return facultyService.getFacultiesByColor(color);
        }
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @GetMapping("/students/{id}")
    public List<String> getStudentsByFaculty(@PathVariable("id") Long id) {
        return facultyService.getStudentsByFaculty(id);
    }

//    @GetMapping("/filtered")
//    public Collection<Faculty> getFacultiesByColor(@RequestParam("color") String color) {
//        return facultyService.getFacultiesByColor(color);
//    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping("/{id}")
    public Faculty editStudent(@PathVariable("id") Long id, @RequestBody Faculty faculty) {
        return facultyService.editFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable("id") Long id) {
        facultyService.deleteFaculty(id);
    }
}
