package ru.hogwarts.school.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.DataNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private int count = 0;
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(Long id) {
        logger.info("getStudentById method has been invoked");
        logger.debug("Requesting info for student with id: {}", id);
        logger.error("There is no student with id: " + id);
        return studentRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("getStudentsByAge method has been invoked");
        logger.debug("Requesting students by age: {}", age);
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> getAllStudents() {
        logger.info("getAllStudents method has been invoked");
        return studentRepository.findAll();
    }

    public Collection<Student> getByFacultyId(Long facultyId) {
        logger.info("getByFacultyId method has been invoked");
        logger.debug("Requesting students by faculty's id: {}", facultyId);
        logger.error("There is no faculty with id: " + facultyId);
        return studentRepository.findAllByFaculty_Id(facultyId);
    }

    public int getAmountOfStudents() {
        logger.info("getAmountOfStudents method has been invoked");
        return studentRepository.getAmountOfStudents();
    }

    public double getAverageAgeOfStudents() {
        logger.info("getAverageAgeOfStudents method has been invoked");
        return getAllStudents().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElseThrow(DataNotFoundException::new);
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("getLastFiveStudents method has been invoked");
        return studentRepository.getLastFiveStudents();
    }

    public Collection<String> getByA() {
        return getAllStudents().stream()
                .parallel()
                .map(s -> s.getName().toUpperCase())
                .filter(s -> StringUtils.startsWithIgnoreCase(s, "a"))
                .toList();
    }

    public void getSixNames() {
        logger.info(studentRepository.findAll().get(0).getName());
        logger.info(studentRepository.findAll().get(1).getName());

        new Thread(() -> {
            logger.info(studentRepository.findAll().get(2).getName());
            logger.info(studentRepository.findAll().get(3).getName());
        }).start();

        new Thread(() -> {
            logger.info(studentRepository.findAll().get(4).getName());
            logger.info(studentRepository.findAll().get(5).getName());
        }).start();
    }

    public void getSixNamesSynchronized() {
        List<Student> students = studentRepository.findAll();
        printName(students);
        printName(students);

        new Thread(() -> {
            printName(students);
            printName(students);
        }).start();

        new Thread(() -> {
            printName(students);
            printName(students);
        }).start();
    }

    public Student createStudent(Student student) {
        logger.info("createStudent method has been invoked");
        return studentRepository.save(student);
    }

    public Student editStudent(Long id, Student student) {
        logger.info("editStudent method has been invoked");
        logger.error("There is no student with id: " + id);
        Student editedStudent = studentRepository.findById(id).orElseThrow(DataNotFoundException::new);
        Optional.ofNullable(student.getName()).ifPresent(editedStudent::setName);
        Optional.ofNullable(student.getAge()).ifPresent(editedStudent::setAge);
        return studentRepository.save(editedStudent);
    }

    public Student deleteStudent(Long id) {
        logger.info("deleteStudent method has been invoked");
        logger.error("There is no student with id: " + id);
        Student deletedStudent = studentRepository.findById(id).orElseThrow(DataNotFoundException::new);
        studentRepository.delete(deletedStudent);
        return deletedStudent;
    }

    public Collection<Student> findByAgeBetween(int max, int min) {
        logger.info("findByAgeBetween method has been invoked");
        return studentRepository.findByAgeBetween(max, min);
    }

    private synchronized void printName(List<Student> students) {
        logger.info(students.get(count % students.size()).getName());
        count++;
    }
}
