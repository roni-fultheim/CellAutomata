package reversiap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Settings {
    Label message;

    /**
     *  constructor
     */
    public Settings() {
        this.message = new Label();
    }

    /**
     * this method display the settings and allow
     * the players to chose their colors and the size of the board
     */
    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Settings");

        // set the layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(12);
        layout.setHgap(12);
        layout.setId("set");
        // adding labels
        Label colorFirst = new Label("First player color:");
        colorFirst.setTextFill(Color.BLACK);
        GridPane.setConstraints(colorFirst, 0, 0);
        Label colorSecond = new Label("Second player color:");
        colorSecond.setTextFill(Color.BLACK);
        GridPane.setConstraints(colorSecond, 0, 1);
        Label boardSize = new Label("Board size:");
        boardSize.setTextFill(Color.BLACK);
        GridPane.setConstraints(boardSize, 0, 2);

        // add choice box for the three
        ChoiceBox<String> firstPlayer = new ChoiceBox<>();
        GridPane.setConstraints(firstPlayer, 1, 0);
        ChoiceBox<String> secondPlayer = new ChoiceBox<>();
        GridPane.setConstraints(secondPlayer, 1, 1);
        ChoiceBox<String> size = new ChoiceBox<>();
        GridPane.setConstraints(size, 1, 2);

        // the players choose their colors and the size of the board
        firstPlayer.getItems().addAll("Black", "White", "Red", "Blue", "Green", "Yellow");
        secondPlayer.getItems().addAll("Black", "White", "Red", "Blue", "Green", "Yellow");
        size.getItems().addAll("4", "6", "8", "10", "12", "14", "16", "18", "20");

        // the button to confirm their choices
        Button confirm = new Button("Save");
        GridPane.setConstraints(confirm, 1, 10);
        confirm.setOnAction(e -> this.writeChoice(firstPlayer, secondPlayer, size, window));

        // set position of messages to user
        GridPane.setConstraints(this.message, 1, 8);

        layout.getChildren().addAll(colorFirst, colorSecond, boardSize, firstPlayer, secondPlayer, size, confirm,
                this.message);
        layout.setId("settings");
        Scene scene = new Scene(layout, 400, 320);
        scene.getStylesheets().addAll(this.getClass().getResource("reversiap.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();

    }

    /**
     * this method receive the choice of the players
     * and write the choices to the settings file
     * @param firstPlayer the color of the first player
     * @param secondPlayer the color of the second player
     * @param size the size of the board
     * @param window the window to draw on
     */
    private void writeChoice(ChoiceBox<String> firstPlayer, ChoiceBox<String> secondPlayer, ChoiceBox<String> size,
            Stage window) {
        String first = firstPlayer.getValue();
        String second = secondPlayer.getValue();
        String boardSize = size.getValue();

        // make sure all values were filled
        if ((first == null || second == null || boardSize == null)) {
            this.message.setText("Missing a field");
            return;
        }

        // make sure that colors are different
        if (first.equals(second)) {
            this.message.setText("Player colors must be different");
            this.message.setTextFill(Color.RED);
            return;
        }

        File f1 = new File("settings.txt");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(fos, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BufferedWriter writer = new BufferedWriter(osw);
        try {
            writer.write(first + "\n");
            writer.write(second + "\n");
            writer.write(boardSize + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.close();
    }
}
