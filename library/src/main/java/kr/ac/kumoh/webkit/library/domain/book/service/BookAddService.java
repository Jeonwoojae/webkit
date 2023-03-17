package kr.ac.kumoh.webkit.library.domain.book.service;

import kr.ac.kumoh.webkit.library.domain.book.dto.AddBook;
import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookAddService {
    private final BookRepository bookRepository;

    public Book addBook(final AddBook dto) {
        // TODO 책 중복 확인 필요
        Book newBook = Book.builder()
                .title(dto.getTitle())
                .category(dto.getCategory())
                .genre(dto.getGenre())
                .nation(dto.getNation())
                .price(dto.getPrice())
                .build();
        bookRepository.save(newBook);

        return newBook;
    }

}
