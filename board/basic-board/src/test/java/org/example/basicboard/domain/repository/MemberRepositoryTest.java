package org.example.basicboard.domain.repository;

import org.example.basicboard.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();

        memberRepository.save(member);
    }

    @Test
    @DisplayName("existByUserId - 존재하는 아이디면 true를 반환한다.")
    void existsByUserId_존재하면_true() {
        boolean exists = memberRepository.existsByUserId("test");

        assertThat(exists);
    }

    @Test
    @DisplayName("existByUserId - 존재하지 않는 아이디면 false를 반환한다.")
    void existsByUserId_없으면_false() {
        boolean exists = memberRepository.existsByUserId("nobody");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("existByUserId - 존재하는 아이디로 조회하면 회원이 담긴 Optional을 반환")
    void findByUserId_존재하면_회원() {
        Optional<Member> member = memberRepository.findByUserId("test");

        assertThat(member).isPresent();
        assertThat(member.get().getUserName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("existByUserId - 존재하지 않는 아이디로 조회하면 빈 Optional을 반환")
    void findByUserId_없으면_빈_Optional() {
        Optional<Member> member = memberRepository.findByUserId("nobody");

        assertThat(member).isEmpty();
    }


}
