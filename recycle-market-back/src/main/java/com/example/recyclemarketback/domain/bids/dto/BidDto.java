package com.example.recyclemarketback.domain.bids.dto;

import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidDto {
    private Long id;
    private Long memberId;
    private Long productId;
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
        this.memberId = bidEntity.getBidder().getId();
        this.productId = bidEntity.getProduct().getId();
    }
}
