package com.example.recyclemarketback.domain.products.repository;

import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByEndDateBeforeAndProductState(LocalDateTime now, ProductState auction);
    List<ProductEntity> findProductEntitiesByCategory(String category);
    List<ProductEntity> findProductEntitiesByName(String name);
    List<ProductEntity> findAllByOrderByIdDesc();

    List<ProductEntity> findBySeller(MemberEntity member);
}
