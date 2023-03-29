package kr.ac.kumoh.webkit.library.domain.book.service;

import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.repository.BookRepository;
import kr.ac.kumoh.webkit.library.domain.book.repository.BookRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BookSearchService {
    final private BookRepository bookRepository;
    final private BookRepositoryImpl bookRepositoryDsl;

    public List<Book> getLeastBooks() {
        List<Book> bookList = bookRepository.findTop5ByOrderByIdDesc();

        return bookList;
    }

    public List<Book> findBooksByTitle(String title){
        List<Book> bookList = bookRepository.findBooksByTitle(title);

        return bookList;

    }
    public List<Book> findBooksByCategory(String category){
        List<Book> bookList = bookRepository.findBooksByCategory(category);

        return bookList;
    }
    public List<Book> findBooksByUnderPrice(int value){
        List<Book> bookList = bookRepository.findBooksByPriceIsLessThan(value);

        return bookList;
    }

    public List<Book> findBooksByNationAndGenre(String nation,
                                                String genre) {
        List<Book> bookList = bookRepository.findBooksByNationAndGenre(nation, genre);

        return bookList;
    }

    public List<Book> findBooksByNation(String nation) {
        List<Book> bookList = bookRepository.findBooksByNation(nation);

        return bookList;
    }

    public Book findOne(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당하는 책을 찾을 수 없습니다."));

        return book;
    }

    public List<Book> findBooksByNationAndTitle(String nation, String title) {
        List<Book> bookList = bookRepository.findBooksByNationAndTitle(nation,title);

        return bookList;
    }

    public List<Book> findBooksByNationAndCategory(String nation, String category) {
        List<Book> bookList = bookRepository.findBooksByNationAndCategory(nation,category);

        return bookList;
    }

    public List<Book> findBooksByNationAndUnderPrice(String nation, int value) {
        List<Book> bookList = bookRepository.findBooksByNationAndPriceIsLessThan(nation,value);

        return bookList;
    }

    public List<Book> findBooksByNationAndGenreAndTitle(String nation, String genre, String title) {
        List<Book> bookList = bookRepository.findBooksByNationAndGenreAndTitle(nation,genre,title);

        return bookList;
    }

    public List<Book> findBooksByNationAndGenreAndCategory(String nation, String genre, String category) {
        List<Book> bookList = bookRepository.findBooksByNationAndGenreAndCategory(nation,genre,category);

        return bookList;
    }

    public List<Book> findBooksByNationAndGenreAndUnderPrice(String nation, String genre, int value) {
        List<Book> bookList = bookRepository.findBooksByNationAndGenreAndPriceIsLessThan(nation,genre,value);

        return bookList;
    }

    public List<Book> getBookDynamic(String nation, String genre, String title, String category, int value){
        List<Book> bookList = bookRepositoryDsl.findDynamicQueryAdvance(nation,genre,title,category,value);

        return bookList;
    }
}
