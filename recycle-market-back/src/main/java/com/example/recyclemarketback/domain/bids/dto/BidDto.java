package com.example.recyclemarketback.domain.bids.dto;

import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidDto {
    private Long id;
    private Long memberId;
    private Long productId;
    private String productName;
    private boolean isEnd;
    private Long price;

    @Builder
    public BidDto(Long id, Long memberId, Long productId, Long price) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.price = price;
    }

    public BidDto(BidEntity bidEntity) {
        this.id = bidEntity.getId();
        this.price = bidEntity.getBidPrice();
        this.memberId = bidEntity.getBuyer().getId();
        this.productId = bidEntity.getProduct().getId();
        this.productName = bidEntity.getProduct().getName();
        this.isEnd = bidEntity.getProduct().getProductState() == ProductState.ENDED ? true: false;
    }
}
