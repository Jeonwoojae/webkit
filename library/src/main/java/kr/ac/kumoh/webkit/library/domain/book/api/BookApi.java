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

import java.util.ArrayList;
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

    @GetMapping("/v1/search")
    public ResponseEntity<List<Book>> getBooksByCondition(@RequestParam(name = "nation", required = false)String nation,
            @RequestParam(name = "genre", required = false)String genre,
            @RequestParam(name = "title", required = false)String title,
            @RequestParam(name = "category", required = false)String category,
            @RequestParam(name = "price", required = false)Integer value){
        List<Book> bookList = new ArrayList<>();
            if (nation != null){
                if (genre == null){
                    if (title != null){
                        bookList = bookSearchService.findBooksByNationAndTitle(nation,title);
                    } else if (category != null){
                        bookList = bookSearchService.findBooksByNationAndCategory(nation, category);
                    } else if (value != null){
                        bookList = bookSearchService.findBooksByNationAndUnderPrice(nation, value);
                    } else{
                        bookList = bookSearchService.findBooksByNation(nation);
                    }
                } else {
                    if (title != null){
                        bookList = bookSearchService.findBooksByNationAndGenreAndTitle(nation, genre, title);
                    } else if (category != null){
                        bookList = bookSearchService.findBooksByNationAndGenreAndCategory(nation, genre, category);
                    } else if (value != null){
                        bookList = bookSearchService.findBooksByNationAndGenreAndUnderPrice(nation, genre, value);
                    } else{
                        bookList = bookSearchService.findBooksByNationAndGenre(nation, genre);
                    }
                }
            } else {
                if (title != null){
                    bookList = bookSearchService.findBooksByTitle(title);
                } else if (category != null){
                    bookList = bookSearchService.findBooksByCategory(category);
                } else if (value != null){
                    bookList = bookSearchService.findBooksByUnderPrice(value);
                }
            }

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> getBooksDynamic(@RequestParam(name = "nation", required = false)String nation,
                                                          @RequestParam(name = "genre", required = false)String genre,
                                                          @RequestParam(name = "title", required = false)String title,
                                                          @RequestParam(name = "category", required = false)String category,
                                                          @RequestParam(name = "price", defaultValue = "999999")Integer value) {
        List<Book> bookList = bookSearchService.getBookDynamic(nation,genre,title,category,value);
        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }
}
