package com.example.schoolmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Enseignement.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        String cssPath = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        stage.setTitle("School Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}