package com.example.recyclemarketback.domain.transaction.entity;

import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EntityListeners(TransactionEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionState transactionState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private MemberEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private MemberEntity buyer;

    @Column(length = 50)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private PaymentMethod paymentMethod;

    @Builder
    public TransactionEntity(ProductEntity product, MemberEntity seller,
                             MemberEntity buyer) {
        this.product = product;
        this.transactionState = TransactionState.PAYMENT_WAITING;
        this.seller = seller;
        this.buyer = buyer;
    }

    public void setTransactionState(TransactionState transactionState) {
        this.transactionState = transactionState;
    }

    public void setTrackingNumber(String trackingNumber){
        this.trackingNumber = trackingNumber;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    public void pay(){
        this.paymentMethod.handlePayment(this.paymentMethod);
    }

    public void setSeller(MemberEntity o) {
        this.seller = o;
    }

    public void setBuyer(MemberEntity o) {
        this.buyer = o;
    }

    public void setProduct(ProductEntity o) {
        this.product = o;
    }
}
