package kr.ac.kumoh.webkit.library.domain.book.repository;

import kr.ac.kumoh.webkit.library.domain.book.entity.Book;

import java.util.List;

public interface BookRepositoryCustom {
    List<Book> findDynamicQueryAdvance(String nation, String genre, String title, String category, Integer price);
}
