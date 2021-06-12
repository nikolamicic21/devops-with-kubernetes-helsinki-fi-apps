package io.nikolamicic21.mainapplicationwriter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class TimestampWriterService {

    private static final String UUID = java.util.UUID.randomUUID().toString();

    private final Path filePath;

    public TimestampWriterService(@Value("${timestamp.file-path}") String filePath) {
        this.filePath = Paths.get(filePath);
    }

    @Scheduled(fixedDelay = 5000L)
    public void writeTimestampToFile() {
        try {
            Files.writeString(this.filePath, ZonedDateTime.now() + ": " + UUID);
        } catch (IOException e) {
            log.error("Error while write to a file. ", e);
        }
    }

}
