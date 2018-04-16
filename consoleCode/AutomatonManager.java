/**
 * Yael Hacmon, ID 313597897
 * Roni Fultheim, ID 313465965
 */

/**
 * Manager the automaton: contains the game rules.
 * Plays on a single matrix, is responsible for calculating measures updating the listener.
 */
public class AutomatonManager {
    // board of automaton
    private Cell[][] matrix;

    // size of matrix (must be square)
    private int size;

    // randomizer containing non-deterministic methods to make decisions by
    private Randomizer rand;

    // listener to follow local and global measurements
    private Listener listener;

    /**
     * Constructor taking the automaton matrix, randomizer for non-deterministic rules and listener to follow measures.
     * @param m square board of automaton
     * @param r randomizer with the wanted probabilities to make decisions by
     * @param list listener of game
     */
    public AutomatonManager(Cell[][] m, Randomizer r, Listener list) {
        this.matrix = m;
        this.size = m.length;
        this.rand = r;
        this.listener = list;

        // update starting status (using method)
        this.updateMeasures();
    }

    public void playRound() {
        // notice that the inner matrix (actual automaton) is indexes 1,..,(size-2) due to border + Java indexing
        int s = this.size - 1;

        // change matrix according to the automaton rules
        for (int i = 1; i < s; i++) {
            for (int j = 1; j < s; j++) {
                // rule#1: a burning cell turns into an empty one
                if (this.isCellOnFire(i, j)) {
                    this.matrix[i][j] = Cell.EMPTY;
                } else if (this.isCellEmpty(i, j) && this.rand.grewTree()) {
                    // rule#2: an empty cell grows a tree in a given probability
                    this.matrix[i][j] = Cell.TREE;
                } else {
                    // this is a tree cell
                    if (this.isNeighborOnFire(i, j)) {
                        // rule#3: a tree cell catches fire from a neighbor in a given probability
                        if (this.rand.caughtFireFromNeighbor()) {
                            this.matrix[i][j] = Cell.FIRE;
                        }
                    } else if (this.rand.caughtFireRandomly()) {
                        // rule#4: a tree cell catches fire randomly in a given probability
                        this.matrix[i][j] = Cell.FIRE;
                    }
                }
            }
        }

        // update measures
        this.updateMeasures();
    }

    /**
     * Updates and prints to console the local and global measures of the game
     */
    private void updateMeasures() {
        // global measure - count tree cells, count empty cells, divide
        // notice that the inner matrix (actual automaton) is (size-2)*(size-2) - we lose 2 columns + 2 rows
        int trees = this.countColor(Cell.TREE, 1, 1, (this.size - 1));
        int empty = this.countColor(Cell.EMPTY, 1, 1, (this.size - 1));

        // calculate global measure
        double global = trees / empty;

        // local measure - count each 10*10 square (not overlapping) that has a majority of trees/empty cells
        // notice that the inner matrix (actual automaton) is indexes 1,..,(size-2) due to border + Java indexing
        int local = 0;
        for (int i = 1; i < (this.size - 1); i = i + 10) {
            for (int j = 1; j < (this.size - 1); j = j + 10) {
                // count submatrices of 10*10 which have 2/3 of trees/empty cells
                if (this.hasStateMajority(i, j, 10)) {
                    local++;
                }
            }
        }

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
        int cells = this.countColor(Cell.TREE, r, c, limit);

        // if the tree cells are majority - return true
        if (cells > majority) {
            return true;
        }

        // count empty cells
        cells = this.countColor(Cell.EMPTY, r, c, limit);
        // if the empty cells are majority - return true
        if (cells > majority) {
            return true;
        }

        // else - none are the majority - return false
        return false;

    }

    /**
     * Checks if one of a given cell's neighbors is on fire
     * @param i row of cell
     * @param j column of cell
     * @return true if one of the neighbors is on fire, false if none
     */
    private boolean isNeighborOnFire(int i, int j) {
        // if edge - there is s problem, return
        if (this.isEdge(i, j)) {
            System.out.println("Problem: working on edge");
            return false;
        }

        // up
        if (this.isCellOnFire(i - 1, j)) {
            return true;
        }
        // down
        if (this.isCellOnFire(i + 1, j)) {
            return true;
        }
        // left
        if (this.isCellOnFire(i, j - 1)) {
            return true;
        }
        // right
        if (this.isCellOnFire(i, j + 1)) {
            return true;
        }

        // none are burning - return false
        return false;
    }

    /**
     * Counts the number of cells with the given color in the given range (always a square).
     * @param type of cells to count
     * @param r starting row
     * @param c starting column
     * @param numCells square width (how many cells to count to each direction)
     * @return number of cells with given color in wanted limits
     */
    private int countColor(Cell type, int r, int c, int numCells) {
        // initialize variables
        int counter = 0;
        int rLim = r + numCells;
        int cLim = c + numCells;

        // go over board and count type-colored squares
        for (int i = r; i < rLim; i++) {
            for (int j = c; j < cLim; j++) {
                // if colors match
                if (this.matrix[i][j] == type) {
                    // increment counter
                    counter++;
                }
            }
        }

        // return the number of counter cells
        return counter;
    }

    /**
     * Returns if given cell is a tree - beautifier
     * @param row of cell
     * @param col of cell
     * @return true if tree, false otherwise
     */
    public boolean isCellTree(int row, int col) {
        return this.matrix[row][col] == Cell.TREE;
    }

    /**
     * Returns if given cell is on fire - beautifier
     * @param row of cell
     * @param col of cell
     * @return true if on fire, false otherwise
     */
    public boolean isCellOnFire(int row, int col) {
        return this.matrix[row][col] == Cell.FIRE;
    }

    /**
     * Returns if given cell is empty - beautifier
     * @param row of cell
     * @param col of cell
     * @return true if empty, false otherwise
     */
    public boolean isCellEmpty(int row, int col) {
        return this.matrix[row][col] == Cell.EMPTY;
    }

    /**
     * Checks if given coordinates are a cell at the board's edge
     * @param i row
     * @param j column
     * @return true if a edge, false otherwise
     */
    public boolean isEdge(int i, int j) {
        return (i == 0 || i == (this.size - 1) || j == 0 || j == (this.size - 1));
    }
}