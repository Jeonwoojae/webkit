package kr.ac.kumoh.webkit.library.domain.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kumoh.webkit.library.domain.book.entity.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static kr.ac.kumoh.webkit.library.domain.book.entity.QBook.book;

@Repository
@Slf4j
public class BookRepositoryImpl implements BookRepositoryCustom{
    @Autowired
    private JPAQueryFactory queryFactory;
    @Override
    public List<Book> findDynamicQueryAdvance(String nation, String genre, String title, String category, Integer price) {
        return queryFactory
                .selectFrom(book)
                .where(eqNation(nation),
                        eqGenre(genre),
                        eqTitle(title),
                        eqCategory(category),
                        eqPrice(price))
                .fetch();
    }
    

    private BooleanExpression eqNation(String nation) {
        if (StringUtils.isEmpty(nation)) {
            return null;
        }
        return book.nation.eq(nation);
    }

    private BooleanExpression eqGenre(String genre) {
        if (StringUtils.isEmpty(genre)) {
            return null;
        }
        return book.genre.eq(genre);
    }

    private BooleanExpression eqTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            return null;
        }
        return book.title.eq(title);
    }
    private BooleanExpression eqCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            return null;
        }
        return book.category.eq(category);
    }

    private BooleanExpression eqPrice(Integer price) {
        if (StringUtils.isEmpty(price)) {
            return null;
        }
        return price < 0 ? null : book.price.lt(price);
    }
}
