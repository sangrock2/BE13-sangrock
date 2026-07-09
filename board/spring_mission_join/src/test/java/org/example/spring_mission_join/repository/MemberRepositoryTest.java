package org.example.spring_mission_join.repository;

import org.example.spring_mission_join.domain.entity.Member;
import org.example.spring_mission_join.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();

        memberRepository.save(member);
    }

    @Test
    void existsByUserid_true() {
        boolean exists = memberRepository.existsByUserId("test");

        assertThat(exists).isEqualTo(true);
    }

    @Test
    void existsByUserid_false() {
        boolean exists = memberRepository.existsByUserId("nobody");

        assertThat(exists).isEqualTo(false);
    }

    @Test
    void findByUserId_true() {
        Optional<Member> member = memberRepository.findByUserId("test");

        assertThat(member).isPresent();
        assertThat(member.get().getUserName()).isEqualTo("홍길동");
    }

    @Test
    void findByUserId_false() {
        Optional<Member> member = memberRepository.findByUserId("nobody");

        assertThat(member).isNotPresent();
    }
}
