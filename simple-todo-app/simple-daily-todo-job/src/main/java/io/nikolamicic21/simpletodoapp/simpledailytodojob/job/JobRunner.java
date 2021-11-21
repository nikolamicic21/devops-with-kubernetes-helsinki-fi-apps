package io.nikolamicic21.simpletodoapp.simpledailytodojob.job;

import io.nikolamicic21.simpletodoapp.simpledailytodojob.model.Todo;
import io.nikolamicic21.simpletodoapp.simpledailytodojob.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JobRunner implements CommandLineRunner {

    private final TodoRepository todoRepository;
    private final WebClient webClient;

    public JobRunner(TodoRepository todoRepository, WebClient.Builder webClientBuilder) {
        this.todoRepository = todoRepository;
        this.webClient = webClientBuilder
                .baseUrl("https://en.wikipedia.org/wiki/Special:Random")
                .build();
    }

    @Override
    public void run(String... args) {
        final var responseEntity = this.webClient.get()
                .retrieve()
                .toBodilessEntity()
                .block();
        if (responseEntity != null) {
            final var headers = responseEntity.getHeaders();
            if (headers.getLocation() != null) {
                final var location = headers.getLocation();
                final var dailyTodo = new Todo();
                dailyTodo.setTitle("Read " + location.toString());
                this.todoRepository.save(dailyTodo);
            }
        }
    }
}
