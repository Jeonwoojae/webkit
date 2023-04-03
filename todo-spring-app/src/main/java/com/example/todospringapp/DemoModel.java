package com.example.todospringapp;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
// Builder패턴을 사용하여 생성자를 만드는
@RequiredArgsConstructor
// @NonNull이나 final이 적용된 필드의 생성자를 만들어준다.
public class DemoModel {
    @NonNull
    private String id;
}
