package com.example.recyclemarketback.domain.member.service;

import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.bids.repository.BidRepository;
import com.example.recyclemarketback.domain.member.dto.MemberDto;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.member.repository.MemberRepository;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.domain.transaction.entity.TransactionEntity;
import com.example.recyclemarketback.domain.transaction.repository.TransactionRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final BidRepository bidRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByPhoneNumber(username)
                .orElseThrow(()-> new CustomException(400,"멤버를 찾을 수 없습니다"));

        return new User(memberEntity.getPhoneNumber(), memberEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Transactional
    public MemberDto createUser(MemberDto memberDto) {
        if (memberRepository.existsByPhoneNumber(memberDto.getPhoneNumber())) {
            log.info("이미 가입된 유저");
            throw new CustomException(400,"이미 가입한 유저입니다.");
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
                .orElseThrow(()->new CustomException(400,"멤버를 찾을 수 없습니다"));
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

    @Transactional
    public void deleteUserByPhoneNumber(String phoneNumber) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(400,"멤버를 찾을 수 없습니다"));

        List<TransactionEntity> transactions = transactionRepository.findAllBySellerOrBuyer(member, member);
        for (TransactionEntity transaction : transactions) {
            transactionRepository.delete(transaction);
        }

        List<ProductEntity> products = productRepository.findBySeller(member);
        for (ProductEntity product : products) {
            List<BidEntity> bids = product.getBids();
            for (BidEntity bid : bids) {
                bidRepository.delete(bid);
            }
            productRepository.delete(product);
        }

        memberRepository.delete(member);
    }


    @Transactional
    public MemberDto updateUserInfoWithPhoneNumber(String phoneNumber, MemberDto memberDto) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(400,"멤버를 찾을 수 없습니다"));

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
