package com.example.recyclemarketback.domain.products.entity;

public enum ProductState {
    AUCTION("경매 중"),
    ENDED("경매 종료");

    private String state;
    ProductState(String state){ this.state = state; }
    public String getState() { return state; }
}
