package gamecore;

import java.util.ArrayList;
import java.util.List;

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
    private ElementInBoard[][] gameBoard_;

    /**
     * Size of board (length/width)
     */
    private int size_;

    private List<Position> emptyCells_;

    public Board(int size) {
        this.size_ = size;
        this.gameBoard_ = new ElementInBoard[size][size];
        this.emptyCells_ = new ArrayList<Position>();
        Position p;
        // initialize empty squares
        for (int i = 0; i < this.size_; i++) {
            for (int j = 0; j < this.size_; j++) {
                // a cell will be empty by default
                this.gameBoard_[i][j] = ElementInBoard.EMPTY;
                // adding cell to list of empty cells
                p = new Position(i, j);
                this.emptyCells_.add(p);
            }
        }

        p = new Position(this.size_ / 2 - 1, this.size_ / 2 - 1);
        this.makeInColor(ElementInBoard.FIRE, p.getRow(), p.getColumn());
        p = new Position(this.size_ / 2, this.size_ / 2);
        this.makeInColor(ElementInBoard.FIRE, p.getRow(), p.getColumn());

        // initialize black squares
        p = new Position(this.size_ / 2 - 1, this.size_ / 2);
        this.makeInColor(ElementInBoard.TREE, p.getRow(), p.getColumn());
        p = new Position(this.size_ / 2, this.size_ / 2 - 1);
        this.makeInColor(ElementInBoard.TREE, p.getRow(), p.getColumn());
    }

    public Board(Board b) {
        // copy all private elements of given board
        this.size_ = b.size_;
        this.gameBoard_ = new ElementInBoard[this.size_][this.size_];
        for (int i = 0; i < this.size_; i++) {
            for (int j = 0; j < this.size_; j++) {
                this.gameBoard_[i][j] = b.gameBoard_[i][j];
            }
        }
        this.emptyCells_ = new ArrayList<Position>(b.emptyCells_);
    }

    public ElementInBoard[][] getBoard() {
        return this.gameBoard_;
    }

    public int getSize() {
        return this.size_;
    }

    public boolean isCellTree(int row, int col) {
        Position pos = new Position(row, col);
        return this.getCell(pos) == ElementInBoard.TREE;
    }

    public boolean isCellOnFire(int row, int col) {
        Position pos = new Position(row, col);
        return this.getCell(pos) == ElementInBoard.FIRE;
    }

    public boolean isCellEmpty(int row, int col) {
        Position pos = new Position(row, col);
        return this.getCell(pos) == ElementInBoard.EMPTY;
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

    public boolean compareCellColors(ElementInBoard c, Position loc) {
        return (c != ElementInBoard.EMPTY && this.getCell(loc) == c);
    }

    /**
     * Flips the color at the given position, if non-empty.
     * If color is black cell will become white, and vice versa.
     * @param p position to flip
     */
    public void flipColor(Position p) {
        // check that cell is not empty
        if (!this.isCellEmpty(p.getRow(), p.getColumn())) {
            // if cell is black
            if (this.compareCellColors(ElementInBoard.BLACK, p)) {
                // flip to white
                this.gameBoard_[p.getRow()][p.getColumn()] = ElementInBoard.WHITE;
            } else {
                // else cell is white - flip to black
                this.gameBoard_[p.getRow()][p.getColumn()] = ElementInBoard.BLACK;
            }
        }
    }

    public void makeInColor(ElementInBoard c, int row, int col) {
        this.gameBoard_[row][col] = c;
        // remove from list of empty cells
        this.emptyCells_.remove(new Position(row, col));
    }

    public void flipColorInRange(Position prevStart, Position end, int rowJumps, int colJumps) {
        // make sure both prevStart and end are in range of board
        if (!this.isInBoardRange(prevStart) || !this.isInBoardRange(end)) {
            return;
        }

        // avoid infinite loop in case of no jumps
        if (rowJumps == 0 && colJumps == 0) {
            return;
        }

        // iterate over board and change the location:
        // initialize start of range
        Position curr = new Position(prevStart.getRow(), prevStart.getColumn());

        // if end location cannot be reached by given jumps - there is no way to know when to stop
        // check if row/column jumps are 0 before using the % operation (undefined for 0)
        if (((rowJumps != 0) && ((end.getRow() - curr.getRow()) % rowJumps != 0))
                || ((colJumps) != 0 && ((end.getColumn() - curr.getColumn()) % colJumps != 0))) {
            // return to avoid infinite loop
            return;
        }

        // while location is still in given range - has not yet reached end
        while (curr != end) {
            // first move to next square (started with square previous to start)
            curr.increment(rowJumps, colJumps);
            // then flip next square
            ElementInBoard e = this.getCell(curr);
            if (e == ElementInBoard.WHITE) {
                this.gameBoard_[curr.getRow()][curr.getColumn()] = ElementInBoard.BLACK;
            } else if (e == ElementInBoard.BLACK) {
                this.gameBoard_[curr.getRow()][curr.getColumn()] = ElementInBoard.WHITE;
            }
        }
    }

    public boolean isInBoardRange(Position loc) {
        // check that row and column values are between 0 and board size-1
        return (loc.getRow() >= 0 && loc.getColumn() >= 0 && loc.getRow() < this.size_ && loc.getColumn() < this.size_);
    }

    public boolean isEdge(Position loc) {
        return (loc.getRow() == 0 || loc.getRow() == (this.size_ - 1) || loc.getColumn() == 0
                || loc.getColumn() == (this.size_ - 1));
    }

    public boolean isBoardFull() {
        // if list is empty - board is full
        return this.emptyCells_.isEmpty();
    }

    public List<Position> emptyCells() {
        return this.emptyCells_;
    }

    public ElementInBoard getCell(Position loc) {
        return this.gameBoard_[loc.getRow()][loc.getColumn()];
    }

    public void removeCellFromEmptyList(Position removalPoint) {
        this.emptyCells_.remove(removalPoint);
    }

    /**
     * Counts the number of cells with the given color on the given board.
     *
     * @param b full Board to count cells of
     * @param c color of cells to count
     * @return number of cells with given color
     */
    public int countColor(ElementInBoard c) {
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