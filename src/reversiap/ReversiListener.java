package reversiap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Listens to the game, and observes the changes. Holds the lables that should be changed
 */
public class ReversiListener {
    @FXML
    private Label currPlayerText;

    @FXML
    private Label whitePlayerScoreText;

    @FXML
    private Label blackPlayerScoreText;

    @FXML
    private Label messages;

    private String player1;

    private String player2;

    /**
     * Creates listener with given labels
     * @param currPlayerText label of current player
     * @param whitePlayerScoreText label of "X" player's score
     * @param blackPlayerScoreText label of "O" player's score
     */
    public ReversiListener(String player1, String player2, Label currPlayerText, Label whitePlayerScoreText,
            Label blackPlayerScoreText, Label m) {
        this.currPlayerText = currPlayerText;
        this.whitePlayerScoreText = whitePlayerScoreText;
        this.blackPlayerScoreText = blackPlayerScoreText;
        this.messages = m;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Updates the message to the player. For showing moves, invalid move, etc.
     * @param m message to show
     */
    public void updateMessage(String m) {
        this.messages.setText(m);
    }

    /**
     * Updates current playing player
     * @param player name
     */
    public void changeCurrentPlayer(String player) {
        this.currPlayerText.setText("Current player: " + player);
    }

    /**
     * Updates score of O (second) player
     * @param score new score
     */
    public void changeOPlayerScore(int score) {
        this.whitePlayerScoreText.setText(this.player2 + " player score: " + Integer.toString(score));
    }

    /**
     * Updates score of X (first) player
     * @param score new score
     */
    public void changeXPlayerScore(int score) {
        this.blackPlayerScoreText.setText(this.player1 + " player score: " + Integer.toString(score));
    }

}