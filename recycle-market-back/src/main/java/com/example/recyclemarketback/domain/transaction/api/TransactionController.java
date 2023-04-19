package com.example.recyclemarketback.domain.transaction.api;

import com.example.recyclemarketback.domain.transaction.dto.TransactionDto;
import com.example.recyclemarketback.domain.transaction.entity.PaymentMethod;
import com.example.recyclemarketback.domain.transaction.service.TransactionService;
import com.example.recyclemarketback.global.TokenProvider;
import com.example.recyclemarketback.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TokenProvider tokenProvider;

    @GetMapping("/users")
    public ResponseEntity<?> getAllTransactionsByUser(@RequestHeader(value = "Authorization") String atk) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            List<TransactionDto> transactionList = transactionService.getAllUsersTransactions(phoneNumber);

            return ResponseEntity.ok().body(transactionList);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PatchMapping("/{transactionId}/pay")
    public ResponseEntity<?> payTransaction(@RequestHeader(value = "Authorization") String atk,
                                            @PathVariable Long transactionId,
                                            @RequestParam PaymentMethod method) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            TransactionDto transaction = transactionService.payTransaction(transactionId, phoneNumber, method);

            return ResponseEntity.ok().body(transaction);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PatchMapping("/{transactionId}/ship")
    public ResponseEntity<?> shipProduct(@RequestHeader(value = "Authorization") String atk,
                                            @PathVariable Long transactionId,
                                            @RequestBody String trackingNumber) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            TransactionDto transaction = transactionService.shipProduct(transactionId, phoneNumber, trackingNumber);

            return ResponseEntity.ok().body(transaction);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PatchMapping("/{transactionId}/complete")
    public ResponseEntity<?> deliveryComplete(@RequestHeader(value = "Authorization") String atk,
                                         @PathVariable Long transactionId) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            TransactionDto transaction = transactionService.deliveryComplete(transactionId, phoneNumber);

            return ResponseEntity.ok().body(transaction);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PatchMapping("/{transactionId}/cancle")
    public ResponseEntity<?> cancleTransaction(@RequestHeader(value = "Authorization") String atk,
                                              @PathVariable Long transactionId) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            TransactionDto transaction = transactionService.cancleTransaction(transactionId, phoneNumber);

            return ResponseEntity.ok().body(transaction);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionDetail(@RequestHeader(value = "Authorization") String atk,
                                               @PathVariable Long transactionId) {
        String phoneNumber = tokenProvider.getPhoneNumberFromAccessToken(atk);
        try{
            TransactionDto transaction = transactionService.getTransactionDetail(transactionId, phoneNumber);

            return ResponseEntity.ok().body(transaction);

        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
