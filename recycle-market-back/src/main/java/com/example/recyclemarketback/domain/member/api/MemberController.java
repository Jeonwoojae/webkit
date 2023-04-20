package com.example.recyclemarketback.domain.member.api;

import com.example.recyclemarketback.domain.member.dto.MemberDto;
import com.example.recyclemarketback.domain.member.service.MemberService;
import com.example.recyclemarketback.global.TokenProvider;
import com.example.recyclemarketback.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;


    @PostMapping("")
    public ResponseEntity<?> registerMember(@RequestBody MemberDto memberDto) {
        try{
            MemberDto registeredMember = memberService.createUser(memberDto);

            return ResponseEntity.ok().body(registeredMember);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getMemberInfo(@RequestHeader(value = "Authorization") String atk) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            MemberDto memberInfo = memberService.getUserByPhoneNumber(phoneNumber);

            return ResponseEntity.ok().body(memberInfo);
        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteMember(@RequestHeader(value = "Authorization") String atk) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);

            memberService.deleteUserByPhoneNumber(phoneNumber);
            Map<String, String> res = new HashMap<>();
            res.put("status", "ok");

            return ResponseEntity.ok().body(res);

    }

    @PatchMapping("")
    public ResponseEntity<?> updateMember(@RequestHeader(value = "Authorization") String atk,
                                          @RequestBody MemberDto memberDto) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            MemberDto updatedMember = memberService.updateUserInfoWithPhoneNumber(phoneNumber,memberDto);

            return ResponseEntity.ok().body(updatedMember);
        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}