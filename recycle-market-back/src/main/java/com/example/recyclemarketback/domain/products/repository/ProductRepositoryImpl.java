package com.example.recyclemarketback.domain.products.repository;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.QProductEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.recyclemarketback.domain.products.entity.QProductEntity.productEntity;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<ProductEntity> findDynamicQueryAdvance(String category, String name) {
        return queryFactory
                .selectFrom(productEntity)
                .where(eqName(name), eqCategory(category))
                .fetch();
    }

    private BooleanExpression eqName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return productEntity.name.contains(name);
    }

    private BooleanExpression eqCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            return null;
        }
        return productEntity.category.eq(category);
    }
}
