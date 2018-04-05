package reversiap;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
    public void display(String title, String message) {
        // create new stage
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);

        window.setMinWidth(250);
        window.setMinHeight(150);

        Label label = new Label();
        label.setText(message);

        Button close = new Button("Return to menu");
        close.setOnAction(EventHandler -> window.close());

        // create new scene, add label and button and set to center of screen
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(this.getClass().getResource("reversiap.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
    }
}