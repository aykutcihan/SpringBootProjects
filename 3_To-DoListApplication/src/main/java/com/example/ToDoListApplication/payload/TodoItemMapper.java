package com.example.ToDoListApplication.payload;


import com.example.ToDoListApplication.entity.TodoItem;

public class TodoItemMapper {

    public static TodoItem mapTodoItemRequestToEntity(TodoItemRequest todoItemRequest) {
        return TodoItem.builder()
                .title(todoItemRequest.getTitle())
                .description(todoItemRequest.getDescription())
                .completed(todoItemRequest.getCompleted() != null && todoItemRequest.getCompleted())
                .build();
    }

    public static TodoItemResponse mapTodoItemToResponse(TodoItem todoItem) {
        return TodoItemResponse.builder()
                .id(todoItem.getId())
                .title(todoItem.getTitle())
                .description(todoItem.getDescription())
                .completed(String.valueOf(todoItem.isCompleted()))
                .build();
    }
}