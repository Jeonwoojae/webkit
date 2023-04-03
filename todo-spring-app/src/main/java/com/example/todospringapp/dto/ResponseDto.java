package com.example.todospringapp.dto;

import lombok.*;

import java.util.List;

@Builder
//Builder를 사용하면 생성자가 없을시 AllArgs생성자(accesslevel=PACKAGE) 어노테이션 사용
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Getter
public class ResponseDto<T> {
    private String error;
    private List<T> data;
}
