package org.example;

import java.util.List;

public interface MemberRepository {
    void add(Member member);
    Member findByEmail(String email);
    List<Member> findByName(String name);
    boolean delete(String email);
    List<Member> findAll();
    int getCount();
    int getCapacity();

    boolean existsEmail(String email);
    boolean update(String oldEmail, String name, String newEmail, String phone);

    void save();
}
