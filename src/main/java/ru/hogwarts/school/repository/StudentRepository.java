package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge(Integer age);
    List<Student> findByAgeBetween(int min, int max);
    List<Student> findAllByFaculty_Id(Long facultyId);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int getAmountOfStudents();
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    double getAverageAgeOfStudents();
    @Query(value = "select * from student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
