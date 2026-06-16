package org.example;

import java.util.*;

public class MemberManager implements MemberRepository{
    private Map<String, Member> members;
    private int capacity;

    public MemberManager(int capacity) {
        this.members = new HashMap<>();
        this.capacity = capacity;
    }

    public boolean existsEmail(String email) {
        return members.containsKey(email);
    }

    @Override
    public void add(Member m) {
        members.put(m.getEmail(), m);
    }

    @Override
    public Member findByEmail(String email) {
        return members.get(email);
    }

    @Override
    public List<Member> findByName(String name) {
        List<Member> result = new ArrayList<>();

        for (Member m : members.values()) {
            if (m.getName().equals(name)) result.add(m);
        }

        return result;
    }

    @Override
    public boolean delete(String email) {
        return members.remove(email) != null;
    }

    @Override
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>(members.values());
        Collections.sort(list);

        return list;
    }

    @Override public int getCount() { return members.size(); }
    @Override public int getCapacity() { return capacity; }

    public boolean update(String oldEmail, String name, String newEmail, String phone) {
        Member m = members.get(oldEmail);

        if (m == null) return false;

        if (!oldEmail.equals(newEmail)) {
            if (existsEmail(newEmail)) return false;

            members.remove(oldEmail);
            m.update(name, newEmail, phone);
            members.put(newEmail, m);
        } else {
            m.update(name, newEmail, phone);
        }

        return true;
    }
}
