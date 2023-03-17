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
        return null;

    }

    public List<Book> findBooksByTitle(){
        return null;

    }
    public List<Book> findBooksByCategory(){
        return null;

    }
    public List<Book> findBooksByPrice(){
        return null;
    }

    public List<Book> findBooksByGenre() {
        return null;
    }

    public Book findOne(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당하는 책을 찾을 수 없습니다."));

        return book;
    }
}
