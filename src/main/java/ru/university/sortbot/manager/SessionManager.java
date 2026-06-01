package ru.university.sortbot.manager;

import ru.university.sortbot.model.SortItem;
import ru.university.sortbot.model.SortTask;
import ru.university.sortbot.model.UserSession;

import java.util.*;

/**
 * Менеджер сессий пользователей.
 * Использует HashMap для хранения сессий по ID чата.
 */
public class SessionManager {
    // Хранилище сессий: chatId -> UserSession
    private final Map<Long, UserSession> sessions;

    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    /**
     * Получить или создать сессию для пользователя
     */
    public UserSession getSession(long chatId) {
        return sessions.computeIfAbsent(chatId, k -> new UserSession());
    }

    /**
     * Удалить сессию пользователя
     */
    public void removeSession(long chatId) {
        sessions.remove(chatId);
    }

    /**
     * Проверить наличие сессии
     */
    public boolean hasSession(long chatId) {
        return sessions.containsKey(chatId);
    }

    /**
     * Получить количество активных сессий
     */
    public int getActiveSessionsCount() {
        return (int) sessions.values().stream()
                .filter(UserSession::isGameInProgress)
                .count();
    }
}
