package io.nikolamicic21.simpletodoapp.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TodoEventListener {

    private final Connection natsConnection;
    private final String todoMutationSubject;
    private final ObjectMapper objectMapper;

    public TodoEventListener(
            Connection natsConnection,
            @Value("${event.todo.subject}") String todoMutationSubject,
            ObjectMapper objectMapper
    ) {
        this.natsConnection = natsConnection;
        this.todoMutationSubject = todoMutationSubject;
        this.objectMapper = objectMapper;
    }

    @EventListener(TodoMutationEvent.class)
    public void onTodoMutationEvent(TodoMutationEvent event) {
        try {
            final var todo = this.objectMapper.writeValueAsBytes(event.getSource());
            this.natsConnection.publish(this.todoMutationSubject, todo);
        } catch (JsonProcessingException exception) {
            log.error("Error occurred while trying to serialize Todo object", exception);
        }
    }

}
