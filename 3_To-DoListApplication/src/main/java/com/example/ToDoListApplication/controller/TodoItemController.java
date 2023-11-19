package com.example.ToDoListApplication.controller;

import com.example.ToDoListApplication.payload.ResponseMessage;
import com.example.ToDoListApplication.payload.TodoItemRequest;
import com.example.ToDoListApplication.payload.TodoItemResponse;
import com.example.ToDoListApplication.service.TodoItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor
public class TodoItemController {

    private final TodoItemService todoItemService;

    @PostMapping("/save")
    public ResponseMessage<TodoItemResponse> createPost(@RequestBody @Valid TodoItemRequest todoItemRequest) {
        return todoItemService.createPost(todoItemRequest);
    }

    @GetMapping("/{id}")
    public ResponseMessage<TodoItemResponse> getTodoItemById(@PathVariable Long id) {
        return todoItemService.getTodoItemById(id);
    }

    @GetMapping("/getAll")
    public List<TodoItemResponse> getAllTodoList(){
        return todoItemService.getAllTodoList();
    }

    @GetMapping("/page")
    public Page<TodoItemResponse> getAllTodoItemsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String type) {

        return todoItemService.getAllTodoItemsByPage(page, size, sort, type);
    }


    @PutMapping("/update/{id}")
    public ResponseMessage<TodoItemResponse> updateBlogPost(@PathVariable Long id,
                                                            @RequestBody @Valid TodoItemRequest todoItemRequest) {
        return todoItemService.updateTodoItems(id, todoItemRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteTodoList(@PathVariable Long id) {

        return  todoItemService.deleteTodoItem(id);
    }


}
