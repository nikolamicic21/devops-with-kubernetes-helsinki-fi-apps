package io.nikolamicic21.simpletodoapp.dto;

import io.nikolamicic21.simpletodoapp.model.TodoStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateTodoDto {

    @Size(min = 3, max = 140, message = "Todo's title cannot be longer than 140 chars and shorter than 3 chars")
    @NotBlank
    private String title;

    private TodoStatus status;

}
