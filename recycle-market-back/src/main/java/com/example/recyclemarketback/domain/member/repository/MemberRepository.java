package com.example.recyclemarketback.domain.member.repository;

import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<MemberEntity> findByPhoneNumber(String phoneNumber);
}
