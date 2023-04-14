package com.example.recyclemarketback.domain.member.dto;

import com.example.recyclemarketback.domain.member.entity.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String username;
    private String phoneNumber;
    private String password;
    private String addressInfo;
    private String addressDetails;
    private MemberType memberType;
}
