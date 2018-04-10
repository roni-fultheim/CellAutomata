package reversiap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class of program
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // load root from fxml
            VBox root = (VBox) FXMLLoader.load(this.getClass().getResource("menu.fxml"));
            // create scene
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(this.getClass().getResource("reversiap.css").toExternalForm());
            // set stage to use scene
            primaryStage.setScene(scene);
            primaryStage.setTitle("Welcome to Cellular Automaton");
            // show stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}