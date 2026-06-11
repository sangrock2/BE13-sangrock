package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class MemberManager {
    private ArrayList<Member> members;
    private int capacity;

    public MemberManager(int capacity) {
        this.members = new ArrayList<>();
        this.capacity = capacity;
    }

    public boolean isFull() {
        return members.size() >= capacity;
    }

    public boolean existsEmail(String email) {
        for (Member m : members) {
            if (m.getEmail().equals(email)) return true;
        }
        return false;
    }

    public void add(Member m) {
        members.add(m);
    }

    public int getCount()    { return members.size(); }
    public int getCapacity() { return capacity; }

    public Member findByEmail(String email) {
        for (Member m : members) {
            if (m.getEmail().equals(email)) return m;
        }
        return null;
    }

    public ArrayList<Member> findByName(String name) {
        ArrayList<Member> result = new ArrayList<>();
        for (Member m : members) {
            if (m.getName().equals(name)) result.add(m);
        }
        return result;
    }

    public void printAll() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        Collections.sort(members);

        for (Member m : members) {
            m.printInfo();
            System.out.println();
        }
    }

    public boolean update(String email, String name, String newEmail, String phone) {
        if (!email.equals(newEmail) && existsEmail(newEmail)) {
            return false;
        }

        Member m = findByEmail(email);
        if (m == null) return false;
        m.update(name, newEmail, phone);
        return true;
    }

    public boolean delete(String email) {
        Member m = findByEmail(email);
        if (m == null) return false;
        members.remove(m);
        return true;
    }
}
