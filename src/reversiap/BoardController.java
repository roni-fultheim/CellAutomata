package reversiap;

import gamecore.AutomatonManager;
import gamecore.Board;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardController extends GridPane {
    private Board board;

    private AutomatonManager manager;

    /**
     * C'tor of board controller, taking cell automata manager to manage game and board to play on
     * @param b board to play on
     * @param am cellular automaton manager to handle game
     */
    public BoardController(Board b, AutomatonManager am) {
        this.board = b;
        this.manager = am;

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

    }

    /**
     * Handles the event of a player clicking on the board.
     * @param event
     */
    public void calcMouseClick(MouseEvent event) {
        // TODO - endless loop with game rules, then drawinng the board, then sleeping
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