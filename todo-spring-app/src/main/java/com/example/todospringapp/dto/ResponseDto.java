package com.example.todospringapp.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Getter
public class ResponseDto<T> {
    private String error;
    private List<T> data;
}
