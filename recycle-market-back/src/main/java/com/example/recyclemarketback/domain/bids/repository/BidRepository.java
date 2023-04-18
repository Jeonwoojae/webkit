package com.example.recyclemarketback.domain.bids.repository;

import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<BidEntity, Long> {
    Optional<BidEntity> findByProductAndBidder(ProductEntity product, MemberEntity member);
    Optional<BidEntity> findTopByProductOrderByBidPriceDesc(ProductEntity product);
    boolean existsByProduct(ProductEntity product);
    List<BidEntity> findAllByBidder(MemberEntity member);
}
