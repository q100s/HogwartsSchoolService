package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.DataNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getFacultyById(Long id) {
        logger.info("getFacultyById method has been invoked");
        logger.debug("Requesting info for faculty with id: {}", id);
        logger.error("There is no faculty with id: " + id);
        return facultyRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public List<Faculty> getAllFaculties() {
        logger.info("getAllFaculties method has been invoked");
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultiesByName(String name) {
        logger.info("getFacultiesByName method has been invoked");
        logger.error("There is no faculties with name: " + name);
        return facultyRepository.findAllByNameIgnoreCase(name);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("getFacultiesByColor method has been invoked");
        logger.error("There is no faculties with color: " + color);
        return facultyRepository.findAllByColorIgnoreCase(color);
    }
    public Faculty getByStudentId(Long studentId) {
        logger.info("getByStudentId method has been invoked");
        logger.debug("Requesting faculties by student's id: {}", studentId);
        logger.error("There are no students with id: " + studentId);
        return facultyRepository.findByStudents_Id(studentId).orElseThrow(DataNotFoundException::new);
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("createFaculty method has been invoked");
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        logger.info("editFaculty method has been invoked");
        logger.error("There is no faculty with id: " + id);
        Faculty editedFaculty = facultyRepository.findById(id).orElseThrow(DataNotFoundException::new);
        Optional.ofNullable(faculty.getName()).ifPresent(editedFaculty::setName);
        Optional.ofNullable(faculty.getColor()).ifPresent(editedFaculty::setColor);
        return facultyRepository.save(editedFaculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("deleteFaculty method has been invoked");
        logger.error("There is no faculty with id: " + id);
        Faculty deletedFaculty = facultyRepository.findById(id).orElseThrow(DataNotFoundException::new);
        facultyRepository.delete(deletedFaculty);
    }
}
