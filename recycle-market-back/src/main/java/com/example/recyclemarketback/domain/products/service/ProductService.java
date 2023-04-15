package com.example.recyclemarketback.domain.products.service;

import com.example.recyclemarketback.domain.member.dto.MemberDto;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.member.repository.MemberRepository;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.global.exception.CustomException;
import com.example.recyclemarketback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ProductDto createProduct(String phoneNumber, ProductDto productDto) {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));

        // TODO 사용자가 판매자인지 확인 필요
        ProductEntity productEntity = ProductEntity.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .seller(seller)
                .endDate(productDto.getEndDate())
                .startPrice(productDto.getStartPrice())
                .build();

        ProductEntity newEntity = productRepository.save(productEntity);

        ProductDto response = Optional.ofNullable(newEntity)
                .map(ProductDto::new)
                .orElseThrow(() -> new RuntimeException("Entity to DTO failed."));

        return response;
    }

    @Transactional
    public ProductDto updateProduct(String phoneNumber, ProductDto productDto) {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));
        ProductEntity product = productRepository.findById(productDto.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_PRODUCT));

        if (product.getSeller() != seller){
            // 해당 판매자의 물품이 아닌 경우
            throw new CustomException(ErrorCode.BAD_TOKEN);
        }

        ProductEntity updatedProduct = Optional.ofNullable(product)
                        .map(p -> {
                            p.setName(productDto.getName());
                            p.setDescription(productDto.getDescription());
                            p.setEndDate(productDto.getEndDate());
                            return productRepository.save(p);
                        }).orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_PRODUCT));

        ProductDto response = Optional.ofNullable(updatedProduct)
                .map(ProductDto::new).orElseThrow(()->new CustomException(ErrorCode.CANNOT_CONVERT));

        return response;
    }


    @Transactional
    public void deleteProduct(String phoneNumber, String productId) {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));
        ProductEntity product = productRepository.findById(Long.valueOf(productId))
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_PRODUCT));

        if (product.getSeller() != seller){
            // 해당 판매자의 물품이 아닌 경우
            throw new CustomException(ErrorCode.BAD_TOKEN);
        }

        productRepository.delete(product);
    }

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAll();
        List<ProductDto> dtos = entities.stream().map(ProductDto::new).collect(Collectors.toList());

        return dtos;
    }

    public List<ProductDto> getAllUsersProducts(String phoneNumber) {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));

        List<ProductDto> dtos = seller.getProducts().stream().map(ProductDto::new).collect(Collectors.toList());

        return dtos;
    }
}
