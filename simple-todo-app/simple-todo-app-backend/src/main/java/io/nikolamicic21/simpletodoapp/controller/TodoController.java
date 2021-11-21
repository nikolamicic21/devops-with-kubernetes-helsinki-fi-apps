package io.nikolamicic21.simpletodoapp.controller;

import io.nikolamicic21.simpletodoapp.model.Todo;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final Collection<Todo> todos = ConcurrentHashMap.newKeySet();

    @GetMapping
    public Collection<Todo> getAll() {
        return this.todos;
    }

    @PostMapping
    public Todo add(@RequestBody Todo todo) {
        this.todos.add(todo);

        return todo;
    }
}
