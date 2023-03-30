package kr.ac.kumoh.webkit.library.domain.book.repository;

import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findTop5ByOrderByIdDesc();
    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByCategory(String category);
    List<Book> findBooksByNationAndGenre(String nation,String genre);
    List<Book> findBooksByNation(String nation);

    List<Book> findBooksByPriceIsLessThan(int value);

    List<Book> findBooksByNationAndTitle(String nation, String title);

    List<Book> findBooksByNationAndCategory(String nation, String category);

    List<Book> findBooksByNationAndPriceIsLessThan(String nation, int value);

    List<Book> findBooksByNationAndGenreAndTitle(String nation, String genre, String title);

    List<Book> findBooksByNationAndGenreAndCategory(String nation, String genre, String category);

    List<Book> findBooksByNationAndGenreAndPriceIsLessThan(String nation, String genre, int value);
}
