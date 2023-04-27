package com.example.recyclemarketback.domain.products.service;

import com.example.recyclemarketback.domain.bids.repository.BidRepository;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCheckService {
    private final ProductRepository productRepository;
    private final BidRepository bidRepository;
    private final TransactionService transactionService;

    @Transactional
    @Scheduled(fixedDelay = 60000) // 1분마다 실행
    public void checkProductState() {
        LocalDateTime now = LocalDateTime.now();
        // 종료시간이 아직 남았으며 경매중인 상품 가져오기
        List<ProductEntity> productList = productRepository.findAllByEndDateBeforeAndProductState(now, ProductState.AUCTION);

        for (ProductEntity product : productList) {
            product.checkEndDateAndChangeProductState();
            // 해당 물품의 상태를 변경하고 저장
            productRepository.save(product);

            boolean hasBidder = bidRepository.existsByProduct(product);
            // 입찰자가 있을 경우 Transaction 엔티티 생성
            // 주의!! 바로 레포지토리의 코드를 넣으면 false여도 if 안에 들어간다
            if (hasBidder) {
                transactionService
                        .initTransaction(Optional.ofNullable(product).map(ProductDto::new).orElse(null));
            }
        }
    }
}
