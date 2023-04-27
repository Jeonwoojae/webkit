package com.example.recyclemarketback.domain.member.entity;

import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.transaction.entity.TransactionEntity;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transaction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NonNull
    @Column(nullable = false, length = 20)
    private String name;

    @NonNull
    @Column(nullable = false, length = 20, unique = true)
    private String phoneNumber;

    @NonNull
    @Column(nullable = false)
    private String encryptedPwd;

    @NonNull
    @Column(nullable = false,length = 100)
    private String address_info;

    @NonNull
    @Column(nullable = false, length = 100)
    private String address_details;

    @NonNull
    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장
    @Column(nullable = false)
    private MemberType memberType;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    private List<BidEntity> bids = new ArrayList<>();


    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private Set<ProductEntity> products = new HashSet<>();

    @OneToMany(mappedBy = "buyer")
    private Set<TransactionEntity> buyingTransactions = new HashSet<>();

    @Builder
    public MemberEntity(@NonNull String name, @NonNull String phoneNumber, @NonNull String encryptedPwd, @NonNull String address_info, @NonNull String address_details, @NonNull MemberType memberType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.encryptedPwd = encryptedPwd;
        this.address_info = address_info;
        this.address_details = address_details;
        this.memberType = memberType;
    }

    public MemberEntity update(@NonNull String name, @NonNull String address_info, @NonNull String address_details){
        this.name = name;
        this.address_info = address_info;
        this.address_details = address_details;

        return this;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    public void addTransaction(TransactionEntity transaction){
        this.buyingTransactions.add(transaction);
    }

    public void addBid(BidEntity bid){
        this.bids.add(bid);
    }
}
