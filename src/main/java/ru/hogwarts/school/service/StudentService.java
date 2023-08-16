package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.DataNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student editStudent(Long id, Student student) {
        Student editedStudent = studentRepository.findById(id).orElseThrow(DataNotFoundException::new);
        Optional.ofNullable(student.getName()).ifPresent(editedStudent::setName);
        Optional.ofNullable(student.getAge()).ifPresent(editedStudent::setAge);
        return studentRepository.save(editedStudent);
    }

    public Student deleteStudent(Long id) {
        Student deletedStudent = studentRepository.findById(id).orElseThrow(DataNotFoundException::new);
        studentRepository.delete(deletedStudent);
        return deletedStudent;
    }

    public Collection<Student> findByAgeBetween(int max, int min) {
        return studentRepository.findByAgeBetween(max, min);
    }
}
