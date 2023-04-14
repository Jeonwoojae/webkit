package com.example.recyclemarketback.domain.products.dto;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import lombok.*;

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
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    private Long startPrice;
    private Long currentPrice;
    private String sellerPhoneNumber;
    private ProductState productState;

    @Builder
    public ProductDto(@NotNull String name, @NotNull String description, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, @NotNull Long startPrice) {
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
        this.productState = entity.getProductState();
    }
}