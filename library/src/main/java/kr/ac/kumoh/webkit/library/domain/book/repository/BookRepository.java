package kr.ac.kumoh.webkit.library.domain.book.repository;

import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findTop5ByOrderByIdDesc();
    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByCategory(String category);
    List<Book> findBooksByNationAndGenre(String nation,String genre);
    List<Book> findBooksByNation(String nation);

    List<Book> findBooksByPriceIsLessThan(int value);

}
