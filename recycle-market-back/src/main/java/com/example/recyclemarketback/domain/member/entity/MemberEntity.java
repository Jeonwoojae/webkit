package com.example.recyclemarketback.domain.member.entity;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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
    @Column(nullable = false, length = 50)
    private String name;

    @NonNull
    @Column(nullable = false, length = 50, unique = true)
    private String phoneNumber;

    @NonNull
    @Column(nullable = false)
    private String encryptedPwd;

    @NonNull
    @Column(nullable = false)
    private String address_info;

    @NonNull
    @Column(nullable = false)
    private String address_details;

    @NonNull
    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장
    @Column(nullable = false)
    private MemberType memberType;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductEntity> products = new HashSet<>();

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
}
