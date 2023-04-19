package com.example.recyclemarketback.domain.products.api;

import com.example.recyclemarketback.domain.member.dto.MemberDto;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.domain.products.service.ProductService;
import com.example.recyclemarketback.global.TokenProvider;
import com.example.recyclemarketback.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // tokenprovider를 계속 의존성 주입보다는 token 클래스를 생성하는게 더 괜찮아 보임
    private final TokenProvider tokenProvider;

    @PostMapping(value="")
    public ResponseEntity<?> addProduct(
                                        @RequestHeader(value = "Authorization") String atk,
                                        @ModelAttribute ProductDto productDto,
                                        @RequestPart("image")MultipartFile image
                                        ) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            // 이미지 업로드 후 생성시 이미지 주소 추가
            ProductDto product = productService.createProduct(phoneNumber, productDto, image);

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
            Map<String,String> res = new HashMap<>();
            res.put("status","ok");

            return ResponseEntity.ok().body(res);

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        try{
            ProductDto product = productService.getProduct(id);

            return ResponseEntity.ok().body(product);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
