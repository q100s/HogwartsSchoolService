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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController controller;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void getFaculties() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty", String.class));
    }

    @Test
    public void getFacultyById() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class));
    }

    @Test
    public void getFacultyByStudent() throws Exception {
        assertNotNull(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/by-student", String.class));
    }

    @Test
    public void createFaculty() throws Exception {
        Faculty faculty = new Faculty(null, "TestedFaculty", "test");
        assertNotNull(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class));
    }

    @Test
    public void editFaculty() throws Exception {
        Faculty faculty = new Faculty(null, "TestedFaculty", "test");
        controller.createFaculty(faculty);
        String name = faculty.getName();
        String color = faculty.getColor();
        Long id = faculty.getId();
        HttpEntity<Faculty> entity = new HttpEntity<Faculty>(faculty);
        ResponseEntity<Faculty> response = testRestTemplate.exchange("/faculty/{id}", HttpMethod.PUT, entity,
                Faculty.class, id);
        assertNotNull(response.getBody().getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getName(), name);
        assertEquals(response.getBody().getColor(), color);
        controller.deleteFaculty(faculty.getId());
    }

    @Test
    public void deleteStudent() throws Exception {
        Faculty faculty = new Faculty(null, "TestedFaculty", "test");
        controller.createFaculty(faculty);
        Long id = faculty.getId();
        HttpEntity<Faculty> entity = new HttpEntity<Faculty>(faculty);
        ResponseEntity<Faculty> response = testRestTemplate.exchange("/faculty/{id}", HttpMethod.DELETE, entity,
                Faculty.class, id);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
