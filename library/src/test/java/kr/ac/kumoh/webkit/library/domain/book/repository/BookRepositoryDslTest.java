package kr.ac.kumoh.webkit.library.domain.book.repository;

import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.entity.QBook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class BookRepositoryDslTest {
    @Autowired
    EntityManager em;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void cleanup() {
        // given
        String title = "hi_book";
        String category = "국내만화";
        String nation = "대한민국";
        String genre = "코믹";
        int price = 3_000;

        bookRepository.save(Book.builder()
                .title(title)
                .category(category)
                .nation(nation)
                .genre(genre)
                .price(price)
                .build());
    }

    @DisplayName("hi 내용을 포함하며 장르가 코믹인 Book을 ID 내림차순으로 조회한다.")
    @Test
    public void 책_검색하기() {
        JPAQuery<Book> query = new JPAQuery<>(em);
        QBook qBook = new QBook("b");

        List<Book> books = query.from(qBook)
                .where(qBook.title.contains("hi")
                        .and(qBook.genre.contains("코믹"))
                ).orderBy(qBook.id.desc())
                .fetch();

        assertThat(books.get(0).getTitle()).isEqualTo("hi_book");
    }

}