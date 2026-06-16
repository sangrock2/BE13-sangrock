package org.example;

public interface Member extends Comparable<Member> {
    String getName();
    String getEmail();
    String getPhone();
    Grade getGrade();

    void update(String name, String email, String phone);

    default void printInfo() {
        System.out.println("[" + getGrade() + "] " + getName() + " / "
                + getEmail() + " / " + getPhone() + " (Benefit: " + getGrade().getBenefit() + ")");
    }
}
