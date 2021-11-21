package io.nikolamicic21.simpletodoapp.repository;

import io.nikolamicic21.simpletodoapp.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
