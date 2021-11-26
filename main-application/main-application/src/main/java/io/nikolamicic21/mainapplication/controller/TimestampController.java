package io.nikolamicic21.mainapplication.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/timestamp")
public class TimestampController {

    private static final String UUID = java.util.UUID.randomUUID().toString();
    private static ZonedDateTime timestamp = ZonedDateTime.now();

    private final WebClient webClient;
    private final String message;

    public TimestampController(
            WebClient.Builder webClientBuilder,
            @Value("${service.ping.url}") String url,
            @Value("${service.ping.endpoint.count}") String endpoint,
            @Value("${MESSAGE}") String message) {
        final var uriComponents = UriComponentsBuilder
                .fromHttpUrl(url)
                .path(endpoint)
                .build();
        this.webClient = webClientBuilder
                .baseUrl(uriComponents.toString())
                .build();
        this.message = message;
    }

    @GetMapping
    public String getTimestamp() {
        final var pingCount = this.webClient.get()
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(String.class))
                .block();

        return String.format(
                "%s\n%s\nPing / Pongs: %s",
                this.message,
                timestamp + ": " + UUID,
                pingCount
        );
    }

    @Scheduled(fixedDelay = 5000L)
    private void updateTimestamp() {
        timestamp = ZonedDateTime.now();
    }

}
