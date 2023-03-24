package kr.ac.kumoh.webkit.library.domain.book.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddBook {
    @NotNull(message = "제목을 입력해주세요.")
    private String title;
    @NotNull(message = "카테고리를 입력해주세요.")
    private String category;
    @NotNull(message = "국가를 선택해주세요")
    private String nation;
    @NotNull(message = "장르를 선택해주세요.")
    private String genre;
    private int price;
}
