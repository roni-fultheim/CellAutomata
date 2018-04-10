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
        this.extraMatrix = new CellType[b.getSize()][b.getSize()];

        // initialize empty matrix
        int size = this.board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.extraMatrix[i][j] = CellType.EMPTY;
            }
        }

        // update starting status (using method)
        this.updateMeasures();
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
                } else {
                    // this is a tree cell
                    if (this.board.isNeighborOnFire(i, j)) {
                        // rule#3: a tree cell catches fire from a neighbor in a given probability
                        if (this.rand.caughtFireFromNeighbor()) {
                            this.extraMatrix[i][j] = CellType.FIRE;
                        }
                    } else if (this.rand.caughtFireRandomly()) {
                        // rule#4: a tree cell catches fire randomly in a given probability
                        this.extraMatrix[i][j] = CellType.FIRE;
                    }
                }
            }
        }

        // copy to real board
        this.board.setMatrix(this.extraMatrix);

        // update measures
        this.updateMeasures();
    }

    /**
     * Updates and prints to console the local and global measures of the game
     */
    private void updateMeasures() {
        // global measure - count tree cells, count empty cells, divide
        int trees = this.board.countColor(CellType.TREE, 1, 1, (this.board.getSize() - 1));
        int empty = this.board.countColor(CellType.EMPTY, 1, 1, (this.board.getSize() - 1));

        double global = trees / empty;

        // local measure - count each 10*10 square (not overlapping) that has a majority of trees/empty cells
        int local = 0;
        for (int i = 1; i < (this.board.getSize() - 1); i = i + 10) {
            for (int j = 1; j < (this.board.getSize() - 1); j = j + 10) {
                // count submatices of 10*10 which have 2/3 of trees/empty cells
                if (this.hasStateMajority(i, j, 10)) {
                    local++;
                }
            }
        }

        // print measures
        System.out.println(global + "\t" + local);

        // update listeners
        this.listener.changeGlobalMeasure(global);
        this.listener.changeLocalMeasure(local);
    }

    /**
     * Checks if the given square has a majority of cells in one of two states: tree of empty
     * @param r starting row
     * @param c starting column
     * @param limit width of square to check
     * @return true if square has a majority of either trees or empty cells
     */
    private boolean hasStateMajority(int r, int c, int limit) {
        int majority = limit * limit * 2 / 3; // majority is 2/3 of the cells
        // count trees
        int cells = this.board.countColor(CellType.TREE, r, c, limit);

        // if the tree cells are majority - return true
        if (cells > majority) {
            return true;
        }

        // count empty cells
        cells = this.board.countColor(CellType.EMPTY, r, c, limit);
        // if the empty cells are majority - return true
        if (cells > majority) {
            return true;
        }

        // else - none are the majority - reutrn false
        return false;

    }
}