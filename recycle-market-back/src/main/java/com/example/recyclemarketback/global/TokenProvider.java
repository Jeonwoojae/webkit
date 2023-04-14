package com.example.recyclemarketback.global;

import com.example.recyclemarketback.global.exception.CustomException;
import com.example.recyclemarketback.global.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {
    private final Environment env;

    @Autowired
    public TokenProvider(Environment env) {
        this.env = env;
    }

    public void checkValidToken(String token) {
        try{
            Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(token).getBody()
                    .getSubject();
        } catch (ExpiredJwtException e){
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }

    public String getPhoneNumberFromAccessToken(String atk) {
        String phoneNumber = null;
        atk = atk.substring(7);
        try{
            phoneNumber = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(atk).getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            phoneNumber = e.getClaims().getSubject();
        } if (phoneNumber == null) throw new CustomException(ErrorCode.BAD_TOKEN); // 토큰 검증 에러

        return phoneNumber;
    }
}

