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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private MemberEntity buyer;

    @NonNull
    @Column(nullable = false)
    private Long bidPrice;

    @Builder
    public BidEntity(@NonNull ProductEntity product, @NonNull MemberEntity buyer, @NonNull Long bidPrice) {
        this.product = product;
        this.buyer = buyer;
        this.bidPrice = bidPrice;
    }

    public void updatePrice(Long bidPrice){
        this.bidPrice = bidPrice;
    }
    public MemberEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(MemberEntity bidder) {
        this.buyer = bidder;
    }
}
