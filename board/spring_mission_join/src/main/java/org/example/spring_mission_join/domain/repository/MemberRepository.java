package org.example.spring_mission_join.domain.repository;

import org.example.spring_mission_join.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String userId);
    Optional<Member> findByUserId(String userId);
}
