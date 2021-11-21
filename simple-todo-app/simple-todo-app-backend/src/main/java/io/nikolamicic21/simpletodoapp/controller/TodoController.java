package io.nikolamicic21.simpletodoapp.controller;

import io.nikolamicic21.simpletodoapp.model.Todo;
import io.nikolamicic21.simpletodoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping
    public Iterable<Todo> getAll() {
        return this.todoRepository.findAll();
    }

    @PostMapping
    public Todo add(@RequestBody Todo todo) {
        return this.todoRepository.save(todo);
    }
}
