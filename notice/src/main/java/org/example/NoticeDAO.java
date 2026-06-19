package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/notice";
    private static final String DB_USER = "programmers";
    private static final String DB_PASS = "password1234";

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUserId(String userId) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_id = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error checking ID duplicate.", e);
        }

        return false;
    }

    public boolean sigupExc(String userId, String password, String name) {
        String sql = "INSERT INTO user (user_id, password, name) VALUES (?, ?, ?)";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, userId);
            ps.setString(2, password);
            ps.setString(3, name);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("error during signin process", e);
        }
    }

    public SignInResponseDTO signInExc(String userId, String password) {
        String sql = "SELECT user_id, name, password FROM user WHERE user_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (rs.getString("password").equals(password)) {
                        return new SignInResponseDTO(true, rs.getString("user_id"), rs.getString("name"));
                    } else {
                        return new SignInResponseDTO(false, null, null);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error during login processing.", e);
        }

        return null;
    }

    public boolean newNotice(String userId, String content) {
        String sql = "INSERT INTO content (user_id, content) VALUES (?, ?)";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, userId);
            ps.setString(2, content);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error posting new notice.", e);
        }
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT id, user_id, content, created FROM content";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            list = new ArrayList<>();

            while (rs.next()) {
                String noticeStr = "[id]" + rs.getString("id") + "-" + rs.getString("content") + "-" + rs.getString("created");
                list.add(noticeStr);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching notice list.", e);
        }

        return list;
    }

    public List<String> getListByUserId(String userId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT id, user_id, content, created FROM content WHERE user_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String noticeStr = String.format("[%d] %s (Date: %s)", rs.getInt("id"), rs.getString("content"), rs.getTimestamp("created"));
                    list.add(noticeStr);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user's notice list.", e);
        }

        return list;
    }

    public boolean updateNotice(int id, String content, String userId) {
        String sql = "UPDATE content SET content = ?, created = ? WHERE id = ? AND user_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, content);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(3, id);
            ps.setString(4, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating notice.", e);
        }
    }

    public boolean deleteNotice(int id, String userId) {
        String sql = "DELETE FROM content WHERE id = ? AND user_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ps.setString(2, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting notice.", e);
        }
    }

    public boolean leaveExc(String userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user account.", e);
        }
    }

    public void deleteContentExc(String userId) {
        String sql = "DELETE FROM content WHERE user_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user's contents.", e);
        }
    }
}
