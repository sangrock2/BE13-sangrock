package org.example;

public class NormalMember extends Member {
    public NormalMember(String name, String email, String phone) {
        super(name, email, phone);
    }

    @Override
    public String getGrade() {
        return "normal";
    }

    @Override
    public String getBenefit() {
        return "basic";
    }

    @Override
    public int getMonthlyFee() {
        return 10000;
    }
}
