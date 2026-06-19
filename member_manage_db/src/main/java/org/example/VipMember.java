package org.example;

public class VipMember implements Member {
    private String name, email, phone;

    public VipMember(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }
    @Override public String getName()  { return name; }
    @Override public String getEmail() { return email; }
    @Override public String getPhone() { return phone; }
    @Override public String getGrade()   { return "VIP"; }
    @Override public String getBenefit() { return "10% Off + Free Shipping"; }
    @Override public void update(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }
}
