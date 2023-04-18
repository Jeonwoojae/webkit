package com.example.recyclemarketback.domain.bids.entity;

import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bids")
public class BidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity bidder;

    @NonNull
    @Column(nullable = false)
    private Long bidPrice;

    private String productName;

    @Builder
    public BidEntity(@NonNull ProductEntity product, @NonNull MemberEntity bidder, @NonNull Long bidPrice) {
        this.product = product;
        this.bidder = bidder;
        this.bidPrice = bidPrice;
        this.productName = product.getName();
    }

    public void updatePrice(Long bidPrice){
        this.bidPrice = bidPrice;
    }
}
