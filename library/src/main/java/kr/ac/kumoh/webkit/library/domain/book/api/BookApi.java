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

import java.util.List;

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

    @GetMapping("/least")
    public ResponseEntity<List<Book>> getLeastBook(){
        final List<Book> bookList = bookSearchService.getLeastBooks();

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<Book>> findByTitle(@PathVariable("title")String title){
        final List<Book> bookList = bookSearchService.findBooksByTitle(title);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<Book>> findByCategory(@PathVariable("category")String category){
        final List<Book> bookList = bookSearchService.findBooksByCategory(category);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/genre/{genre}")
    public ResponseEntity<List<Book>> findByGenre(@PathVariable("genre")String genre){
        final List<Book> bookList = bookSearchService.findBooksByGenre(genre);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/price/{start}/{end}")
    public ResponseEntity<List<Book>> findByPrice(@PathVariable("start")Integer start,
                                                  @PathVariable("end")Integer end){
        final List<Book> bookList = bookSearchService.findBooksByPrice(start,end);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

}
