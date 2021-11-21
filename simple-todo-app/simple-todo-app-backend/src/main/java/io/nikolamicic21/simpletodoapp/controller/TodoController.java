package io.nikolamicic21.simpletodoapp.controller;

import io.nikolamicic21.simpletodoapp.dto.CreateTodoDto;
import io.nikolamicic21.simpletodoapp.model.Todo;
import io.nikolamicic21.simpletodoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping
    public Iterable<Todo> getAll() {
        return this.todoRepository.findAll();
    }

    @PostMapping
    public Todo add(@Valid @RequestBody CreateTodoDto todoDto) {
        log.info("Received Todo request object: {}", todoDto.toString());
        return this.todoRepository.save(mapToTodo(todoDto));
    }

    private Todo mapToTodo(CreateTodoDto todoDto) {
        final var todo = new Todo();
        todo.setTitle(todoDto.getTitle());

        return todo;
    }
}
