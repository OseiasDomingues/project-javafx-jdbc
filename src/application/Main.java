package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/MainView.fxml"));
            ScrollPane scrollPane = fxmlLoader.load();

            //Força os nodes ficarem do tamanho do scrollPane
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Java FX Application");
            primaryStage.show();

        } catch (IOException e) {
            e.getMessage();
        }
    }
}
