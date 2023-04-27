package com.example.recyclemarketback.domain.transaction.service;

import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.bids.repository.BidRepository;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.member.repository.MemberRepository;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.domain.transaction.dto.TransactionDto;
import com.example.recyclemarketback.domain.transaction.entity.PaymentMethod;
import com.example.recyclemarketback.domain.transaction.entity.TransactionEntity;
import com.example.recyclemarketback.domain.transaction.repository.TransactionRepository;
import com.example.recyclemarketback.global.exception.CustomAccessDeniedException;
import com.example.recyclemarketback.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final BidRepository bidRepository;

    /**
     * Product의 상태가 경매 종료로 변경될 시 사용되는 메소드. Transaction 엔티티 생성.
     * @param productDto
     * @return
     */
    @Transactional
    public TransactionDto initTransaction(ProductDto productDto){
        ProductEntity product = productRepository.findById(productDto.getId())
                .orElseThrow(()->new CustomException(400,"물품을 찾을 수 없습니다"));
        MemberEntity seller = memberRepository.findByPhoneNumber(productDto.getSellerPhoneNumber())
                .orElseThrow(()->new CustomException(400,"멤버를 찾을 수 없습니다"));
        BidEntity highestBid = bidRepository.findTopByProductOrderByBidPriceDesc(product)
                .orElseThrow(()-> new IllegalArgumentException("입찰자가 없습니다."));
        // 입찰자가 없는 경우 Transaction을 생성하지 않도록 했다.

        MemberEntity buyer = highestBid.getBuyer();

        TransactionEntity transaction = TransactionEntity.builder()
                .product(product)
                .buyer(buyer)
                .seller(seller)
                .build();
        buyer.addTransaction(transaction);
        memberRepository.save(buyer);

        TransactionEntity response = transactionRepository.save(transaction);

        return Optional.ofNullable(response).map(TransactionDto::new).orElse(null);
    }

    public List<TransactionDto> getAllUsersTransactions(String phoneNumber){
        List<TransactionEntity> list = transactionRepository.findAllByBuyer_PhoneNumberOrSeller_PhoneNumber(phoneNumber, phoneNumber);
        List<TransactionDto> response = new ArrayList<>();

        for (TransactionEntity transaction : list){
            response.add(Optional.ofNullable(transaction).map(TransactionDto::new).orElse(null));
        }

        return response;
    }

    @Transactional
    public TransactionDto cancleTransaction(Long transactionId, String phoneNumber) {
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()->new NoSuchElementException("Transaction을 찾을 수 없습니다."));
        if (!transaction.getSeller().getPhoneNumber().equals(phoneNumber) && !transaction.getBuyer().getPhoneNumber().equals(phoneNumber)){
            throw new CustomAccessDeniedException(403, "권한이 없습니다.");
        }

        transaction.getTransactionState().cancelTransaction(transaction);
        TransactionEntity updatedTransaction = transactionRepository.save(transaction);

        TransactionDto response = Optional.ofNullable(updatedTransaction).map(TransactionDto::new).orElse(null);

        return response;
    }

    @Transactional
    public TransactionDto payTransaction(Long transactionId, String phoneNumber, PaymentMethod paymentMethod){
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()->new NoSuchElementException("Transaction을 찾을 수 없습니다."));
        if (!transaction.getBuyer().getPhoneNumber().equals(phoneNumber)){
            throw new CustomAccessDeniedException(403, "권한이 없습니다.");
        }

        // 실제 결제 진행
        transaction.setPaymentMethod(paymentMethod);
        transaction.pay();

        // 거래 상태 변경
        transaction.getTransactionState().paymentConfirmed(transaction);
        TransactionEntity updatedTransaction = transactionRepository.save(transaction);

        TransactionDto response = Optional.ofNullable(updatedTransaction).map(TransactionDto::new).orElse(null);

        return response;
    }

    @Transactional
    public TransactionDto shipProduct(Long transactionId, String phoneNumber, String trackingNumber){
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()->new NoSuchElementException("Transaction을 찾을 수 없습니다."));
        if (!transaction.getSeller().getPhoneNumber().equals(phoneNumber)){
            throw new CustomAccessDeniedException(403, "권한이 없습니다.");
        }

        // 운송장 등록(Valid는 외부 시스템에)
        transaction.setTrackingNumber(trackingNumber);

        // 거래 상태 변경
        transaction.getTransactionState().ship(transaction);
        TransactionEntity updatedTransaction = transactionRepository.save(transaction);

        TransactionDto response = Optional.ofNullable(updatedTransaction).map(TransactionDto::new).orElse(null);

        return response;
    }

    @Transactional
    public TransactionDto deliveryComplete(Long transactionId, String phoneNumber){
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()->new NoSuchElementException("Transaction을 찾을 수 없습니다."));
        if (!transaction.getBuyer().getPhoneNumber().equals(phoneNumber)){
            throw new CustomAccessDeniedException(403, "권한이 없습니다.");
        }

        // 거래 상태 변경
        transaction.getTransactionState().deliveryComplete(transaction);
        TransactionEntity updatedTransaction = transactionRepository.save(transaction);

        TransactionDto response = Optional.ofNullable(updatedTransaction).map(TransactionDto::new).orElse(null);

        return response;
    }

    public TransactionDto getTransactionDetail(Long transactionId, String phoneNumber) {
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()->new NoSuchElementException("Transaction을 찾을 수 없습니다."));
        if (!transaction.getBuyer().getPhoneNumber().equals(phoneNumber) && !transaction.getSeller().getPhoneNumber().equals(phoneNumber)){
            throw new CustomAccessDeniedException(403, "권한이 없습니다.");
        }


        TransactionDto response = Optional.ofNullable(transaction).map(TransactionDto::new).orElse(null);

        return response;
    }
}
