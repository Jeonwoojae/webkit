package com.example.recyclemarketback.domain.products.api;

import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.service.ProductSearchService;
import com.example.recyclemarketback.domain.products.service.ProductService;
import com.example.recyclemarketback.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products/search")
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductSearchService productSearchService;
    private final ProductService productService;
    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false)String category,
                                            @RequestParam(required = false)String name) {
        try{
            List<ProductDto> product;
            if(category == null && name == null){
                product = productService.getAllProducts();
            }else product = productSearchService.findProductsDynamicQuery(category, name);


            return ResponseEntity.ok().body(product);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
