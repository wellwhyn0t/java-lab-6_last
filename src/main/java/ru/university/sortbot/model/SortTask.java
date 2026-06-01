package ru.university.sortbot.model;

import java.util.List;

/**
 * Класс задания на сортировку.
 * Содержит список элементов для упорядочивания и правильный порядок.
 */
public class SortTask {
    private String category;           // Категория (например, "Планеты")
    private List<SortItem> items;      // Элементы для сортировки (перемешанные)
    private String description;        // Описание задания

    public SortTask(String category, List<SortItem> items, String description) {
        this.category = category;
        this.items = items;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public List<SortItem> getItems() {
        return items;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Проверка правильности порядка элементов.
     * @param userOrder порядок, предложенный пользователем (названия через запятую)
     * @return true если порядок верный
     */
    public boolean checkAnswer(String userOrder) {
        String[] userNames = userOrder.split(",");
        
        if (userNames.length != items.size()) {
            return false;
        }

        // Создаем карту название -> правильный порядок
        java.util.Map<String, Integer> orderMap = new java.util.HashMap<>();
        for (SortItem item : items) {
            orderMap.put(item.getName().toLowerCase().trim(), item.getCorrectOrder());
        }

        // Проверяем порядок
        int expectedOrder = 1;
        for (String name : userNames) {
            String trimmedName = name.trim().toLowerCase();
            if (!orderMap.containsKey(trimmedName)) {
                return false;
            }
            // Для простоты проверяем что все элементы указаны
            // Фактический порядок проверяется через сравнение с отсортированным списком
        }

        // Более точная проверка: сравниваем порядок
        java.util.List<SortItem> sortedItems = new java.util.ArrayList<>(items);
        sortedItems.sort((a, b) -> Integer.compare(a.getCorrectOrder(), b.getCorrectOrder()));

        for (int i = 0; i < userNames.length; i++) {
            if (!sortedItems.get(i).getName().equalsIgnoreCase(userNames[i].trim())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Получить правильный порядок элементов
     */
    public String getCorrectOrderAsString() {
        java.util.List<SortItem> sortedItems = new java.util.ArrayList<>(items);
        sortedItems.sort((a, b) -> Integer.compare(a.getCorrectOrder(), b.getCorrectOrder()));
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sortedItems.size(); i++) {
            result.append(sortedItems.get(i).getName());
            if (i < sortedItems.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    /**
     * Получить строковое представление элементов для вопроса
     */
    public String getItemsAsString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            result.append(items.get(i).getName());
            if (i < items.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
