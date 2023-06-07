package com.example.forma_new;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FormaApplication extends Application {

@Override
public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(FormaApplication.class.getResource("Forma_GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 596, 694);

        stage.setTitle("FORMA 2.0");
        stage.setScene(scene);
        stage.show();

        }

public static void main(String[] args) throws IOException {
        launch();
        }

}