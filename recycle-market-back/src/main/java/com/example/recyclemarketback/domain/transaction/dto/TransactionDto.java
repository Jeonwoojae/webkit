package com.example.recyclemarketback.domain.transaction.dto;

import com.example.recyclemarketback.domain.transaction.entity.PaymentMethod;
import com.example.recyclemarketback.domain.transaction.entity.TransactionEntity;
import com.example.recyclemarketback.domain.transaction.entity.TransactionState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    private Long productId;
    private String productName;
    private Long sellerId;
    private String sellerName;
    private Long buyerId;
    private String buyerName;
    private Long price;
    private String transactionState;
    private PaymentMethod paymentMethod;
    private String trackingNumber;

    public TransactionDto(TransactionEntity transactionEntity) {
        this.id = transactionEntity.getId();
        this.productId = transactionEntity.getProduct().getId();
        this.productName = transactionEntity.getProduct().getName();
        this.sellerId = transactionEntity.getSeller().getId();
        this.sellerName = transactionEntity.getSeller().getName();
        this.buyerId = transactionEntity.getBuyer().getId();
        this.buyerName = transactionEntity.getBuyer().getName();
        this.price = transactionEntity.getProduct().getCurrentPrice();
        this.transactionState = transactionEntity.getTransactionState().getState();
        this.paymentMethod = transactionEntity.getPaymentMethod();
        this.trackingNumber = transactionEntity.getTrackingNumber();
    }
}
