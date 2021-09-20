package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Test");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
