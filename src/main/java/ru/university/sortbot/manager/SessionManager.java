package ru.university.sortbot.manager;

import ru.university.sortbot.model.SortItem;
import ru.university.sortbot.model.SortTask;
import ru.university.sortbot.model.UserSession;

import java.util.*;


public class SessionManager {
    // Хранилище сессий: chatId -> UserSession
    private final Map<Long, UserSession> sessions;

    public SessionManager() {
        this.sessions = new HashMap<>();
    }


    public UserSession getSession(long chatId) {
        return sessions.computeIfAbsent(chatId, k -> new UserSession());
    }


    public void removeSession(long chatId) {
        sessions.remove(chatId);
    }


    public boolean hasSession(long chatId) {
        return sessions.containsKey(chatId);
    }


    public int getActiveSessionsCount() {
        return (int) sessions.values().stream()
                .filter(UserSession::isGameInProgress)
                .count();
    }
}
