package com.example.recyclemarketback.domain.member.service;

import com.example.recyclemarketback.domain.member.dto.MemberDto;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.member.repository.MemberRepository;
import com.example.recyclemarketback.global.exception.CustomException;
import com.example.recyclemarketback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByPhoneNumber(username)
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_USER));

        return new User(memberEntity.getPhoneNumber(), memberEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }

    public MemberDto createUser(MemberDto memberDto) {
        if (memberRepository.existsByPhoneNumber(memberDto.getPhoneNumber())) {
            log.info("이미 가입된 유저");
            throw new CustomException(ErrorCode.CANNOT_CREATE_USER);
        }

        MemberEntity member = MemberEntity.builder()
                .name(memberDto.getUsername())
                .phoneNumber(memberDto.getPhoneNumber())
                .encryptedPwd(passwordEncoder.encode(memberDto.getPassword()))
                .address_info(memberDto.getAddressInfo())
                .address_details(memberDto.getAddressDetails())
                .memberType(memberDto.getMemberType())
                .build();

        MemberEntity registeredMember = memberRepository.save(member);

        MemberDto response = Optional.ofNullable(registeredMember)
                .map(m -> MemberDto.builder()
                        .username(m.getName())
                        .phoneNumber(m.getPhoneNumber())
                        .password(m.getEncryptedPwd())
                        .addressInfo(m.getAddress_info())
                        .addressDetails(m.getAddress_details())
                        .memberType(m.getMemberType())
                        .build())
                .orElse(null);

        return response;
    }

    public MemberDto getUserByPhoneNumber(String userPhoneNumber) {
        MemberEntity member = memberRepository.findByPhoneNumber(userPhoneNumber)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));
        MemberDto response = Optional.ofNullable(member)
                .map(m -> MemberDto.builder()
                        .username(m.getName())
                        .phoneNumber(m.getPhoneNumber())
                        .password(m.getEncryptedPwd())
                        .addressInfo(m.getAddress_info())
                        .addressDetails(m.getAddress_details())
                        .memberType(m.getMemberType())
                        .build())
                .orElse(null);

        return response;
    }

    public void deleteUserByPhoneNumber(String phoneNumber) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));
        memberRepository.delete(member);

        // TODO 지웠는데 지운 정보를 반환해야하는가
//        MemberDto response = Optional.ofNullable(member)
//                .map(m -> MemberDto.builder()
//                        .username(m.getName())
//                        .phoneNumber(m.getPhoneNumber())
//                        .password(m.getEncryptedPwd())
//                        .addressInfo(m.getAddress_info())
//                        .addressDetails(m.getAddress_details())
//                        .memberType(m.getMemberType())
//                        .build())
//                .orElse(null);
    }

    public MemberDto updateUserInfoWithPhoneNumber(String phoneNumber, MemberDto memberDto) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));

        // 프론트에서 입력 검증을 하기 때문에 빈 값의 입력은 생각하지 않음
        MemberEntity updatedMember = member.update(memberDto.getUsername(),memberDto.getAddressInfo(),memberDto.getAddressDetails());
        MemberDto response = Optional.ofNullable(updatedMember)
                .map(m -> MemberDto.builder()
                        .username(m.getName())
                        .phoneNumber(m.getPhoneNumber())
                        .password(m.getEncryptedPwd())
                        .addressInfo(m.getAddress_info())
                        .addressDetails(m.getAddress_details())
                        .memberType(m.getMemberType())
                        .build())
                .orElse(null);

        return response;
    }
}
