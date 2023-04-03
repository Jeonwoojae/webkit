package com.example.todospringapp.service;

import com.example.todospringapp.entity.TodoEntity;
import com.example.todospringapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    final private TodoRepository todoRepository;

    public String testService() {
//        Todo Entity 생성
        TodoEntity entity = TodoEntity.builder().userId("user01").title("My first todo item").build();
//        Todo Entity 저장
        todoRepository.save(entity);
//        Todo Entity 검색
        TodoEntity savedEntity = todoRepository.findByUserId(entity.getUserId()).get(0);

        return savedEntity.getUserId();
    }
}
