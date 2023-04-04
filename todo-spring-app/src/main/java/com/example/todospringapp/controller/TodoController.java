package com.example.todospringapp.controller;

import com.example.todospringapp.dto.ResponseDto;
import com.example.todospringapp.dto.TodoDto;
import com.example.todospringapp.model.TodoEntity;
import com.example.todospringapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    final private TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto){
        try {
            /** POST localhost:8080/todo
             * {
             *     "title":"My first todo",
             *     "done":false
             * }
             * */
            log.info("Log:createTodo entrance");

//            dto를 이용해 테이블에 저장하기 위한 entity를 생성한다.
            TodoEntity entity = TodoDto.toEntity(dto);
            log.info("Log:dto => entity ok!");

//            entity userId를 임시로 지정한다.
            entity.setUserId("temporary-userid");

//            service.create를 통해 repository에 entity를 저장한다.
//            이때 넘어오는 값이 없을 수도 있으므로 List가 아닌 Optional로 한다.
            Optional<TodoEntity> entities = todoService.create(entity);
            log.info("Log:service.create ok!");

//            entities를 dtos로 스트림 변환한다.
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
            log.info("Log:entities => dtos ok!");

//            Response Dto를 생성한다.
            /**
             * {
             *     "error": null,
             *     "data": [
             *         {
             *             "id": "402865c887460a880187460b34240000",
             *             "title": "My first todo",
             *             "done": false
             *         }
             *     ]
             * }
             * */
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
            log.info("Log:responsedto ok!");

//            HTTP Status 200 상태로 response를 전송한다.
            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body("this is error");
        }
    }
}
