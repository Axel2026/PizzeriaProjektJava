package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("StartingButtons.fxml"));
        primaryStage.setTitle("Na Okrągło");
        primaryStage.setScene(new Scene(root, 1280, 720));
        File file = new File("F:\\git_reps\\PizzeriaProjektJava\\src\\sample\\style.css");
        root.getStylesheets().clear();
        root.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
