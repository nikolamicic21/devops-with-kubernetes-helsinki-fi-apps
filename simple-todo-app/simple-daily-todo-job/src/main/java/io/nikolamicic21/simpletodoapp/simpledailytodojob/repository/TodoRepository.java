package io.nikolamicic21.simpletodoapp.simpledailytodojob.repository;

import io.nikolamicic21.simpletodoapp.simpledailytodojob.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
