package com.example.todospringapp.controller;

import com.example.todospringapp.dto.ResponseDto;
import com.example.todospringapp.dto.UserDto;
import com.example.todospringapp.model.UserEntity;
import com.example.todospringapp.security.TokenProvider;
import com.example.todospringapp.service.UserSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {
    final private UserSerivce userSerivce;
    final private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        try{
            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build();

            UserEntity registeredUsesr = userSerivce.create(user);
            UserDto responseUserDto = UserDto.builder()
                    .email(registeredUsesr.getEmail())
                    .username(registeredUsesr.getUsername())
                    .id(registeredUsesr.getId())
                    .build();

            return ResponseEntity.ok().body(responseUserDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        UserEntity user = userSerivce.getByCredentials(userDto.getEmail(), userDto.getPassword());

        if(user != null){
            // 로그인한 사용자 정보로 토큰 생성
            final String token = tokenProvider.create(user);
            final UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(userDto.getId())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(responseUserDto);
        } else {
            ResponseDto responseDto = ResponseDto.builder()
                    .error("Login failed")
                    .build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
