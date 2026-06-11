package org.example;

public class VvipMember extends Member {
    public VvipMember(String name, String email, String phone) {
        super(name, email, phone);
    }

    @Override
    public String getGrade() {
        return "VVIP";
    }

    @Override
    public String getBenefit() {
        return "10% Off + Free Shipping + Free Lounge";
    }

    @Override
    public int getMonthlyFee() {
        return 100000;
    }
}
