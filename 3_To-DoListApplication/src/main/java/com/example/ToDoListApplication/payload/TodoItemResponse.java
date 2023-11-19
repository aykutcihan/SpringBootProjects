package com.example.ToDoListApplication.payload;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class TodoItemResponse {

    private Long id;
    private String title;
    private String description;
    private String completed;
}
