package com.example.todospringapp.repository;

import com.example.todospringapp.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
//    SELECT u FROM TodoReposiotry t WHERE t.userId = `{userId}`
    List<TodoEntity> searchByUserId(String userId);
}
