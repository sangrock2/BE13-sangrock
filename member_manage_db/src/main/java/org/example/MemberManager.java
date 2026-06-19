package org.example;

import java.sql.*;

public class MemberManager {
    private final int capacity;

    public MemberManager(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() { return size() >= capacity; }

    public boolean existsEmail(String email) {
        String sql = "SELECT COUNT(*) FROM member WHERE email = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return false;
    }

    public void add(Member m) {
        String sql = "insert into member (grade, name, email, phone) values(?, ?, ?, ?)";

        try (
            Connection conn = connection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, m.getGrade());
            ps.setString(2, m.getName());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Member toMember(ResultSet rs) throws SQLException {
        String grade = rs.getString("grade");
        String name  = rs.getString("name");
        String email = rs.getString("email");
        String phone = rs.getString("phone");

        return grade.equals("VIP") ? new VipMember(name, email, phone) : new NormalMember(name, email, phone);
    }

    public int size()     {
        String sql = "SELECT COUNT(*) FROM member";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return 0;
    }
    public int capacity() { return capacity; }

    private Member findMemberByColumn(String column, String value) {
        String sql = "SELECT grade, name, email, phone FROM member WHERE " + column + " = ?";

        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return toMember(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Member findByEmail(String email) {
        return findMemberByColumn("email", email);
    }

    public Member findByName(String name) {
        return findMemberByColumn("name", name);
    }

    public void printAll() {
        String sql = "SELECT grade, name, email, phone FROM member";

        try (
            Connection conn = connection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            boolean empty = true;
            while (rs.next()) { toMember(rs).printInfo(); empty = false; }
            if (empty) System.out.println("Not members found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(String email, String name, String newEmail, String phone) {
        String sql = "UPDATE member SET name = ?, email = ?, phone = ? WHERE email = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, newEmail);
            ps.setString(3, phone);
            ps.setString(4, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public boolean delete(String email) {
        String sql = "DELETE FROM member WHERE email = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Connection connection() {
        String url = "jdbc:mysql://localhost:3306/java_basic";
        String user = "programmers";
        String password = "password1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
