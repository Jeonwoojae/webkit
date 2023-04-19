package com.example.recyclemarketback.domain.products.entity;

import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @NonNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @NonNull
    @Column(nullable = false)
    private String category;

    @NonNull
    @Column(nullable = false)
    private Long startPrice;

    private Long currentPrice;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity seller;

    private ProductState productState;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BidEntity> bidList = new ArrayList<>();

    @Column
    private String imageUrl;

    @Builder
    public ProductEntity(@NonNull String name, @NonNull String description, @NonNull LocalDateTime endDate, @NonNull Long startPrice, @NonNull MemberEntity seller, @NonNull String category) {
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
        this.category = category;
    }

    public ProductEntity update(@NonNull String name, @NonNull String description, @NonNull LocalDateTime endDate, @NonNull String category){
        this.name = name;
        this.description = description;
        this.endDate = endDate;
        this.category = category;

        return this;
    }

    public void setCurrentPrice(Long price){
        this.currentPrice = price;
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

    public void addBid(BidEntity bid) {
        bidList.add(bid);
        bid.setProduct(this);
    }

    public void removeBid(BidEntity bid) {
        bidList.remove(bid);
    }

    public void updateCurrentPrice() {
        BidEntity highestBid = getHighestBid();
        if (highestBid != null) {
            this.currentPrice = highestBid.getBidPrice();
        } else {
            this.currentPrice = startPrice;
        }
    }

    private BidEntity getHighestBid() {
        List<BidEntity> bids = getBids();
        if (bids.isEmpty()) {
            return null;
        }
        // 최고 입찰가를 가진 Bid 엔티티를 찾습니다.
        return bids.stream().max(Comparator.comparing(BidEntity::getBidPrice)).orElse(null);
    }

    private List<BidEntity> getBids() {
        return bidList.stream()
                .filter(bid -> bid.getBidPrice() != null)
                .collect(Collectors.toList());
    }
}