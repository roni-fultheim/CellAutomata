package reversiap;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenuController implements Initializable {
    @FXML
    private Button startGameBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button exitBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.exitBtn.setOnAction((event) -> {
            System.exit(0);
        });
    }

    @FXML
    public void changeSettings() {
        Settings set = new Settings();
        set.display();
    }

    @FXML
    public void startGame() {
        try {
            // preparing the game screen
            HBox root = (HBox) FXMLLoader.load(this.getClass().getResource("ReversiGame.fxml"));
            Scene scene = new Scene(root, 650, 500);
            scene.getStylesheets().add(this.getClass().getResource("reversiap.css").toExternalForm());
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Reversi Game");
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage s = (Stage) this.exitBtn.getScene().getWindow();
            s.close();
        } catch (Exception ex) {
            System.out.println("startGame error:");
            ex.printStackTrace();
        }
    }

}