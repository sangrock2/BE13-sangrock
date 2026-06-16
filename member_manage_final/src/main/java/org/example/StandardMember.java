package org.example;

public class StandardMember implements Member {
    private String name;
    private String email;
    private String phone;
    private Grade grade;

    public StandardMember(String name, String email, String phone, Grade grade) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.grade = grade;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public Grade getGrade() {
        return grade;
    }

    @Override
    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public int compareTo(Member o) {
        return this.getName().compareTo(o.getName());
    }
}
