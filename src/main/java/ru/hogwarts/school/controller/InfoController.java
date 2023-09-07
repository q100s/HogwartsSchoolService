package ru.hogwarts.school.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    private Environment env;

    public InfoController(Environment env) {
        this.env = env;
    }

    @GetMapping("/info")
    public String getPort() {
        return "Port is: " + env.getProperty("server.port");
    }
}
