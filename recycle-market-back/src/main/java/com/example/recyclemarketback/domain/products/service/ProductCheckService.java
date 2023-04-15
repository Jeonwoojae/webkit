package com.example.recyclemarketback.domain.products.service;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.global.exception.CustomException;
import com.example.recyclemarketback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCheckService {
    private final ProductRepository productRepository;

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
        }
    }
}
