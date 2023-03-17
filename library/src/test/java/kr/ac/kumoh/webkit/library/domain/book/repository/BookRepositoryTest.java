package kr.ac.kumoh.webkit.library.domain.book.repository;

import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @AfterEach
    public void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    public void 책저장_불러오기() {
        // given
        String title = "훈민정음";
        String category = "국내만화";
        String nation = "대한민국";
        String genre = "일상";
        int price = 3_000;

        bookRepository.save(Book.builder()
                        .title(title)
                        .category(category)
                        .nation(nation)
                        .genre(genre)
                        .price(price)
                .build());

        // when
        List<Book> bookList = bookRepository.findAll();

        // then
        Book book = bookList.get(0);
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getCategory()).isEqualTo(category);
        assertThat(book.getNation()).isEqualTo(nation);
        assertThat(book.getGenre()).isEqualTo(genre);
        assertThat(book.getPrice()).isEqualTo(price);
    }

}