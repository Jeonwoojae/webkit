package com.example.recyclemarketback.domain.products.api;

import com.example.recyclemarketback.domain.member.dto.MemberDto;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.service.ProductService;
import com.example.recyclemarketback.global.TokenProvider;
import com.example.recyclemarketback.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // tokenprovider를 계속 의존성 주입보다는 token 클래스를 생성하는게 더 괜찮아 보임
    private final TokenProvider tokenProvider;

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestHeader(value = "Authorization") String atk,
                                        @RequestBody ProductDto productDto) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            ProductDto product = productService.createProduct(phoneNumber, productDto);

            return ResponseEntity.ok().body(product);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProductInfo(@RequestHeader(value = "Authorization") String atk,
                                        @PathVariable("id")Long productId,
                                        @RequestBody ProductDto productDto) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        productDto.setId(productId);
        try{
            ProductDto product = productService.updateProduct(phoneNumber, productDto);

            return ResponseEntity.ok().body(product);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancleProduct(@RequestHeader(value = "Authorization") String atk,
                                               @PathVariable("id")String productId) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            productService.deleteProduct(phoneNumber, productId);

            return ResponseEntity.ok().body("");

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    /**
     * 판매자가 내 판매 목록에 갔을 때 표시할 판매 목록
     * @param atk
     * @param
     * @return
     */
    @GetMapping("/my")
    public ResponseEntity<?> getAllMyProducts(@RequestHeader(value = "Authorization") String atk) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            List<ProductDto> product = productService.getAllUsersProducts(phoneNumber);

            return ResponseEntity.ok().body(product);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    /**
     * 모든 멤버가 메인 화면에서 품목 리스트들을 보여줄 때 확인할 품목
     * @param
     * @return
     */
    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        try{
            List<ProductDto> product = productService.getAllProducts();

            return ResponseEntity.ok().body(product);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}