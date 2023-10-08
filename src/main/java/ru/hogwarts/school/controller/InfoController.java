package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoController.class);
    private Environment env;

    public InfoController(Environment env) {
        this.env = env;
    }

    @GetMapping("/get-port")
    public String getPort() {
        return "Port is: " + env.getProperty("server.port");
    }

    @GetMapping("/task4")
    public int task4() {
        long startTime = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        long consumedTime = System.currentTimeMillis() - startTime;
        LOGGER.info("working time: " + consumedTime);
        return sum;
    }
}
