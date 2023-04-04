package com.example.todospringapp.repository;

import com.example.todospringapp.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, String> {
}
