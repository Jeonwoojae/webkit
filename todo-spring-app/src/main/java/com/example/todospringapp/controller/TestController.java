package com.example.todospringapp.controller;

import com.example.todospringapp.dto.ResponseDto;
import com.example.todospringapp.dto.TestRequestBodyDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController(){
        return "Hello World!(안녕하세요!)";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(required = false)Long id){
        return "Hello World: ID" + id;
    }

    @GetMapping("/param")
    public String testControllerRequestParam(@RequestParam(required = false) Long id) {
        return "Hello World! ID param " + id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDto testRequestBodyDto){
        return "Hello World! ID" + testRequestBodyDto.getId() + " Message : " + testRequestBodyDto.getMessage();
    }

    @GetMapping("/testResponseBody")
    public ResponseDto<String> testControllerResponseBody(){
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDto");
        list.add("See you!");
        ResponseDto<String> responseDto = ResponseDto.<String>builder().data(list).build();

        return responseDto;
    }
}
