package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController controller;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void getAllStudents() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class));
    }

    @Test
    public void getStudentById() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/1", String.class));
    }

    @Test
    public void getStudentByAge() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/filtered", String.class));
    }

    @Test
    public void getStudentsByAgePeriod() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/sorted", String.class));
    }

    @Test
    public void getStudentsByFaculty() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/by-faculty", String.class));
    }

    @Test
    public void createStudent() throws Exception {
        Student student = new Student(null, "TestedStudent", 22);
        assertNotNull(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class));
    }

    @Test
    public void editStudent() throws Exception {
        Student student = new Student(null, "TestedStudent", 22);
        controller.createStudent(student);
        String name = student.getName();
        Long id = student.getId();
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> response = testRestTemplate.exchange("/student/{id}", HttpMethod.PUT, entity,
                Student.class, id);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody().getId());
        assertEquals(response.getBody().getName(), name);
        controller.deleteStudent(student.getId());
    }

    @Test
    public void deleteStudent() throws Exception {
        Student student = new Student(null, "TestedStudent", 22);
        controller.createStudent(student);
        Long id = student.getId();
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> response = testRestTemplate.exchange("/student/{id}", HttpMethod.DELETE, entity,
                Student.class, id);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
