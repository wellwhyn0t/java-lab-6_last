package ru.university.sortbot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.university.sortbot.config.BotConfig;
import ru.university.sortbot.game.QuizDataLoader;
import ru.university.sortbot.manager.SessionManager;
import ru.university.sortbot.model.SortTask;
import ru.university.sortbot.model.UserSession;

/**
 * Telegram-бот "Сортировщик по порядку" (Вариант 18).
 * 
 * Команды:
 * /start - начать игру и описание
 * /quiz - начать раунд викторины
 * /stop - остановить текущую игру
 * /stats - показать статистику игрока
 */
public class SortBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final SessionManager sessionManager;

    public SortBot() {
        this.telegramClient = new OkHttpTelegramClient(BotConfig.BOT_TOKEN);
        this.sessionManager = new SessionManager();
    }

    @Override
    public void consume(Update update) {
        // Проверяем, что обновление содержит текстовое сообщение
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String userMessage = update.getMessage().getText().trim();
        long chatId = update.getMessage().getChatId();

        // Обрабатываем сообщение и получаем ответ
        String response = processMessage(chatId, userMessage);

        // Отправляем ответ пользователю
        sendText(chatId, response);
    }

    /**
     * Обработка входящих сообщений
     */
    private String processMessage(long chatId, String text) {
        UserSession session = sessionManager.getSession(chatId);

        // Если игра в процессе и пользователь не вводит команду - проверяем ответ
        if (session.isGameInProgress() && !text.startsWith("/")) {
            return checkUserAnswer(chatId, text);
        }

        // Обработка команд
        switch (text.toLowerCase()) {
            case "/start":
                return getStartMessage();

            case "/quiz":
                return startQuiz(chatId);

            case "/stop":
                return stopQuiz(chatId);

            case "/stats":
                return getStats(chatId);

            case "/help":
                return getHelpMessage();

            default:
                if (text.startsWith("/")) {
                    return "❌ Неизвестная команда. Напишите /help для справки.";
                }
                return "🎮 Для начала игры напишите /quiz\nДля справки: /help";
        }
    }

    /**
     * Сообщение при старте
     */
    private String getStartMessage() {
        return "👋 Привет! Я бот-викторина \"Сортировщик по порядку\" (Вариант 18).\n\n" +
               "📚 Суть игры:\n" +
               "Я предлагаю вам элементы, а вы должны расположить их в правильном порядке.\n\n" +
               "🎯 Примеры заданий:\n" +
               "• Планеты по удалённости от Солнца\n" +
               "• Материки по площади\n" +
               "• Цвета радуги\n" +
               "• Ноты музыкальной гаммы\n" +
               "• И другие!\n\n" +
               "📋 Команды:\n" +
               "/quiz - начать новый раунд\n" +
               "/stats - ваша статистика\n" +
               "/stop - остановить игру\n" +
               "/help - подробная справка\n\n" +
               "Напишите /quiz чтобы начать!";
    }

    /**
     * Справка по игре
     */
    private String getHelpMessage() {
        return "📖 Как играть:\n\n" +
               "1️⃣ Начните игру командой /quiz\n" +
               "2️⃣ Бот покажет список элементов в случайном порядке\n" +
               "3️⃣ Напишите правильный порядок через запятую\n" +
               "   Пример: Меркурий, Венера, Земля, Марс, Юпитер\n" +
               "4️⃣ Бот проверит ваш ответ и начислит баллы\n\n" +
               "💡 Советы:\n" +
               "• Можно писать с большой или маленькой буквы\n" +
               "• Между названиями ставьте запятую\n" +
               "• Порядок важен!\n\n" +
               "📊 Статистика доступна по команде /stats\n" +
               "🛑 Остановить игру: /stop\n\n" +
               "🎲 Доступно категорий: " + QuizDataLoader.getTaskCount();
    }

    /**
     * Начало викторины
     */
    private String startQuiz(long chatId) {
        UserSession session = sessionManager.getSession(chatId);

        // Если уже есть активная игра
        if (session.isGameInProgress()) {
            return "⏸ Игра уже идёт! Завершите текущий раунд или напишите /stop для отмены.";
        }

        // Получаем случайное задание
        SortTask task = QuizDataLoader.getRandomTask();
        if (task == null) {
            return "❌ Ошибка: нет доступных заданий.";
        }

        // Сохраняем задание в сессии
        session.setCurrentTask(task);
        session.setGameInProgress(true);

        return "🎲 Новое задание!\n\n" +
               "📁 Категория: *" + task.getCategory() + "*\n\n" +
               task.getDescription() + "\n\n" +
               "🔀 Элементы:\n" +
               task.getItemsAsString() + "\n\n" +
               "📝 Напишите правильный порядок через запятую.\n" +
               "Пример: А, Б, В, Г";
    }

    /**
     * Проверка ответа пользователя
     */
    private String checkUserAnswer(long chatId, String userAnswer) {
        UserSession session = sessionManager.getSession(chatId);
        SortTask task = session.getCurrentTask();

        if (task == null) {
            session.setGameInProgress(false);
            return "❌ Ошибка: задание не найдено. Начните заново: /quiz";
        }

        // Увеличиваем счётчик вопросов
        session.incrementTotal();

        // Проверяем ответ
        boolean isCorrect = task.checkAnswer(userAnswer);

        if (isCorrect) {
            session.incrementCorrect();
            session.setGameInProgress(false);
            return "✅ Верно!\n\n" +
                   "Правильный порядок:\n" +
                   task.getCorrectOrderAsString() + "\n\n" +
                   "📊 Ваша статистика: " + 
                   session.getCorrectAnswers() + "/" + session.getTotalQuestions() +
                   " (" + String.format("%.1f", session.getAccuracy()) + "%)\n\n" +
                   "Хотите ещё? Пишите /quiz";
        } else {
            session.setGameInProgress(false);
            return "❌ Неверно!\n\n" +
                   "Правильный ответ:\n" +
                   "*" + task.getCorrectOrderAsString() + "*\n\n" +
                   "Ваш ответ: " + userAnswer + "\n\n" +
                   "Попробуйте ещё раз: /quiz";
        }
    }

    /**
     * Остановка викторины
     */
    private String stopQuiz(long chatId) {
        UserSession session = sessionManager.getSession(chatId);
        
        if (!session.isGameInProgress()) {
            return "ℹ️ У вас нет активной игры.";
        }

        session.setGameInProgress(false);
        session.setCurrentTask(null);

        return "🛑 Игра остановлена.\n" +
               "Текущая статистика: " + 
               session.getCorrectAnswers() + "/" + session.getTotalQuestions() +
               " (" + String.format("%.1f", session.getAccuracy()) + "%)\n\n" +
               "Напишите /quiz чтобы начать заново.";
    }

    /**
     * Статистика игрока
     */
    private String getStats(long chatId) {
        UserSession session = sessionManager.getSession(chatId);

        if (session.getTotalQuestions() == 0) {
            return "📊 Статистика пуста.\n" +
                   "Начните игру: /quiz";
        }

        String status = session.isGameInProgress() ? "🟢 В игре" : "⚪ Не активен";

        return "📊 Ваша статистика:\n\n" +
               "✅ Правильных ответов: " + session.getCorrectAnswers() + "\n" +
               "📝 Всего вопросов: " + session.getTotalQuestions() + "\n" +
               "🎯 Точность: " + String.format("%.1f", session.getAccuracy()) + "%\n" +
               "📈 Статус: " + status;
    }

    /**
     * Отправка текстового сообщения
     */
    private void sendText(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode("Markdown")
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
