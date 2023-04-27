package com.example.recyclemarketback.domain.products.service;

import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.repository.ProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepositoryImpl productRepository;

    public List<ProductDto> findProductsDynamicQuery(String category, String name){
        List<ProductEntity> entities = productRepository.findDynamicQueryAdvance(category, name);
        List<ProductDto> dtos = entities.stream().map(ProductDto::new).collect(Collectors.toList());

        return dtos;
    }
}
