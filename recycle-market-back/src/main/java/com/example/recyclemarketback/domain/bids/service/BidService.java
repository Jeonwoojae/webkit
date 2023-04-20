package com.example.recyclemarketback.domain.bids.service;

import com.example.recyclemarketback.domain.bids.dto.BidDto;
import com.example.recyclemarketback.domain.bids.entity.BidEntity;
import com.example.recyclemarketback.domain.bids.repository.BidRepository;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.member.entity.MemberType;
import com.example.recyclemarketback.domain.member.repository.MemberRepository;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.global.exception.CustomAccessDeniedException;
import com.example.recyclemarketback.global.exception.CustomException;
import com.example.recyclemarketback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
                .orElseThrow(() -> new CustomException(400,"멤버를 찾을 수 없습니다"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(400, "물품을 찾을 수 없습니다."));

        if (member.getMemberType() != MemberType.BUYER) throw new CustomAccessDeniedException(403,"권한이 없습니다.");

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
        product.addBid(newBid);
        productRepository.save(product);
        
        BidDto response = Optional.ofNullable(newBid).map(BidDto::new)
                .orElse(null);
        return response;
    }

    @Transactional
    // 입찰 내용 수정
    public BidDto updateBid(String phoneNumber, Long productId, Long price) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(400,"멤버를 찾을 수 없습니다"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(400,"물품을 찾을 수 없습니다"));


        if (product.getProductState() != ProductState.AUCTION) {
            throw new IllegalStateException("경매가 종료된 상품입니다.");
        }

        if (product.getStartPrice() >= price || product.getCurrentPrice() >= price) {
            throw new IllegalArgumentException("현재 입찰가보다 높은 가격을 입력해주세요.");
        }

        BidEntity bid = bidRepository.findByProductAndBidder(product, member)
                .orElseThrow(() -> new CustomException(400,"입찰 정보를 찾을 수 없습니다"));

        bid.updatePrice(price);
        BidDto response = Optional.ofNullable(bid).map(BidDto::new)
                .orElse(null);
        return response;
    }

    // 입찰 삭제
    @Transactional
    public void deleteBid(String phoneNumber, Long productId) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(400,"멤버를 찾을 수 없습니다"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(400,"물품을 찾을 수 없습니다"));

        if (product.getProductState() != ProductState.AUCTION) {
            throw new IllegalStateException("경매가 종료된 상품입니다.");
        }


        BidEntity bidEntity = bidRepository.findByProductAndBidder(product, member)
                .orElseThrow(() -> new CustomException(400,"입찰정보를 찾을 수 없습니다"));



        bidRepository.delete(bidEntity);
        product.removeBid(bidEntity);

        // 상품도 최고 입찰가로 수정
        product.updateCurrentPrice();
        productRepository.save(product);
    }

    public BidDto getMembersBid(String phoneNumber, Long productId) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(400,"멤버를 찾을 수 없습니다"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(400,"물품을 찾을 수 없습니다"));

        BidEntity bid = bidRepository.findByProductAndBidder(product, member)
                .orElseThrow(() -> new CustomException(400,"입찰 정보를 찾을 수 없습니다"));

        BidDto response = Optional.ofNullable(bid).map(BidDto::new)
                .orElse(null);
        return response;
    }

    public List<BidDto> getMembersBids(String phoneNumber) {
        MemberEntity member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(400,"멤버를 찾을 수 없습니다"));
        List<BidEntity> list = bidRepository.findAllByBidder(member);

        List<BidDto> response = list.stream().map(BidDto::new).collect(Collectors.toList());

        return response;
    }
}
