package com.example.recyclemarketback.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogin {
    private String phoneNumber;
    private String password;
}
