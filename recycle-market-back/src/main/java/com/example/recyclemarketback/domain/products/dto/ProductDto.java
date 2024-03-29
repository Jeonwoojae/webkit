package com.example.recyclemarketback.domain.products.dto;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private String imageUrl;
    @NotNull
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    @NotNull
    private Long startPrice;
    private Long currentPrice;
    private String sellerPhoneNumber;
    private String sellerName;
    private ProductState productState;

    @Builder
    public ProductDto(String name, String description, LocalDateTime startDate, LocalDateTime endDate, Long startPrice) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPrice = startPrice;
    }

    public ProductDto(final ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.startPrice = entity.getStartPrice();
        this.currentPrice = entity.getCurrentPrice();
        this.productState = entity.getProductState();
        this.sellerPhoneNumber = entity.getSeller().getPhoneNumber();
        this.sellerName = entity.getSeller().getName();
        this.category = entity.getCategory();
        this.imageUrl = entity.getImageUrl();
    }
}