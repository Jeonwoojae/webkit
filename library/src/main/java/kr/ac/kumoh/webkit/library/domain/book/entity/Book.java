package kr.ac.kumoh.webkit.library.domain.book.entity;

import kr.ac.kumoh.webkit.library.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;
    @Column(nullable = false, length = 200)
    private String category;
    @Column(nullable = false, length = 50)
    private String nation;
    @Column(nullable = false, length = 50)
    private String genre;
    private int price;

    @Builder
    public Book(String title, String category, String nation, String genre, int price) {
        this.title = title;
        this.category = category;
        this.nation = nation;
        this.genre = genre;
        this.price = price;
    }
}
