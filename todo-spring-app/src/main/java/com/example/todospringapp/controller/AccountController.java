package com.example.todospringapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.todospringapp.service.MailService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final MailService mailService;

    @PostMapping("mail/check")
    @ResponseBody
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = mailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }
}
