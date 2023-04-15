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

    private Long currentPrice;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity seller;

    private ProductState productState;

    @Builder
    public ProductEntity(@NonNull String name, @NonNull String description, @NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate, @NonNull Long startPrice, @NonNull MemberEntity seller) {
        this.name = name;
        this.description = description;
        // 물품이 만들어지면 바로 시작시간
        this.startDate = LocalDateTime.now();
        this.endDate = endDate;
        this.startPrice = startPrice;
        this.currentPrice = startPrice;
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

    public void checkEndDateAndChangeProductState() {
        if (this.productState != ProductState.AUCTION) {
            // 이미 상태가 변경된 경우 return
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(this.endDate)) {
            // 경매 종료 시간이 지났으면 SOLD 상태로 변경
            this.productState = ProductState.ENDED;
        }
    }
}