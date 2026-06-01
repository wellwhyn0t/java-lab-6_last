package com.student.lab6;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controller for the student form view.
 * Handles adding and editing student records.
 */
public class StudentFormController {
    
    @FXML
    private TextField idField;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField groupField;
    
    @FXML
    private TextField ratingField;
    
    @FXML
    private DatePicker birthDatePicker;
    
    @FXML
    private Label messageLabel;
    
    private StudentsController studentsController;
    private boolean isEditMode = false;
    private Student currentStudent;
    
    public void setStudentsController(StudentsController controller) {
        this.studentsController = controller;
    }
    
    public void setEditMode(boolean editMode) {
        this.isEditMode = editMode;
        if (editMode) {
            idField.setEditable(false);
        }
    }
    
    public void fillFormData(Student student) {
        this.currentStudent = student;
        idField.setText(String.valueOf(student.getId()));
        nameField.setText(student.getName());
        groupField.setText(student.getGroup());
        ratingField.setText(String.valueOf(student.getRating()));
        birthDatePicker.setValue(student.getBirthDate());
    }
    
    @FXML
    public void onSaveButton() {
        if (!validateInput()) {
            return;
        }
        
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String group = groupField.getText().trim();
            double rating = Double.parseDouble(ratingField.getText().trim());
            LocalDate birthDate = birthDatePicker.getValue();
            
            if (isEditMode && currentStudent != null) {
                Student updated = new Student(id, name, group, rating, birthDate);
                studentsController.updateStudent(updated);
                showMessage("Студент успешно обновлен", false);
            } else {
                Student newStudent = new Student(id, name, group, rating, birthDate);
                studentsController.addStudent(newStudent);
                showMessage("Студент успешно добавлен", false);
            }
            
            closeWindow();
        } catch (NumberFormatException e) {
            showMessage("Неверный формат числа", true);
        } catch (Exception e) {
            showMessage("Ошибка: " + e.getMessage(), true);
        }
    }
    
    @FXML
    public void onCancelButton() {
        closeWindow();
    }
    
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            showMessage("ФИО обязательно для заполнения", true);
            return false;
        }
        
        if (groupField.getText().trim().isEmpty()) {
            showMessage("Группа обязательна для заполнения", true);
            return false;
        }
        
        if (ratingField.getText().trim().isEmpty()) {
            showMessage("Рейтинг обязателен для заполнения", true);
            return false;
        }
        
        try {
            double rating = Double.parseDouble(ratingField.getText().trim());
            if (rating < 0 || rating > 5) {
                showMessage("Рейтинг должен быть от 0 до 5", true);
                return false;
            }
        } catch (NumberFormatException e) {
            showMessage("Неверное значение рейтинга", true);
            return false;
        }
        
        if (birthDatePicker.getValue() == null) {
            showMessage("Дата рождения обязательна", true);
            return false;
        }
        
        if (!isEditMode && idField.getText().trim().isEmpty()) {
            showMessage("ID обязателен для заполнения", true);
            return false;
        }
        
        return true;
    }
    
    private void showMessage(String text, boolean isError) {
        messageLabel.setText(text);
        messageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
    
    private void closeWindow() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}
