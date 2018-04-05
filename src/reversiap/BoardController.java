package reversiap;

import java.io.IOException;
import gamecore.Board;
import gamecore.GameManager;
import gamecore.Position;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BoardController extends GridPane {
    private Board board;

    private Color xColor;

    private Color oColor;

    private GameManager manager;

    /**
     * C'tor of board controller, taking colors of playes, game manager to manage game and board to play on
     * @param b board to play on
     * @param pathX color of player 1
     * @param pathO color of player 2
     * @param gm game manager to handle game
     */
    public BoardController(Board b, String pathX, String pathO, GameManager gm) {
        this.board = b;
        this.xColor = this.parse(pathX);
        this.oColor = this.parse(pathO);
        this.manager = gm;

        this.setOnMouseClicked(event -> this.calcMouseClick(event));
    }

    public void draw() {
        int height = (int) this.getPrefHeight();
        int width = (int) this.getPrefWidth();
        int cellHeight = height / this.board.getSize();
        int cellWidth = width / this.board.getSize();
        // draw the cells of the board
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int j = 0; j < this.board.getSize(); j++) {
                Rectangle rect = new Rectangle(cellWidth, cellHeight);
                if (this.board.isCellOnFire(i, j)) {
                    rect.setFill(Color.ORANGE);
                } else if (this.board.isCellTree(i, j)) {
                    rect.setFill(Color.GREEN);
                } else {
                    // cell is empty
                    rect.setFill(Color.BISQUE);
                }
                rect.setStroke(Color.BLACK);

                this.add(rect, j, i);
            }
        }

        /*
         * // draw circle, if cell is colored for (int i = 0; i < this.board.getSize(); i++) { for (int j = 0; j <
         * this.board.getSize(); j++) { if (this.board.isCellBlack(i, j)) { Ellipse e = new Ellipse(cellWidth / 2,
         * cellHeight / 2); e.setFill(this.xColor); e.setEffect(new InnerShadow()); this.add(e, j, i); } else if
         * (this.board.isCellWhite(i, j)) { Ellipse e = new Ellipse(cellWidth / 2, cellHeight / 2);
         * e.setFill(this.oColor); e.setEffect(new InnerShadow()); this.add(e, j, i); } } }
         */
    }

    /**
     * Handles the event of a player clicking on the board.
     * @param event
     */
    public void calcMouseClick(MouseEvent event) {
        int height = (int) this.getPrefHeight();
        int width = (int) this.getPrefWidth();

        int cellHeight = height / this.board.getSize();
        int cellWidth = width / this.board.getSize();

        // calculate and cast to int
        int column = (int) event.getX() / cellWidth;
        int row = (int) event.getY() / cellHeight;

        // play turn
        boolean gameContinues = this.manager.playTurn(new Position(row, column));

        // draw board
        this.draw();

        if (!gameContinues) {
            Alert alert = new Alert();
            alert.display("Game over!", this.manager.winner());

            // return to main menu
            try {
                VBox root = (VBox) FXMLLoader.load(this.getClass().getResource("menu.fxml"));
                Scene scene = new Scene(root, 300, 350);
                scene.getStylesheets().add(this.getClass().getResource("reversiap.css").toExternalForm());
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Menu");
                primaryStage.setScene(scene);
                primaryStage.show();
                Stage s = (Stage) this.getScene().getWindow();
                s.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses the string from color name into color object
     * @param c sting with color name
     * @return color represnted
     */
    private Color parse(String c) {
        switch (c) {
            case "Black":
                return Color.BLACK;
            case "White":
                return Color.WHITE;
            case "Red":
                return Color.RED;
            case "Yellow":
                return Color.GOLD;
            case "Green":
                return Color.GREEN;
            case "Blue":
                return Color.BLUE;
            default:
                break;
        }
        return null;
    }
}