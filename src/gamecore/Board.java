package gamecore;

import java.util.Random;

/**
 * Represents the board in the game. Can tell if is empty, can change the color of a given cell, can flip the
 * color of a cell, etc.
 * Uses an enum matrix to represent the colors - empty, black and white.
 */

/**
 * Yael Hacmon, ID 313597897
 * Roni Fultheim, ID 313465965
 */

public class Board {

    /**
     * Matrix of game board - enum representing state of each position
     */
    private CellType[][] gameBoard_;

    /**
     * Size of board (length/width)
     */
    private int size_;

    /**
     * C'tor taking size of board and tree starting probability
     * @param size of board
     * @param d probability of cell starting as a tree
     */
    public Board(int size, double d) {
        this.size_ = size;
        this.gameBoard_ = new CellType[size][size];

        // initialize board
        for (int i = 0; i < this.size_; i++) {
            for (int j = 0; j < this.size_; j++) {
                // randomly set to tree (probability of d) or empty (prob. 1-d)
                Random rand = new Random();
                double randomNum = rand.nextDouble(); // return number between 0 to 1
                // if number is smaller than or equal to d -> then make square a tree
                if (randomNum <= d) {
                    this.gameBoard_[i][j] = CellType.TREE;
                } else {
                    this.gameBoard_[i][j] = CellType.EMPTY;
                }
            }
        }
    }

    /**
     * returns size of board
     * @return board size
     */
    public int getSize() {
        return this.size_;
    }

    /**
     * Returns if given cell is a tree
     * @param row of cell
     * @param col of cell
     * @return true if tree, false otherwise
     */
    public boolean isCellTree(int row, int col) {
        return this.gameBoard_[row][col] == CellType.TREE;
    }

    /**
     * Returns if given cell is on fire
     * @param row of cell
     * @param col of cell
     * @return true if on fire, false otherwise
     */
    public boolean isCellOnFire(int row, int col) {
        return this.gameBoard_[row][col] == CellType.FIRE;
    }

    /**
     * Returns if given cell is empty
     * @param row of cell
     * @param col of cell
     * @return true if empty, false otherwise
     */
    public boolean isCellEmpty(int row, int col) {
        return this.gameBoard_[row][col] == CellType.EMPTY;
    }

    /**
     * Checks if given coordinates are a cell at the board's edge
     * @param i row
     * @param j column
     * @return true if a edge, false otherwise
     */
    public boolean isEdge(int i, int j) {
        return (i == 0 || i == (this.size_ - 1) || j == 0 || j == (this.size_ - 1));
    }

    /**
     * Setter for the matrix, so that we can work on one and change the other in every step.
     * Must do deep copy because of the Java referencing system
     * @param matrix to swap to
     */
    public void setMatrix(CellType[][] matrix) {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                // copy
                this.gameBoard_[i][j] = matrix[i][j];
            }
        }
    }

    /**
     * Checks if one of a given cell's neighbors is on fire
     * @param i row of cell
     * @param j column of cell
     * @return true if one of the neighbors is on fire, false if none
     */
    public boolean isNeighborOnFire(int i, int j) {
        // if edge - there is s probelm, return
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
    public int countColor(CellType type, int r, int c, int numCells) {
        // initialize variables
        int counter = 0;

        // go over board and count type-colored squares
        for (int i = r; i < r + numCells; i++) {
            for (int j = c; j < c + numCells; j++) {
                // if colors match
                if (this.gameBoard_[i][j] == type) {
                    // increment counter
                    counter++;
                }
            }
        }

        return counter;
    }
}