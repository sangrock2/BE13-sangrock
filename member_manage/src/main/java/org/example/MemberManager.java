package org.example;

import java.util.ArrayList;

public class MemberManager {
    private final ArrayList<Member> members = new ArrayList<>();

    public int getMemberCount() {
        return members.size();
    }

    public boolean isEmailExist(String email) {
        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Member findByEmail(String email) {
        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                return member;
            }
        }
        return null;
    }

    public ArrayList<Member> findByName(String name) {
        ArrayList<Member> foundMembers = new ArrayList<>();

        for (Member member : members) {
            if (member.getName().equals(name)) {
                foundMembers.add(member);
            }
        }
        return foundMembers;
    }

    public ArrayList<Member> getAllMembers() {
        return members;
    }

    public boolean deleteMember(String email) {
        Member target = findByEmail(email);
        if (target != null) {
            members.remove(target);
            return true;
        }
        return false;
    }
}
