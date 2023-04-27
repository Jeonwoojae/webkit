package com.example.recyclemarketback.domain.transaction.entity;

public enum PaymentMethod {
    CARD,
    BANK_TRANSFER;

    public void handlePayment(PaymentMethod paymentMethod) {
        switch (paymentMethod) {
            case CARD:
                processCardPayment();
                break;
            case BANK_TRANSFER:
                processBankTransfer();
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 결제 방식입니다.");
        }
    }

    private void processCardPayment() {
        // 카드 결제 처리 로직
    }

    private void processBankTransfer() {
        // 통장 입금 처리 로직
    }
}