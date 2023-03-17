package kr.ac.kumoh.webkit.library.domain.book.api;

import kr.ac.kumoh.webkit.library.domain.book.dto.AddBook;
import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import kr.ac.kumoh.webkit.library.domain.book.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void tearDown() throws Exception {
        bookRepository.deleteAll();
    }

    @Test
    public void book_등록된다() throws Exception {
        // given
        String title = "훈민정음";
        String category = "국내만화";
        String nation = "대한민국";
        String genre = "일상";
        int price = 3_000;

        AddBook dto = new AddBook();
        dto.setTitle(title);
        dto.setCategory(category);
        dto.setNation(nation);
        dto.setGenre(genre);
        dto.setPrice(price);

        String url = "http://localhost:" + port + "/book";

        // when
        ResponseEntity<Book> responseEntity = restTemplate.postForEntity(url, dto, Book.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Book> bookList = bookRepository.findAll();
        Book book = bookList.get(0);
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getCategory()).isEqualTo(category);
        assertThat(book.getNation()).isEqualTo(nation);
        assertThat(book.getGenre()).isEqualTo(genre);
        assertThat(book.getPrice()).isEqualTo(price);
    }

}