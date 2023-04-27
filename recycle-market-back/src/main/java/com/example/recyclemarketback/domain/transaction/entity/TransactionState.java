package com.example.recyclemarketback.domain.transaction.entity;

public enum TransactionState {
    PAYMENT_WAITING("결제 대기") {
        @Override
        public void paymentConfirmed(TransactionEntity transactionEntity) {
            transactionEntity.setTransactionState(TransactionState.PAYMENT_COMPLETED);
        }

        @Override
        public void ship(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("결제가 완료되지 않았으므로 상품을 발송할 수 없습니다.");
        }

        @Override
        public void deliveryComplete(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("상품을 발송하지 않았으므로 수령확인할 수 없습니다.");
        }

        @Override
        public void cancelTransaction(TransactionEntity transactionEntity) {
            transactionEntity.setTransactionState(TransactionState.TRANSACTION_CANCELLED);
        }
    },
    PAYMENT_COMPLETED("결제 완료") {
        @Override
        public void paymentConfirmed(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 결제가 완료되었습니다.");
        }

        @Override
        public void ship(TransactionEntity transactionEntity) {
            transactionEntity.setTransactionState(TransactionState.SHIPPING);
        }

        @Override
        public void deliveryComplete(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("상품을 발송하지 않았으므로 수령확인할 수 없습니다.");
        }

        @Override
        public void cancelTransaction(TransactionEntity transactionEntity) {
            transactionEntity.setTransactionState(TransactionState.TRANSACTION_CANCELLED);
        }
    },
    SHIPPING("배송 중") {
        @Override
        public void paymentConfirmed(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 결제가 완료되었습니다.");
        }

        @Override
        public void ship(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 상품이 발송되었습니다.");
        }

        @Override
        public void deliveryComplete(TransactionEntity transactionEntity) {
            transactionEntity.setTransactionState(TransactionState.TRANSACTION_COMPLETED);
        }

        @Override
        public void cancelTransaction(TransactionEntity transactionEntity) {
            throw new IllegalStateException("이미 상품이 발송되어 취소할 수 없습니다.");
        }
    },
    TRANSACTION_COMPLETED("거래 완료") {
        @Override
        public void paymentConfirmed(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 거래가 완료되었습니다.");
        }

        @Override
        public void ship(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 거래가 완료되었습니다.");
        }

        @Override
        public void deliveryComplete(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 거래가 완료되었습니다.");
        }

        @Override
        public void cancelTransaction(TransactionEntity transactionEntity) {
            throw new IllegalStateException("이미 거래가 완료되어 취소할 수 없습니다.");
        }
    },
    TRANSACTION_CANCELLED("거래 취소") {
        @Override
        public void paymentConfirmed(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 거래가 취소되어 결제할 수 없습니다.");
        }

        @Override
        public void ship(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 거래가 취소되어 발송할 수 없습니다.");
        }

        @Override
        public void deliveryComplete(TransactionEntity transactionEntity) {
            throw new UnsupportedOperationException("이미 거래가 취소되어 취소할 수 없습니다.");
        }

        @Override
        public void cancelTransaction(TransactionEntity transactionEntity) {
            throw new IllegalStateException("이미 거래가 취소되어 취소할 수 없습니다.");
        }
    };

    public abstract void paymentConfirmed(TransactionEntity transactionEntity);

    public abstract void ship(TransactionEntity transactionEntity);

    public abstract void deliveryComplete(TransactionEntity transactionEntity);

    public abstract void cancelTransaction(TransactionEntity transactionEntity);

    private String state;
    TransactionState(String state){ this.state = state; }
    public String getState() { return state; }
}


