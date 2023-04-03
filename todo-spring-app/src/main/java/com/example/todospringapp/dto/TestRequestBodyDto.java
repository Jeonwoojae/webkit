package com.example.todospringapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
// Setter를 사용하지 않는 이유는 dto의 값이 중간에 변질될 이유가 없음에도 수정하는 함수를 둬서는 안된다.
public class TestRequestBodyDto {
    private int id;
    private String message;
}
