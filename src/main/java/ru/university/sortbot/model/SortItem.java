package ru.university.sortbot.model;

/**
 * Модель элемента для сортировки.
 * Содержит название элемента и его правильный порядок (число).
 */
public class SortItem {
    private String name;      // Название элемента (например, "Земля")
    private int correctOrder; // Правильный порядок (например, 3 для третьей планеты)

    public SortItem(String name, int correctOrder) {
        this.name = name;
        this.correctOrder = correctOrder;
    }

    public String getName() {
        return name;
    }

    public int getCorrectOrder() {
        return correctOrder;
    }

    @Override
    public String toString() {
        return name;
    }
}
