package com.example.recyclemarketback.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 404 error
     */
    CANNOT_FIND_USER("E404","해당하는 유저를 찾을 수 없습니다."),
    CANNOT_FIND_PRODUCT("E404", "해당하는 물품을 찾을 수 없습니다."),
    CANNOT_FIND_BID("E404", "해당하는 입찰을 찾을 수 없습니다."),
    /**
     * 409 error
     */
    CANNOT_CREATE_USER("E409","이미 가입된 유저입니다."),
    LOGIN_ERROR("E400", " 로그인 중 에러 발생"),
    BAD_TOKEN("E403","토큰 검증 에러"),
    EXPIRED_ACCESS_TOKEN("E403","엑세스 토큰 검증 에러"),
    EXPIRED_REFRESH_TOKEN("E403", "리프레쉬 토큰 검증 에러"),

    CANNOT_CONVERT("E500","dto 변환 에러")
    ;

//    스테이터스 코드 커스텀 가능
    private final String status;
    private final String message;
}
