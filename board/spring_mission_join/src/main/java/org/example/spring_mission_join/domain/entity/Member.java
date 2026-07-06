package org.example.spring_mission_join.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;
    private String userName;
}
