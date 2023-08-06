package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.DataNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long currentId = 0L;

    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(f -> f.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++currentId);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        if (!faculties.containsKey(id)) {
            throw new DataNotFoundException();
        }
        Faculty editedFaculty = faculties.get(id);
        editedFaculty.setName(faculty.getName());
        editedFaculty.setColor(faculty.getColor());
        return editedFaculty;
    }
    public void deleteFaculty(Long id) {
        if (faculties.remove(id) == null) {
            throw new DataNotFoundException();
        }
    }
}
