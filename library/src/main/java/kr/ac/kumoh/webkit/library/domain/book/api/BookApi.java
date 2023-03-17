package kr.ac.kumoh.webkit.library.domain.book.api;

import kr.ac.kumoh.webkit.library.domain.book.dto.AddBook;
import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.service.BookAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookApi {
    private final BookAddService bookAddService;

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody AddBook dto) {
        final Book book =bookAddService.addBook(dto);

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}
