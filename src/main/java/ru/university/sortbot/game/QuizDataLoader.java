package ru.university.sortbot.game;

import ru.university.sortbot.model.SortItem;
import ru.university.sortbot.model.SortTask;

import java.util.*;

/**
 * Класс для загрузки данных викторины.
 * Загружает вопросы из встроенных данных (можно расширить загрузкой из файла).
 */
public class QuizDataLoader {
    
    // Все доступные категории вопросов
    private static final List<SortTask> ALL_TASKS = new ArrayList<>();

    static {
        // Категория 1: Планеты Солнечной системы по удалённости от Солнца
        List<SortItem> planets = Arrays.asList(
            new SortItem("Юпитер", 5),
            new SortItem("Земля", 3),
            new SortItem("Меркурий", 1),
            new SortItem("Марс", 4),
            new SortItem("Венера", 2)
        );
        shuffleItems(planets);
        ALL_TASKS.add(new SortTask("Планеты", planets, 
            "Расположите планеты Солнечной системы по удалённости от Солнца (от ближней к дальней)"));

        // Категория 2: Материки по площади
        List<SortItem> continents = Arrays.asList(
            new SortItem("Африка", 2),
            new SortItem("Евразия", 1),
            new SortItem("Австралия", 6),
            new SortItem("Северная Америка", 3),
            new SortItem("Южная Америка", 4),
            new SortItem("Антарктида", 5)
        );
        shuffleItems(continents);
        ALL_TASKS.add(new SortTask("Материки", continents,
            "Расположите материки по площади (от большего к меньшему)"));

        // Категория 3: Числа римскими цифрами по возрастанию
        List<SortItem> romanNumbers = Arrays.asList(
            new SortItem("L", 50),
            new SortItem("X", 10),
            new SortItem("C", 100),
            new SortItem("V", 5),
            new SortItem("M", 1000)
        );
        shuffleItems(romanNumbers);
        ALL_TASKS.add(new SortTask("Римские цифры", romanNumbers,
            "Расположите римские цифры по возрастанию их значений"));

        // Категория 4: Времена года (месяцы начала сезонов в северном полушарии)
        List<SortItem> seasons = Arrays.asList(
            new SortItem("Июнь", 2),
            new SortItem("Март", 1),
            new SortItem("Декабрь", 4),
            new SortItem("Сентябрь", 3)
        );
        shuffleItems(seasons);
        ALL_TASKS.add(new SortTask("Времена года", seasons,
            "Расположите месяцы начала времён года по порядку (весна, лето, осень, зима)"));

        // Категория 5: Цвета радуги
        List<SortItem> rainbow = Arrays.asList(
            new SortItem("Жёлтый", 2),
            new SortItem("Красный", 1),
            new SortItem("Голубой", 5),
            new SortItem("Оранжевый", 3),
            new SortItem("Синий", 6),
            new SortItem("Зелёный", 4),
            new SortItem("Фиолетовый", 7)
        );
        shuffleItems(rainbow);
        ALL_TASKS.add(new SortTask("Радуга", rainbow,
            "Расположите цвета радуги в правильном порядке (запомни: Каждый Охотник Желает...)"));

        // Категория 6: Ноты музыкальной гаммы
        List<SortItem> notes = Arrays.asList(
            new SortItem("Ми", 3),
            new SortItem("До", 1),
            new SortItem("Соль", 5),
            new SortItem("Ре", 2),
            new SortItem("Ля", 6),
            new SortItem("Фа", 4),
            new SortItem("Си", 7)
        );
        shuffleItems(notes);
        ALL_TASKS.add(new SortTask("Ноты", notes,
            "Расположите ноты музыкальной гаммы по порядку (До, Ре, Ми...)"));
    }

    /**
     * Перемешивание списка элементов
     */
    private static void shuffleItems(List<SortItem> items) {
        Collections.shuffle(items);
    }

    /**
     * Получить случайное задание
     */
    public static SortTask getRandomTask() {
        if (ALL_TASKS.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return ALL_TASKS.get(random.nextInt(ALL_TASKS.size()));
    }

    /**
     * Получить все доступные задания
     */
    public static List<SortTask> getAllTasks() {
        return new ArrayList<>(ALL_TASKS);
    }

    /**
     * Получить количество доступных заданий
     */
    public static int getTaskCount() {
        return ALL_TASKS.size();
    }
}
