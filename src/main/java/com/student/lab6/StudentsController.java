package com.student.lab6;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controller for the student list view.
 * Displays a table of students and allows navigation to add/edit forms.
 * Variant 18: Students with fields - ID, Name, Group, Rating, Date of Birth
 */
public class StudentsController implements Initializable {
    
    @FXML
    private TableView<Student> studentsTable;
    
    @FXML
    private TableColumn<Student, Integer> idColumn;
    
    @FXML
    private TableColumn<Student, String> nameColumn;
    
    @FXML
    private TableColumn<Student, String> groupColumn;
    
    @FXML
    private TableColumn<Student, Double> ratingColumn;
    
    @FXML
    private TableColumn<Student, LocalDate> birthDateColumn;
    
    @FXML
    private Label statusLabel;
    
    private ObservableList<Student> studentData;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        
        // Load sample data
        loadStudentData();
    }
    
    private void loadStudentData() {
        studentData = FXCollections.observableArrayList(
            new Student(1, "Иванов Алексей", "ПИ-201", 4.5, LocalDate.of(2002, 5, 15)),
            new Student(2, "Петрова Мария", "ПИ-202", 4.8, LocalDate.of(2001, 8, 22)),
            new Student(3, "Сидоров Дмитрий", "ПИ-201", 3.9, LocalDate.of(2002, 12, 3)),
            new Student(4, "Козлова Анна", "ПИ-203", 4.2, LocalDate.of(2001, 3, 10)),
            new Student(5, "Новиков Сергей", "ПИ-202", 4.6, LocalDate.of(2002, 7, 28))
        );
        studentsTable.setItems(studentData);
        updateStatus("Loaded " + studentData.size() + " students");
    }
    
    @FXML
    public void onAddButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student-form-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 450);
            
            // Pass controller reference for adding new student
            StudentFormController controller = loader.getController();
            controller.setStudentsController(this);
            controller.setEditMode(false);
            
            Stage stage = new Stage();
            stage.setTitle("Add New Student");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            updateStatus("Error opening add form: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    public void onEditButton() {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            updateStatus("Please select a student to edit");
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student-form-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 450);
            
            StudentFormController controller = loader.getController();
            controller.setStudentsController(this);
            controller.setEditMode(true);
            controller.fillFormData(selected);
            
            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            updateStatus("Error opening edit form: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    public void onDeleteButton() {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            updateStatus("Please select a student to delete");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Student");
        alert.setContentText("Are you sure you want to delete " + selected.getName() + "?");
        
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            studentData.remove(selected);
            updateStatus("Deleted: " + selected.getName());
        }
    }
    
    @FXML
    public void onBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Scene scene = new Scene(loader.load(), 450, 350);
            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setTitle("Lab 6 - Variant 18: Student Navigator");
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (IOException e) {
            updateStatus("Error navigating back: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    public void onRefreshButton() {
        loadStudentData();
    }
    
    public void addStudent(Student student) {
        studentData.add(student);
        updateStatus("Added: " + student.getName());
    }
    
    public void updateStudent(Student updatedStudent) {
        int index = studentData.indexOf(studentsTable.getSelectionModel().getSelectedItem());
        if (index >= 0) {
            studentData.set(index, updatedStudent);
            updateStatus("Updated: " + updatedStudent.getName());
        }
    }
    
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
}
