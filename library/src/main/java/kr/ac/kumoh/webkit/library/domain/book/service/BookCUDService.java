package kr.ac.kumoh.webkit.library.domain.book.service;

import kr.ac.kumoh.webkit.library.domain.book.dto.AddBook;
import kr.ac.kumoh.webkit.library.domain.book.dto.UpdateBook;
import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCUDService {
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

    public Book updateBook(final Long id, final UpdateBook dto) {
        Book oldBook = bookRepository.findById(id).orElseThrow(()->new NoSuchElementException("해당 게시글이 없습니다."));

        oldBook.update(
                dto.getTitle(),
                dto.getCategory(),
                dto.getNation(),
                dto.getGenre(),
                dto.getPrice()
        );

        return oldBook;
    }

}
