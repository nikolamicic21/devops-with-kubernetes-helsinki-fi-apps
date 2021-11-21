package io.nikolamicic21.pingpongapp.controller;

import io.nikolamicic21.pingpongapp.model.PingPong;
import io.nikolamicic21.pingpongapp.repository.PingPongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
public class PingPongRestController {

    private final PingPongRepository repository;

    @GetMapping("/pingpong")
    public String getPongResponse() {
        final var foundPingPong = this.repository.findByTitle(PingPongRepository.TITLE);
        final PingPong savedPingPong;
        if (foundPingPong.isPresent()) {
            final var pingPong = foundPingPong.get();
            pingPong.setCount(pingPong.getCount() + 1);
            savedPingPong = this.repository.save(pingPong);
        } else {
            final var pingPong = new PingPong();
            pingPong.setTitle(PingPongRepository.TITLE);
            pingPong.setCount(1L);
            savedPingPong = this.repository.save(pingPong);
        }

        return String.format("pong %s", savedPingPong.getCount());
    }

    @GetMapping("/count")
    public String getCount() {
        final var foundPingPong = this.repository.findByTitle(PingPongRepository.TITLE);
        final Long count;
        if (foundPingPong.isPresent()) {
            count = foundPingPong.get().getCount();
        } else {
            count = 0L;
        }

        return String.valueOf(count);
    }
}
