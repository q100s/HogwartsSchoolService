package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }
    public Collection<Student> getStudentsByAgePeriod(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findAll().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
    public Faculty getStudentsFaculty(Long id) {
        return getStudentById(id).getFaculty();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student editStudent(Long id, Student student) {
        Student editedStudent = studentRepository.findById(id).get();
        editedStudent.setName(student.getName());
        editedStudent.setAge(student.getAge());
        return studentRepository.save(editedStudent);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    public Collection<Student> findByAgeBetween(int max, int min) {
        return studentRepository.findByAgeBetween(max, min);
    }
}
