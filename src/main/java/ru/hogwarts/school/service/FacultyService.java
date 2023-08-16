package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.DataNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultiesByName(String name) {
        return facultyRepository.findAllByNameIgnoreCase(name);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAllByColorIgnoreCase(color);
    }
    public Faculty getByStudentId(Long studentId) {
        return facultyRepository.findByStudent_Id(studentId).orElseThrow(DataNotFoundException::new);
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        Faculty editedFaculty = facultyRepository.findById(id).orElseThrow(DataNotFoundException::new);
        Optional.ofNullable(faculty.getName()).ifPresent(editedFaculty::setName);
        Optional.ofNullable(faculty.getColor()).ifPresent(editedFaculty::setColor);
        return facultyRepository.save(editedFaculty);
    }

    public void deleteFaculty(Long id) {
        Faculty deletedFaculty = facultyRepository.findById(id).orElseThrow(DataNotFoundException::new);
        facultyRepository.delete(deletedFaculty);
    }
}
