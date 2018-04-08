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

    public Board(Board b) {
        // copy all private elements of given board
        this.size_ = b.size_;
        this.gameBoard_ = new CellType[this.size_][this.size_];
        for (int i = 0; i < this.size_; i++) {
            for (int j = 0; j < this.size_; j++) {
                this.gameBoard_[i][j] = b.gameBoard_[i][j];
            }
        }
    }

    public CellType[][] getBoard() {
        return this.gameBoard_;
    }

    public int getSize() {
        return this.size_;
    }

    public boolean isCellTree(int row, int col) {
        Position pos = new Position(row, col);
        return this.getCell(pos) == CellType.TREE;
    }

    public boolean isCellOnFire(int row, int col) {
        Position pos = new Position(row, col);
        return this.getCell(pos) == CellType.FIRE;
    }

    public boolean isCellEmpty(int row, int col) {
        Position pos = new Position(row, col);
        return this.getCell(pos) == CellType.EMPTY;
    }

    /**
     * Checks if a given position is in board range.
     * @param p position to check
     * @return true if in range, false otherwise
     */
    public boolean inRange(Position p) {
        return ((p.getRow() >= 0) && (p.getColumn() >= 0) && (p.getColumn() < this.getSize())
                && (p.getRow() < this.getSize()));
    }

    public boolean compareCellColors(Position loc1, Position loc2) {
        return this.getCell(loc1) == this.getCell(loc2);
    }

    public boolean compareCellColors(CellType c, Position loc) {
        return (c != CellType.EMPTY && this.getCell(loc) == c);
    }

    public void makeInColor(CellType c, int row, int col) {
        this.gameBoard_[row][col] = c;
    }

    public boolean isInBoardRange(Position loc) {
        // check that row and column values are between 0 and board size-1
        return (loc.getRow() >= 0 && loc.getColumn() >= 0 && loc.getRow() < this.size_ && loc.getColumn() < this.size_);
    }

    /**
     * Checks if given coordinates are a cell at the board's edge
     * @param i
     * @param j
     * @return
     */
    public boolean isEdge(int i, int j) {
        return (i == 0 || i == (this.size_ - 1) || j == 0 || j == (this.size_ - 1));
    }

    public boolean isEdge(Position loc) {
        return (loc.getRow() == 0 || loc.getRow() == (this.size_ - 1) || loc.getColumn() == 0
                || loc.getColumn() == (this.size_ - 1));
    }

    public CellType getCell(Position loc) {
        return this.gameBoard_[loc.getRow()][loc.getColumn()];
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
     * Counts the number of cells with the given color on the given board.
     *
     * @param b full Board to count cells of
     * @param c color of cells to count
     * @return number of cells with given color
     */
    public int countColor(CellType c) {
        // initialize variables
        int counter = 0;

        // go over board and count c-colored squares
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                // if colors match
                if (this.getCell(new Position(i, j)) == c) {
                    // increment counter
                    counter++;
                }
            }
        }

        return counter;
    }
}