package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public List<Faculty> getAllFaculties(@RequestParam(required = false) String name,
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
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }
    @GetMapping("/by-student")
    public Faculty getFacultyByStudent(@RequestParam Long studentId) {
        return facultyService.getByStudentId(studentId);
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
