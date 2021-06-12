package io.nikolamicic21.pingpongapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PingPongRestController {

    private static final AtomicInteger COUNT = new AtomicInteger(0);

    @GetMapping("/pingpong")
    public String getPongResponse() {
        return String.format("pong %s", COUNT.getAndIncrement());
    }

    @GetMapping("/count")
    public String getCount() {
        return String.valueOf(COUNT.get());
    }

}
