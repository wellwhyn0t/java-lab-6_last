package com.student.lab6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for Laboratory Work 6, Variant 18.
 * This application demonstrates JavaFX navigation between multiple screens.
 */
public class Lab6Application extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Lab6Application.class.getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), 450, 350);
        
        primaryStage.setTitle("Lab 6 - Variant 18: Student Navigator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
