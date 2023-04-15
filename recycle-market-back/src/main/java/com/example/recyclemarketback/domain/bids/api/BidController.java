package com.example.recyclemarketback.domain.bids.api;

import com.example.recyclemarketback.domain.bids.dto.BidDto;
import com.example.recyclemarketback.domain.bids.service.BidService;
import com.example.recyclemarketback.domain.products.dto.ProductDto;
import com.example.recyclemarketback.global.TokenProvider;
import com.example.recyclemarketback.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;
    private final TokenProvider tokenProvider;

    @PostMapping("/{productId}")
    public ResponseEntity<?> createBid(@RequestHeader(value = "Authorization") String atk,
                                       @PathVariable Long productId,
                                       @RequestParam Long price) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            BidDto bid = bidService.addBid(phoneNumber, productId, price);

            return ResponseEntity.ok().body(bid);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateBid(@RequestHeader(value = "Authorization") String atk,
                                       @PathVariable Long productId,
                                       @RequestParam Long price) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            BidDto bid = bidService.updateBid(phoneNumber, productId, price);

            return ResponseEntity.ok().body(bid);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getMyBid(@RequestHeader(value = "Authorization") String atk,
                                       @PathVariable Long productId) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try {
            BidDto bid = bidService.getMembersBid(phoneNumber, productId);

            return ResponseEntity.ok().body(bid);

        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteBid(@RequestHeader(value = "Authorization") String atk,
                                          @PathVariable Long productId) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try {
            bidService.deleteBid(phoneNumber, productId);

            return ResponseEntity.ok().body("");

        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
