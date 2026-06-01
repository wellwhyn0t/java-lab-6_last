package ru.university.sortbot.model;


public class UserSession {
    private int correctAnswers;      // Количество правильных ответов
    private int totalQuestions;      // Общее количество вопросов
    private boolean gameInProgress;  // Флаг активной игры
    private SortTask currentTask;    // Текущее задание на сортировку

    public UserSession() {
        this.correctAnswers = 0;
        this.totalQuestions = 0;
        this.gameInProgress = false;
        this.currentTask = null;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void incrementCorrect() {
        this.correctAnswers++;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void incrementTotal() {
        this.totalQuestions++;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(boolean inProgress) {
        this.gameInProgress = inProgress;
    }

    public SortTask getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(SortTask task) {
        this.currentTask = task;
    }


    public void resetStats() {
        this.correctAnswers = 0;
        this.totalQuestions = 0;
        this.gameInProgress = false;
        this.currentTask = null;
    }


    public double getAccuracy() {
        if (totalQuestions == 0) {
            return 0.0;
        }
        return (double) correctAnswers / totalQuestions * 100;
    }
}
