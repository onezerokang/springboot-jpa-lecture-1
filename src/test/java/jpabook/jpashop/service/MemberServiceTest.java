package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 테스트할 때는 기본적으로 롤백을 수행한다.
public class MemberServiceTest {
    // 테스트용 db를 따로 만들기 번거롭다. 자바에서 in-memory 데이터베이스를 띄우는 방법이 있다.

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given: 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        //when: 실행하면
        Long savedId = memberService.join(member);

        //then: 결과가 나와야 한다.
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야 한다.");
    }

}