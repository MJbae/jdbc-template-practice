package mj.jdbc_template_test;

import mj.jdbc_template_test.domain.Member;
import mj.jdbc_template_test.repository.MemberRepository;
import mj.jdbc_template_test.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Test
    void 회원가입() {
        //Given
        Member member = new Member();
        member.setName("h");

        //When
        Long savedId = memberService.join(member);

        //Then
        Member foundMember = memberService.findOne(savedId).get();
        assertThat(foundMember.getId()).isEqualTo(savedId);
    }

    @Test
    void 중복_회원_예외() {
        //Given
        Member member1 = new Member();
        member1.setName("ss");
        Member member2 = new Member();
        member2.setName("ss");

        //When
        memberService.join(member1);

        //Then
        //예외가 발생해야 한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void 모든_회원_조회() {
        //Given
        List<Member> oldMembers = memberService.findMembers();
        Member member1 = new Member();
        member1.setName("s1");
        Member member2 = new Member();
        member2.setName("s2");
        memberService.join(member1);
        memberService.join(member2);

        //when
        List<Member> newMembers = memberService.findMembers();

        //then
        System.out.println("new members " + newMembers.size());
        assertThat(newMembers.size() - oldMembers.size()).isEqualTo(2);
    }
}
