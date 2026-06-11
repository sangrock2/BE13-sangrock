package org.example;

public abstract class Member implements Comparable<Member> {
    protected String name, email, phone;

    public Member(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void printInfo() {
        System.out.println("[" + getGrade() + "] " + name + " / " + email
                + " / " + phone + " (benefit: " + getBenefit() + ")");
    }

    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public int compareTo(Member o) {
        int gradeCompare = o.getGrade().compareTo(this.getGrade());

        if (gradeCompare == 0) {
            return this.getName().compareTo(o.getName());
        }

        return gradeCompare;
    }

    public abstract String getGrade();
    public abstract String getBenefit();
    public abstract int getMonthlyFee();
}
