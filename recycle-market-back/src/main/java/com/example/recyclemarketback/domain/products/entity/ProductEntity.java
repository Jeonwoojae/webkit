package com.example.recyclemarketback.domain.products.entity;

import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime startDate;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime endDate;

    @NonNull
    @Column(nullable = false)
    private Long startPrice;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity seller;

    private ProductState productState;

    @Builder
    public ProductEntity(@NonNull String name, @NonNull String description, @NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate, @NonNull Long startPrice, @NonNull MemberEntity seller) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPrice = startPrice;
        this.seller = seller;
        // 물품이 만들어 지면 바로 경매 진행 상태
        this.productState = ProductState.AUCTION;
    }

    public ProductEntity update(@NonNull String name, @NonNull String description, @NonNull LocalDateTime endDate){
        this.name = name;
        this.description = description;
        this.endDate = endDate;

        return this;
    }

    public ProductDto entityToDto(){
        return ProductDto.builder()
                .name(this.getName())
                .description(this.getDescription())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .startPrice(this.startPrice)
                .build();
    }
}