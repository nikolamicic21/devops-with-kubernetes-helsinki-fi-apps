package io.nikolamicic21.simpletodoapp.controller;

import io.nikolamicic21.simpletodoapp.dto.CreateTodoDto;
import io.nikolamicic21.simpletodoapp.dto.UpdateTodoDto;
import io.nikolamicic21.simpletodoapp.exception.ResourceNotFoundException;
import io.nikolamicic21.simpletodoapp.model.Todo;
import io.nikolamicic21.simpletodoapp.model.TodoStatus;
import io.nikolamicic21.simpletodoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Slf4j
class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping
    Iterable<Todo> getAll() {
        return this.todoRepository.findAll();
    }

    @PostMapping
    Todo add(@Valid @RequestBody CreateTodoDto todoDto) {
        log.info("Received Todo create request object: {}", todoDto.toString());
        return this.todoRepository.save(mapDtoToTodo(todoDto));
    }

    @PutMapping("/{id}")
    Todo update(@Valid @RequestBody UpdateTodoDto todoDto, @PathVariable Long id) {
        log.info("Received Todo update request object: {}", todoDto.toString());
        return this.todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(todoDto.getTitle());
                    if (todoDto.getStatus() != null) {
                        todo.setStatus(todoDto.getStatus());
                    }
                    return this.todoRepository.save(todo);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException(format("No Todo resource with id %s", id)));
    }

    private Todo mapDtoToTodo(CreateTodoDto todoDto) {
        final var todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setStatus(TodoStatus.NEW);

        return todo;
    }
}
