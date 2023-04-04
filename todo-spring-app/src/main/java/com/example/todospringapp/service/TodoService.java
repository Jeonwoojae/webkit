package com.example.todospringapp.service;

import com.example.todospringapp.model.TodoEntity;
import com.example.todospringapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    final private TodoRepository todoRepository;

    public Optional<TodoEntity> create(final TodoEntity entity) {
//        Validation
        validate(entity);
        todoRepository.save(entity);

        return todoRepository.findById(entity.getId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    private void validate(TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if(entity.getUserId()==null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
