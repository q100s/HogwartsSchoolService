package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerMockTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository repository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private StudentController controller;
    @InjectMocks
    private AvatarController avatarController;
    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void getAllStudents() throws Exception {
        List<Student> testedList = new ArrayList<>(List.of(
                new Student(1L, "testedName1", 20),
                new Student(3L, "testedName2", 20),
                new Student(2L, "testedName3", 20)));
        when(repository.findAll()).thenReturn(testedList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentById() throws Exception {
        JSONObject object = new JSONObject();
        final String name = "testName";
        final int age = 20;
        final Long id = 1L;
        object.put("name", name);
        object.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(20);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentsByAge() throws Exception {
        int testedAge = 20;
        List<Student> testedList = new ArrayList<>(List.of(
                new Student(1L, "testedName1", testedAge),
                new Student(3L, "testedName2", testedAge),
                new Student(2L, "testedName3", testedAge)));
        when(repository.findAllByAge(testedAge)).thenReturn(testedList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filtered")
                        .param("age", "20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsByAgePeriod() throws Exception {
        int testedAge1 = 20;
        int testedAge2 = 25;
        List<Student> sortedList = new ArrayList<>(List.of(
                new Student(1L, "testedName1", testedAge1),
                new Student(2L, "testedName2", testedAge2)));
        when(repository.findByAgeBetween((testedAge1 + 1), (testedAge2 - 1))).thenReturn(sortedList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/sorted")
                        .param("min", "20")
                        .param("max", "25")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsByFaculty() throws Exception {
        List<Student> sortedList = new ArrayList<>(List.of(
                new Student(1L, "testedName1", 20),
                new Student(2L, "testedName2", 30)));
        when(repository.findAllByFaculty_Id(any(Long.class))).thenReturn(sortedList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/by-faculty")
                        .param("facultyId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createStudent() throws Exception {
        JSONObject object = new JSONObject();
        final String name = "testName";
        final int age = 20;
        final Long id = 1L;
        object.put("name", name);
        object.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(20);

        when(repository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void editStudent() throws Exception {
        JSONObject object = new JSONObject();
        final String name = "testName";
        final int age = 20;
        final Long id = 1L;
        object.put("name", name);
        object.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(20);

        when(repository.save(any(Student.class))).thenReturn(student);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/1")
                        .param("id", "1")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudent() throws Exception {
        JSONObject object = new JSONObject();
        final String name = "testName";
        final int age = 20;
        final Long id = 1L;
        object.put("name", name);
        object.put("age", age);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(20);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .param("id", "1")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
