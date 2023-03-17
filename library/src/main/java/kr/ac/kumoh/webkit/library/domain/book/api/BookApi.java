package kr.ac.kumoh.webkit.library.domain.book.api;

import kr.ac.kumoh.webkit.library.domain.book.dto.AddBook;
import kr.ac.kumoh.webkit.library.domain.book.dto.UpdateBook;
import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.service.BookCUDService;
import kr.ac.kumoh.webkit.library.domain.book.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookApi {
    private final BookCUDService bookCUDService;
    private final BookSearchService bookSearchService;

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody AddBook dto) {
        final Book book = bookCUDService.addBook(dto);

        // TODO entity 객체 그대로가 아닌 필요한 정보만 노출되도록 변경 필요
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id,
                                           @RequestBody UpdateBook updateBookInfo){
        final Book updatedBook = bookCUDService.updateBook(id, updateBookInfo);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id){
        final Book deleteddBook = bookCUDService.deleteBook(id);

        return ResponseEntity.status(HttpStatus.OK).body(deleteddBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        final Book book = bookSearchService.findOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

}
