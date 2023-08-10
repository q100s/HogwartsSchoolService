package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultiesByName(String name) {
        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(f -> f.getColor().equalsIgnoreCase(color))
                .toList();
    }

    public List<String> getStudentsByFaculty(Long id) {
        List<Student> students = getFacultyById(id).getStudents().stream().toList();
        List<String> studentsNames = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            studentsNames.add(students.get(i).getName());
        }
        return studentsNames;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        Faculty editedFaculty = facultyRepository.findById(id).get();
        editedFaculty.setName(faculty.getName());
        editedFaculty.setColor(faculty.getColor());
        return facultyRepository.save(editedFaculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
