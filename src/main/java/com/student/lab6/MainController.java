package com.student.lab6;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller for the main view (login screen).
 * Handles user authentication and navigation to the student list.
 */
public class MainController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label messageLabel;
    
    // Simple hardcoded credentials for demonstration
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "12345";
    
    @FXML
    public void onLoginButton() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Введите логин и пароль", true);
            return;
        }
        
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            navigateToStudentList();
        } else {
            showMessage("Неверные данные! Попробуйте: admin / 12345", true);
        }
    }
    
    @FXML
    public void onClearButton() {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
        usernameField.requestFocus();
    }
    
    @FXML
    public void onExitButton() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
    
    private void showMessage(String text, boolean isError) {
        messageLabel.setText(text);
        messageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
    
    private void navigateToStudentList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("students-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 500);
            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setTitle("Список студентов - Вариант 18");
            stage.setScene(scene);
            stage.setResizable(true);
        } catch (IOException e) {
            showMessage("Ошибка загрузки списка: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }
}
