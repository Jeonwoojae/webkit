package kr.ac.kumoh.webkit.library.domain.book.repository;

import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
