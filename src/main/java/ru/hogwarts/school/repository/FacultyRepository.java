package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Optional;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByColorIgnoreCase(String color);

    List<Faculty> findAllByNameIgnoreCase(String name);

    Optional<Faculty> findByStudents_Id(Long studentId);
}
