package ru.itis.repository;

public interface ISettingsRepository {
    int getUserCount();          // читает value для name='user_count'
    void updateUserCount(int n); // (опционально) меняет значение
}