package ru.university.sortbot.config;

/**
 * Класс конфигурации Telegram-бота.
 * Вариант 18: Сортировщик по порядку
 * 
 * Для запуска бота необходимо:
 * 1. Создать бота через @BotFather в Telegram
 * 2. Получить токен и имя бота
 * 3. Вставить их в соответствующие поля ниже
 */
public class BotConfig {

    // Имя вашего бота (без @) - укажите имя, которое вы дали боту при создании
    public static final String BOT_USERNAME = "sort_order_bot";

    // Токен бота - ПОЛУЧИТЕ У @BotFather и вставьте вместо пустой строки
    // Пример формата: 1234567890:AAHdqTcvCH1vGWJxfSeofSAs0K5PALDsaw
    public static final String BOT_TOKEN = "";

    // Путь к ресурсам (для загрузки данных из файлов)
    public static final String RESOURCES_PATH = "src/main/resources/";
}
