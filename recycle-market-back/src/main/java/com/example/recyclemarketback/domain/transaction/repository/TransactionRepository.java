package com.example.recyclemarketback.domain.transaction.repository;

import com.example.recyclemarketback.domain.transaction.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByBuyer_PhoneNumberOrSeller_PhoneNumber(String phoneNumber1, String phoneNumber2);
}
