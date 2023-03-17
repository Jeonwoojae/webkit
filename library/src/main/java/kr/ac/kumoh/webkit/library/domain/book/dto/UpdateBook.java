package kr.ac.kumoh.webkit.library.domain.book.dto;

import lombok.Data;

@Data
public class UpdateBook {
    private String title;
    private String category;
    private String nation;
    private String genre;
    private int price;
}
