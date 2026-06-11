package org.example;

public interface Member extends Comparable<Member> {
    String getName();
    String getEmail();
    String getPhone();
    String getGrade();
    String getBenefit();

    void update(String name, String email, String phone);

    default void printInfo() {
        System.out.println("[" + getGrade() + "] " + getName() + " / " + getEmail()
                + " / " + getPhone() + " (benefit: " + getBenefit() + ")");
    }

    default void printGreeting() {
        System.out.println("Hello, " + getName() + "your grade is" + getGrade());
    }

    @Override
    default int compareTo(Member o) {
        int gradeCompare = o.getGrade().compareTo(this.getGrade());

        if (gradeCompare == 0) {
            return this.getName().compareTo(o.getName());
        }

        return gradeCompare;
    }
}
