package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("ProgramPicker.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader programListLoader = new FXMLLoader();
        programListLoader.setLocation(GUI.class.getResource("ProgramPicker.fxml"));
        Parent programListRoot = programListLoader.load();
        Scene programListScene = new Scene(programListRoot, 500, 550);
        ControllerPicker programPickerController = programListLoader.getController();
        stage.setTitle("Select a program");
        stage.setScene(programListScene);
        stage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(GUI.class.getResource("ProgramExecutor.fxml"));
        Parent programExecutorRoot = programExecutorLoader.load();
        Scene programExecutorScene = new Scene(programExecutorRoot, 700, 500);
        ControllerExecutor programExecutorController = programExecutorLoader.getController();
        programPickerController.setControllerExecutor(programExecutorController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(programExecutorScene);
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}