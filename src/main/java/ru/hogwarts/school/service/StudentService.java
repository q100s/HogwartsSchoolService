package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.DataNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long currentId = 0L;

    public Student getStudentById(Long id) {
        return students.get(id);
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Collection<Student> getStudentsByAge(int age) {
        return students.values().stream()
                .filter(s->s.getAge() == age)
                .collect(Collectors.toList());
    }

    public Student createStudent(Student student) {
        student.setId(++currentId);
        students.put(student.getId(), student);
        return student;
    }

    public Student editStudent(Long id, Student student) {
        if (!students.containsKey(id)) {
            throw new DataNotFoundException();
        }
        Student editedStudent = students.get(id);
        editedStudent.setName(student.getName());
        editedStudent.setAge(student.getAge());
        return editedStudent;
    }
    public void deleteStudent(Long id) {
        if (students.remove(id) == null) {
            throw new DataNotFoundException();
        }
    }
}
