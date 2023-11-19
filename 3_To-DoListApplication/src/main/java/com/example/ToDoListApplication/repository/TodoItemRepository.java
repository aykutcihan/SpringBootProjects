package com.example.ToDoListApplication.repository;

import com.example.ToDoListApplication.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
