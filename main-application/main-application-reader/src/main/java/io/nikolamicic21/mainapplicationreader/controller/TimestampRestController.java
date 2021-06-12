package io.nikolamicic21.mainapplicationreader.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/timestamp")
public class TimestampRestController {

    private final Path timestampFilePath;
    private final Path pingFilePath;

    public TimestampRestController(
            @Value("${timestamp.file-path}") String timestampFilePath,
            @Value("${ping.file-path}") String pingFilePath) {
        this.timestampFilePath = Paths.get(timestampFilePath);
        this.pingFilePath = Paths.get(pingFilePath);
    }

    @GetMapping
    public String getTimestamp() throws IOException {
        var timestampText = "No data";
        if (Files.exists(this.timestampFilePath)) {
            timestampText = Files.readString(this.timestampFilePath);
        }
        var pingText = "No data";
        if (Files.exists(this.pingFilePath)) {
            pingText = Files.readString(this.pingFilePath);
        }
        return String.format("%s\nPing / Pong: %s", timestampText, pingText);
    }

}
