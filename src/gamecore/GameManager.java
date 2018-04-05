package gamecore;

import reversiap.ReversiListener;

/**
 * Yael Hacmon, ID 313597897
 * Roni Fultheim, ID 313465965
 */

/**
 * Controls the game flow of a Reversi game when given a board, two players
 * and an object to control the logic of every move.
 *
 * Is responsible for playing the turns, knowing and notifying when the game ends, and printing
 * messages to the players.
 */
public class GameManager {

    // board of game
    private Board board;

    // Starting player is black by default.
    private HumanPlayer currPlayer;

    // Opposite player is black by default.
    private HumanPlayer oppPlayer;

    private StandardMoveLogic logic;

    private ReversiListener listener;

    /**
     * Constructor taking a board on which to play game, two players, and the logic of the moves.
     * @param b board of game
     * @param black black player
     * @param white white player
     * @param log logic to handle moves
     * @param list listener of game
     */
    public GameManager(Board b, HumanPlayer black, HumanPlayer white, StandardMoveLogic log, ReversiListener list) {
        this.board = b;
        this.currPlayer = black;
        this.oppPlayer = white;
        this.logic = log;
        this.listener = list;

        this.logic.updateMoveOptions(this.currPlayer, this.board);
        // show options
        this.listener.updateMessage("Possible moves: " + this.currPlayer.getPossibleMoves().toString());
    }

    /**
     * Plays one turn of the game, while keeping track of game over, current player, etc.
     * @param move move to play
     * @return true if game continues, false otherwise
     */
    public boolean playTurn(Position move) {
        // check that move is allowed, if not return from method
        // while move isn't legal - get another move from player
        if (!this.logic.isMoveAllowed(move, this.currPlayer)) {
            this.listener.updateMessage(
                    "Illegal move, try again.\nPossible moves: " + this.currPlayer.getPossibleMoves().toString());
            return true;
        }

        // call logic to play move
        this.logic.playMove(move, this.currPlayer, this.board, this.oppPlayer);

        // show current scores
        this.listener.changeXPlayerScore(this.board.countColor(ElementInBoard.FIRE));
        this.listener.changeOPlayerScore(this.board.countColor(ElementInBoard.TREE));

        // make sure game continues
        if (this.board.isBoardFull()) {
            return false;
        }

        // SETUP FOR NEXT MOVE
        // update moves opposite player
        this.logic.updateMoveOptions(this.oppPlayer, this.board);

        // if opponent has possible moves
        if (this.oppPlayer.hasPossibleMoves()) {
            // switch players
            HumanPlayer temp = this.currPlayer;
            this.currPlayer = this.oppPlayer;
            this.oppPlayer = temp;

            // show options
            this.listener.updateMessage("Possible moves: " + this.currPlayer.getPossibleMoves().toString());

            // show name of playing player
            this.listener.changeCurrentPlayer(this.currPlayer.getName());
        } else {
            // update moves of current player
            this.logic.updateMoveOptions(this.currPlayer, this.board);

            // if current player has no moves - game is over
            if (!this.currPlayer.hasPossibleMoves()) {
                return false;
            }

            // else - let player know he has no possible moves
            this.listener
                    .updateMessage(this.oppPlayer.getName() + " has no possible moves, skipped turn.\nPossible moves: "
                            + this.currPlayer.getPossibleMoves().toString());
        }

        // method successful
        return true;
    }

    /**
     * Return string with message of game winner
     * @return message with winner, or tie message
     */
    public String winner() {
        int player1 = this.board.countColor(this.currPlayer.getColor());
        int player2 = this.board.countColor(this.oppPlayer.getColor());

        if (player1 > player2) {
            return ("The winner is " + this.currPlayer.getName());
        } else if (player1 < player2) {
            return ("The winner is " + this.oppPlayer.getName());
        } else {
            // tie
            return ("We have a tie");
        }
    }
}