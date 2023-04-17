package com.example.recyclemarketback.domain.bids.service;

import com.example.recyclemarketback.domain.bids.dto.BidDto;
import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.bids.repository.BidRepository;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.member.repository.MemberRepository;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.global.exception.CustomException;
import com.example.recyclemarketback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final BidRepository bidRepository;

    // TODO 각 서비스에서 멤버가 구매자 역할인지 확인해야할까
    // 입찰 등록
    @Transactional
    public BidDto addBid(String phoneNumber, Long productId, Long price) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_PRODUCT));

        if (product.getProductState() != ProductState.AUCTION) {
            throw new IllegalStateException("경매가 종료된 상품입니다.");
        }

        Optional<BidEntity> existingBid = bidRepository.findByProductAndBidder(product, member);
        if (existingBid.isPresent()) {
            throw new IllegalStateException("이미 해당 상품에 대해 입찰한 내역이 있습니다.");
        }

        if (product.getStartPrice() >= price || product.getCurrentPrice() >= price) {
            throw new IllegalArgumentException("현재 입찰가보다 높은 가격을 입력해주세요.");
        }

        BidEntity bid = BidEntity.builder()
                .bidPrice(price)
                .product(product)
                .bidder(member)
                .build();

        BidEntity newBid = bidRepository.save(bid);
        product.setCurrentPrice(newBid.getBidPrice());
        productRepository.save(product);
        
        BidDto response = Optional.ofNullable(newBid).map(BidDto::new)
                .orElse(null);
        return response;
    }

    @Transactional
    // 입찰 내용 수정
    public BidDto updateBid(String phoneNumber, Long productId, Long price) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_PRODUCT));

        if (product.getProductState() != ProductState.AUCTION) {
            throw new IllegalStateException("경매가 종료된 상품입니다.");
        }

        if (product.getStartPrice() >= price || product.getCurrentPrice() >= price) {
            throw new IllegalArgumentException("현재 입찰가보다 높은 가격을 입력해주세요.");
        }

        BidEntity bid = bidRepository.findByProductAndBidder(product, member)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_BID));

        bid.updatePrice(price);
        BidDto response = Optional.ofNullable(bid).map(BidDto::new)
                .orElse(null);
        return response;
    }

    // 입찰 삭제
    @Transactional
    public void deleteBid(String phoneNumber, Long productId) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_PRODUCT));

        if (product.getProductState() != ProductState.AUCTION) {
            throw new IllegalStateException("경매가 종료된 상품입니다.");
        }


        BidEntity bid = bidRepository.findByProductAndBidder(product, member)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_BID));

        bidRepository.delete(bid);
    }

    public BidDto getMembersBid(String phoneNumber, Long productId) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_PRODUCT));

        BidEntity bid = bidRepository.findByProductAndBidder(product, member)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_BID));

        BidDto response = Optional.ofNullable(bid).map(BidDto::new)
                .orElse(null);
        return response;
    }
}
