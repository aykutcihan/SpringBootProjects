package com.example.ToDoListApplication.service;

import com.example.ToDoListApplication.entity.TodoItem;
import com.example.ToDoListApplication.payload.ResponseMessage;
import com.example.ToDoListApplication.payload.TodoItemMapper;
import com.example.ToDoListApplication.payload.TodoItemRequest;
import com.example.ToDoListApplication.payload.TodoItemResponse;
import com.example.ToDoListApplication.repository.TodoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;

    public ResponseMessage<TodoItemResponse> createPost(TodoItemRequest todoItemRequest) {

        TodoItem todoItem = TodoItemMapper.mapTodoItemRequestToEntity(todoItemRequest);
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        TodoItemResponse todoItemResponse = TodoItemMapper.mapTodoItemToResponse(savedTodoItem);

        return ResponseMessage.<TodoItemResponse>builder()
                .object(todoItemResponse)
                .message("Todo item successfully created")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage<TodoItemResponse> getTodoItemById(Long id) {

        Optional<TodoItem> todoItemOptional = todoItemRepository.findById(id);

        if (todoItemOptional.isPresent()) {
            TodoItemResponse todoItemResponse = TodoItemMapper.mapTodoItemToResponse(todoItemOptional.get());
            return ResponseMessage.<TodoItemResponse>builder()
                    .object(todoItemResponse)
                    .message("Todo item successfully created")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<TodoItemResponse>builder()
                    .object(null)
                    .message("Todo item not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

    }

    public List<TodoItemResponse> getAllTodoList() {
        List<TodoItem> todoItemList = todoItemRepository.findAll();

        // Her bir TodoItem'ı TodoItemResponse'a dönüştür ve listeye ekle
        return todoItemList.stream()
                .map(TodoItemMapper::mapTodoItemToResponse)
                .collect(Collectors.toList());
    }

    public Page<TodoItemResponse> getAllTodoItemsByPage(int page, int size, String sort, String type) {
        Sort.Direction direction = "asc".equalsIgnoreCase(type) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<TodoItem> todoItemPage = todoItemRepository.findAll(pageRequest);
        return todoItemPage.map(TodoItemMapper::mapTodoItemToResponse);
    }

    public ResponseMessage<TodoItemResponse> updateTodoItems(Long id, TodoItemRequest todoItemRequest) {
        Optional<TodoItem> todoItemOptional = todoItemRepository.findById(id);

        if (todoItemOptional.isPresent()) {
            TodoItem todoItem = todoItemOptional.get();
            todoItem.setTitle(todoItemRequest.getTitle());
            todoItem.setDescription(todoItemRequest.getDescription());
            todoItem.setCompleted(todoItemRequest.getCompleted() != null && todoItemRequest.getCompleted());

            TodoItem updatedTodoItem = todoItemRepository.save(todoItem);

            TodoItemResponse todoItemResponse = TodoItemMapper.mapTodoItemToResponse(updatedTodoItem);
            return ResponseMessage.<TodoItemResponse>builder()
                    .object(todoItemResponse)
                    .message("Todo item successfully updated")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<TodoItemResponse>builder()
                    .object(null)
                    .message("Todo item not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();       


        }
    }

    public ResponseMessage<?> deleteTodoItem(Long id) {
        if (todoItemRepository.existsById(id)) {
            todoItemRepository.deleteById(id);
            return ResponseMessage.<TodoItemResponse>builder()
                    .object(null)
                    .message("Todo item successfully deleted")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<TodoItemResponse>builder()
                    .object(null)
                    .message("Todo item not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
