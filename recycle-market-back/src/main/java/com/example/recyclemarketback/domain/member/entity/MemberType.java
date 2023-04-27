package com.example.recyclemarketback.domain.member.entity;

public enum MemberType {
    BUYER("구매자"),
    SELLER("판매자");

    private String role;
    MemberType(String role){ this.role = role; }
    public String getRole() { return role; }
}
