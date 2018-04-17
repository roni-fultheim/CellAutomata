package reversiap;

import gamecore.AutomatonManager;
import gamecore.Board;
import javafx.application.Platform;
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

        // set function to click
        // TODO this.setOnMouseClicked(event -> this.calcMouseClick(event));
    }

    /**
     * Draws board
     */
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
                // TODO rect.setStroke(Color.BLACK);

                this.add(rect, j, i);
            }
        }

        // show borders
        // this.setGridLinesVisible(true);

    }

    /**
     * Handles the event of a player clicking on the board.
     * @param event mouse click
     */
    public void calcMouseClick(MouseEvent event) {
        // run for 200 time units
        int counter = 0;
        while (counter < 200) {
            // play one round of the game
            this.manager.playRound();

            // draw the current automaton
            this.draw();

            // sleep for 0.5 seconds
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Plays the game
     */
    public void startAutomaton() {
        System.out.println("in startAutomaton");

        // run for 200 time units
        int counter = 0;
        while (counter < 200) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // draw the current automaton
                    BoardController.this.draw();
                }
            });

            // sleep for 0.5 seconds
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // play one round of the game
            this.manager.playRound();

            // increment counter
            counter++;
        }
    }
}