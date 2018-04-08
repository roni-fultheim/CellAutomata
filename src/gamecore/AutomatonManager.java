package gamecore;

import reversiap.Listener;
import reversiap.Randomizer;

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
public class AutomatonManager {
    // board of automaton
    private Board board;

    // extra board to do changes on
    private CellType[][] extraMatrix;

    // randomizer containing non-deterministic methods to make decisions by
    private Randomizer rand;

    // listener to show local and global measurements
    private Listener listener;

    /**
     * Constructor taking a board on which to play game, two players, and the logic of the moves.
     * @param b board of game
     * @param r randomizer with the wanted probabilities to make decisions by
     * @param list listener of game
     */
    public AutomatonManager(Board b, Randomizer r, Listener list) {
        this.board = b;
        this.rand = r;
        this.listener = list;

        // initialize empty matrix
        int size = this.board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.extraMatrix[i][j] = CellType.EMPTY;
            }
        }

        // TODO update starting status (using method)
    }

    public void playRound() {

        // go over middle of board (without edge rows+columns)
        int size = this.board.getSize() - 1;
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                // rule#1: a burning cell turns into an empty one
                if (this.board.isCellOnFire(i, j)) {
                    this.extraMatrix[i][j] = CellType.EMPTY;
                } else if (this.board.isCellEmpty(i, j) && this.rand.grewTree()) {
                    // rule#2: an empty cell grows a tree in a given probability
                    this.extraMatrix[i][j] = CellType.TREE;
                } // TODO
            }
        }

        // TODO - edge row+columns
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
        this.listener.changeXPlayerScore(this.board.countColor(CellType.FIRE));
        this.listener.changeOPlayerScore(this.board.countColor(CellType.TREE));

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

    private void updateMeasures() {
        // TODO - count tree cells, count empty cells, divide
        // TODO - count submatices of 10*10 which have 2/3 of trees/empty cells
    }
}