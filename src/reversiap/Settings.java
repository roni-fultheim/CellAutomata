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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class of automaton setting screen
 */
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
        Label p = new Label("p (tree creation prob.):");
        p.setTextFill(Color.BLACK);
        GridPane.setConstraints(p, 0, 0);
        Label g = new Label("g (catching fire from neighbor prob.):");
        g.setTextFill(Color.BLACK);
        GridPane.setConstraints(g, 0, 1);
        Label f = new Label("f (catching fire randomly prob.):");
        f.setTextFill(Color.BLACK);
        GridPane.setConstraints(f, 0, 2);
        Label d = new Label("d (start as tree prob.):");
        d.setTextFill(Color.BLACK);
        GridPane.setConstraints(d, 0, 3);

        // add choice box for the three
        TextField pProb = new TextField();
        GridPane.setConstraints(pProb, 1, 0);
        TextField gProb = new TextField();
        GridPane.setConstraints(gProb, 1, 1);
        TextField fProb = new TextField();
        GridPane.setConstraints(fProb, 1, 2);
        TextField dProb = new TextField();
        GridPane.setConstraints(dProb, 1, 3);

        // the button to confirm their choices
        Button confirm = new Button("Save");
        GridPane.setConstraints(confirm, 1, 10);
        confirm.setOnAction(e -> this.writeChoice(pProb, gProb, fProb, dProb, window));

        // set position of messages to user
        GridPane.setConstraints(this.message, 0, 8);
        this.message.setWrapText(true);

        layout.getChildren().addAll(f, g, d, p, pProb, gProb, fProb, dProb, confirm, this.message);
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
    private void writeChoice(TextField pProb, TextField gProb, TextField fProb, TextField dProb, Stage window) {
        Double p, g, f, d;

        try {
            // parse the probabilities
            p = Double.parseDouble(pProb.getText());
            g = Double.parseDouble(gProb.getText());
            f = Double.parseDouble(fProb.getText());
            d = Double.parseDouble(dProb.getText());
        } catch (Exception e) {
            this.message.setText("Numbers only!");
            return;
        }

        // make sure all values were filled and between 0 to 1
        if (!this.inRange(p) || !this.inRange(g) || !this.inRange(f) || !this.inRange(d)) {
            this.message.setText("Number range: 0-1");
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
            // write the probabilities
            writer.write(p + "\n");
            writer.write(g + "\n");
            writer.write(f + "\n");
            writer.write(d + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.close();
    }

    /**
     * Checks if a given number is between 1 and 0
     * @param num number given
     * @return true if number is between 0 and 1, false otherwise
     */
    boolean inRange(double num) {
        return (num >= 0 && num <= 1);
    }
}
