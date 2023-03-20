package kr.ac.kumoh.webkit.library.domain.book.service;

import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.repository.BookRepository;
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
    public List<Book> findBooksByPrice(int start, int end){
        List<Book> bookList = bookRepository.findBooksByPriceBetween(start,end);

        return bookList;
    }

    public List<Book> findBooksByGenre(String genre) {
        List<Book> bookList = bookRepository.findBooksByGenre(genre);

        return bookList;
    }

    public Book findOne(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당하는 책을 찾을 수 없습니다."));

        return book;
    }
}