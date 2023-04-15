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
    CANNOT_FIND_USER(HttpStatus.NOT_FOUND,"해당하는 유저를 찾을 수 없습니다."),
    CANNOT_FIND_PRODUCT(HttpStatus.NOT_FOUND, "해당하는 물품을 찾을 수 없습니다."),
    CANNOT_FIND_BID(HttpStatus.NOT_FOUND, "해당하는 입찰을 찾을 수 없습니다."),
    /**
     * 409 error
     */
    CANNOT_CREATE_USER(HttpStatus.CONFLICT,"이미 가입된 유저입니다."),
    LOGIN_ERROR(HttpStatus.BAD_GATEWAY, " 로그인 중 에러 발생"),
    BAD_TOKEN(HttpStatus.UNAUTHORIZED,"토큰 검증 에러"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"엑세스 토큰 검증 에러"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레쉬 토큰 검증 에러"),

    CANNOT_CONVERT(HttpStatus.NOT_FOUND,"dto 변환 에러")
    ;

//    스테이터스 코드 커스텀 가능
    private final HttpStatus status;
    private final String message;
}
