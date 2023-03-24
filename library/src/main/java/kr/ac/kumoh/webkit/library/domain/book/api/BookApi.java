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

    @GetMapping("/search/nation/{nation}/genre/{genre}")
    public ResponseEntity<List<Book>> findByGenreAndNation(@PathVariable("nation")String nation,
                                                           @PathVariable("genre")String genre){
        final List<Book> bookList = bookSearchService.findBooksByNationAndGenre(nation, genre);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/nation/{nation}")
    public ResponseEntity<List<Book>> findByNation(@PathVariable("nation")String nation){
        final List<Book> bookList = bookSearchService.findBooksByNation(nation);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/price/{value}")
    public ResponseEntity<List<Book>> findByPrice(@PathVariable("value")Integer value){
        final List<Book> bookList = bookSearchService.findBooksByUnderPrice(value);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/nation/{nation}/title/{title}")
    public ResponseEntity<List<Book>> findByTitleAndNation(@PathVariable("title")String title,
                                                           @PathVariable("nation")String nation){
        final List<Book> bookList = bookSearchService.findBooksByNationAndTitle(nation, title);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/nation/{nation}/category/{category}")
    public ResponseEntity<List<Book>> findByCategoryAndNation(@PathVariable("category")String category,
                                                              @PathVariable("nation")String nation){
        final List<Book> bookList = bookSearchService.findBooksByNationAndCategory(nation, category);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/nation/{nation}/price/{value}")
    public ResponseEntity<List<Book>> findByPriceAndNation(@PathVariable("value")Integer value,
                                                           @PathVariable("nation")String nation){
        final List<Book> bookList = bookSearchService.findBooksByNationAndUnderPrice(nation, value);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/nation/{nation}/genre/{genre}/title/{title}")
    public ResponseEntity<List<Book>> findByTitleAndNation(@PathVariable("title")String title,
                                                           @PathVariable("nation")String nation,
                                                           @PathVariable("genre")String genre){
        final List<Book> bookList = bookSearchService.findBooksByNationAndGenreAndTitle(nation,genre, title);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/nation/{nation}/genre/{genre}/category/{category}")
    public ResponseEntity<List<Book>> findByCategoryAndNation(@PathVariable("category")String category,
                                                              @PathVariable("nation")String nation,
                                                              @PathVariable("genre")String genre){
        final List<Book> bookList = bookSearchService.findBooksByNationAndGenreAndCategory(nation,genre, category);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/search/nation/{nation}/genre/{genre}/price/{value}")
    public ResponseEntity<List<Book>> findByPriceAndNation(@PathVariable("value")Integer value,
                                                           @PathVariable("nation")String nation,
                                                           @PathVariable("genre")String genre){
        final List<Book> bookList = bookSearchService.findBooksByNationAndGenreAndUnderPrice(nation,genre, value);

        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

}
