package com.example.recyclemarketback.domain.products.service;

import com.example.recyclemarketback.domain.member.dto.MemberDto;
import com.example.recyclemarketback.domain.member.entity.MemberEntity;
import com.example.recyclemarketback.domain.member.entity.MemberType;
import com.example.recyclemarketback.domain.member.repository.MemberRepository;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.repository.ProductRepository;
import com.example.recyclemarketback.global.S3Uploader;
import com.example.recyclemarketback.global.exception.CustomAccessDeniedException;
import com.example.recyclemarketback.global.exception.CustomException;
import com.example.recyclemarketback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
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
    private final S3Uploader s3Uploader;

    @Transactional
    public ProductDto createProduct(String phoneNumber, ProductDto productDto, MultipartFile image) throws IOException {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(400,"멤버를 찾을 수 없습니다"));

        if (seller.getMemberType() != MemberType.SELLER) throw new CustomAccessDeniedException(403,"권한이 없습니다.");

        ProductEntity productEntity = ProductEntity.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .seller(seller)
                .endDate(productDto.getEndDate())
                .startPrice(productDto.getStartPrice())
                .category(productDto.getCategory())
                .build();

        if(!image.isEmpty()) {
            productEntity.setImageUrl(s3Uploader.upload(image,"images"));
        }

        ProductEntity newEntity = productRepository.save(productEntity);

        ProductDto response = Optional.ofNullable(newEntity)
                .map(ProductDto::new)
                .orElseThrow(() -> new RuntimeException("Entity to DTO failed."));

        return response;
    }

    @Transactional
    public ProductDto updateProduct(String phoneNumber, ProductDto productDto) {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(400,"멤버를 찾을 수 없습니다"));
        ProductEntity product = productRepository.findById(productDto.getId())
                .orElseThrow(()-> new CustomException(400,"물품을 찾을 수 없습니다"));

        if (product.getSeller() != seller){
            // 해당 판매자의 물품이 아닌 경우
            throw new CustomAccessDeniedException(403,"권한이 없습니다.");
        }

        ProductEntity updatedProduct = Optional.ofNullable(product)
                        .map(p -> {
                            p.setName(productDto.getName());
                            p.setDescription(productDto.getDescription());
                            p.setEndDate(productDto.getEndDate());
                            p.setCategory(productDto.getCategory());
                            return productRepository.save(p);
                        }).orElseThrow(() -> new CustomException(400,"물품을 찾을 수 없습니다"));

        ProductDto response = Optional.ofNullable(updatedProduct)
                .map(ProductDto::new).orElseThrow(()->new CustomException(400,"dto 변환 실패"));

        return response;
    }


    @Transactional
    public void deleteProduct(String phoneNumber, String productId) {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(400,"멤버를 찾을 수 없습니다"));
        ProductEntity product = productRepository.findById(Long.valueOf(productId))
                .orElseThrow(()-> new CustomException(400,"물품을 찾을 수 없습니다"));

        if (product.getSeller() != seller){
            // 해당 판매자의 물품이 아닌 경우
            throw new CustomAccessDeniedException(403,"권한이 없습니다.");
        }

        productRepository.delete(product);
    }

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAllByOrderByIdDesc();
        List<ProductDto> dtos = entities.stream().map(ProductDto::new).collect(Collectors.toList());

        return dtos;
    }

    public List<ProductDto> getAllUsersProducts(String phoneNumber) {
        MemberEntity seller = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new CustomException(400,"물품을 찾을 수 없습니다"));

        List<ProductDto> dtos = seller.getProducts().stream().map(ProductDto::new).collect(Collectors.toList());

        return dtos;
    }

    public ProductDto getProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(()-> new CustomException(400,"물품을 찾을 수 없습니다"));

        ProductDto dto = Optional.ofNullable(product).map(ProductDto::new).orElse(null);

        return dto;
    }
}
