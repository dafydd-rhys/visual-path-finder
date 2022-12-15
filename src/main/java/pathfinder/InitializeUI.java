package pathfinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class InitializeUI extends Application {

    private final int[] UI_DIMENSIONS = {1920, 1080};

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(InitializeUI.class.getResource("fxml/UI.fxml"));

        stage.setFullScreenExitHint("");
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setScene(new Scene(loader.load(), UI_DIMENSIONS[0], UI_DIMENSIONS[1]));
        stage.centerOnScreen();
        stage.setOnCloseRequest(e -> System.exit(1));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}