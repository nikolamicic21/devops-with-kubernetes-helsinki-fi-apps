package io.nikolamicic21.simpletodoapp.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CreateTodoDto {

    @Size(min = 3, max = 140, message = "Todo's title cannot be longer than 140 chars and shorter than 3 chars")
    private String title;

}
