package io.nikolamicic21.mainapplication.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Optional;

import static org.springframework.boot.actuate.health.Health.status;
import static org.springframework.boot.actuate.health.Health.unknown;

@Slf4j
@Component
public class PingPongServiceHealthIndicator implements HealthIndicator {

    private final WebClient webClient;

    public PingPongServiceHealthIndicator(
            WebClient.Builder webClientBuilder,
            @Value("${service.ping.url}") String url
    ) {
        final var uriComponents = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/actuator/health/readiness")
                .build();

        this.webClient = webClientBuilder
                .baseUrl(uriComponents.toString())
                .build();
    }

    @Override
    public Health health() {
        return checkCustomHealth()
                .map(customHealth -> status(customHealth.getStatus()).build())
                .orElse(unknown().build());
    }

    private Optional<CustomHealth> checkCustomHealth() {
        CustomHealth customHealth;
        try {
            customHealth = this.webClient.get()
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CustomHealth.class))
                    .timeout(Duration.ofSeconds(1))
                    .block();
        } catch (RuntimeException exception) {
            log.error("Error occurred while PingPongService's health check", exception);
            customHealth = new CustomHealth(Status.DOWN);
        }

        return Optional.ofNullable(customHealth);
    }
}
