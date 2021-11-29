package io.nikolamicic21.simpletodoapp.event;

import org.springframework.context.ApplicationEvent;

public class TodoMutationEvent extends ApplicationEvent {
    public TodoMutationEvent(Object source) {
        super(source);
    }
}
