package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge(Integer age);
    List<Student> findByAgeBetween(int min, int max);

    List<Student> findAllByFaculty_Id(Long facultyId);
}
