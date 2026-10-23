package ru.itis.repository;

import java.sql.*;

public class SettingsRepositoryJdbcImpl implements ISettingsRepository {

    private final Connection connection;

    private static final String SQL_SELECT =
            "select value from settings where name = 'user_count'";
    private static final String SQL_UPDATE =
            "update settings set value = ? where name = 'user_count'";

    public SettingsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getUserCount() {
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt("value");
                if (count > 0) {
                    return count;
                }
                throw new IllegalStateException("Настройка user_count должна быть больше нуля");
            }
            throw new IllegalStateException("В таблице settings нет настройки user_count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserCount(int n) {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, n);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
