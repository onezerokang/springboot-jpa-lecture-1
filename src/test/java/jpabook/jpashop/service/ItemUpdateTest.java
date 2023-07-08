package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test void update() throws Exception {
        Book book = em.find(Book.class, 1L);

        book.setName("adfsdf");

        // 변경 감지 == dirty checking
        // 트랜잭션이 커밋이 되면 변경된 내용을 DB에 반영한다.
    }
}
