package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext // 스프링부트가 eneity manager를 생성하고 주입해준다.
    private EntityManager em;

    // member가 아닌 id를 반환하는 이유: command와 query를 분리하라.(김영한님 스타일)
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
