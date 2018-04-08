package gamecore;

import java.util.ArrayList;

/**
 * Yael Hacmon, ID 313597897
 * Roni Fultheim, ID 313465965
 */

/**
 * Represent the standard move logic of a Reversi game.
 * A move is allowed only if it changes square colors, and square colors are changed if the new
 * move blocks a range of squares with the opposite color between move and a same-colored square.
 */
public class StandardMoveLogic {

    /**
     * Plays the given move for the given player on given board, according to the logic decided.
     * Changes must be made to board.
     * Derived from parent class, originally a pure virtual method.
     *
     * Plays a move by switching the color of all consequent same-colored squares from given move.
     *
     * @param move location of chosen move
     * @param player player playing move
     * @param board board on which move is played
     * @param opponent other player of game
     */
    public void playMove(Position move, Player player, Board board, Player oposite) {
        CellType c = player.getColor();
        // else - get list of last positions to flip in every direction
        ArrayList<Position> positions = this.checkDirections(board, move, c);

        // otherwise - flip in every direction
        this.flipRange(board, move, positions.get(0), 0, 1); // right
        this.flipRange(board, move, positions.get(1), 0, -1); // left
        this.flipRange(board, move, positions.get(2), -1, 0); // up
        this.flipRange(board, move, positions.get(3), 1, 0); // down
        this.flipRange(board, move, positions.get(4), -1, -1); // up-left
        this.flipRange(board, move, positions.get(5), -1, 1); // up-right
        this.flipRange(board, move, positions.get(6), 1, -1); // down-left
        this.flipRange(board, move, positions.get(7), 1, 1); // down-right

        // make cell in color
        board.makeInColor(c, move.getRow(), move.getColumn());
    }

    /**
     * Flips the color of a range of given cells.
     * @param b board played on
     * @param prevStart move made
     * @param end end of range - last cell to flip
     * @param rowJumps jumps of rows
     * @param colJumps jumps of columns
     */
    private void flipRange(Board b, Position prevStart, Position end, int rowJumps, int colJumps) {
        // intialize
        Position p = prevStart;
        // while we have not reached the end
        while (!p.equals(end)) {
            // increment p
            p = new Position(p.getRow() + rowJumps, p.getColumn() + colJumps);
            // flip p
            b.flipColor(p);
        }
    }

    /**
     * Checks if given player can currently play a turn.
     * A player can play if the list of player's next possible moves is not empty.
     * Even so, a derived class may need a completely different implementation, and therefore it is still virtual.
     *
     * @param player pointer to player
     * @return true if player can currently play a turn
     */
    public boolean canPlayTurn(Player player) {
        // check if player can play moves
        return player.hasPossibleMoves();
    }

    /**
     * Returns if a given move by a given player on given board is allowed.
     * A move is allowed if it is a possible move, meaning it is contained
     * in the player's possible moves vector.
     *
     * @param move location of move to check if legal
     * @param player pointer to player playing this move
     * @return true if move is legal and can be played
     */
    public boolean isMoveAllowed(Position move, Player player) {
        // copy vector by reference to avoid recalling getter function
        ArrayList<Position> possibleMoves = player.getPossibleMoves();

        // go over list
        for (int i = 0; i < possibleMoves.size(); i++) {
            // if move's location is equal to a possible move's location - it is allowed
            if (possibleMoves.get(i).equals(move)) {
                return true;
            }
        }
        // otherwise, given move is not a possible move for player - and therefor is not allowed
        return false;
    }

    /**
     * Updates the list of possible moves for the given player on the given board.
     *
     * @param player player of which to update moves
     * @param board  board on which game is played
     */
    public void updateMoveOptions(Player player, Board board) {
        CellType c = player.getColor();

        ArrayList<Position> possible = new ArrayList<Position>();

        for (Position cell : board.emptyCells()) {
            ArrayList<Position> positions = this.checkDirections(board, cell, c);

            for (Position p : positions) {
                // if at least one direction needs to be flipped - stop and add
                if (!p.equals(cell)) {
                    possible.add(cell);
                    break;
                }
            }
        }

        player.updatePossibleMoves(possible);
    }

    /**
     * Checks the a direction of the given position using given jumps, and returns the last square that must be
     * flipped in this direction.
     *
     * @param b board being played
     * @param prevStart position at which to check
     * @param c - color to play
     * @param rowJumps jumps of rows
     * @param colJumps jumps of columns
     * @return position of last square to flip, or given position if no squares should be flipped
     */
    private Position lastToChange(Board b, Position prevStart, CellType c, int rowJumps, int colJumps) {
        // initialize p to first no
        Position p = new Position(prevStart.getRow() + rowJumps, prevStart.getColumn() + colJumps);

        // while position is in range, and in the opposite color as prevStart
        while (b.inRange(p) && !b.isCellEmpty(p.getRow(), p.getColumn()) && !b.compareCellColors(c, p)) {
            // increment p
            p = new Position(p.getRow() + rowJumps, p.getColumn() + colJumps);
        }

        // check why loop was broken - is cell empty, same color as prevStart or edge
        // if cell is not in range or non-empty - return prevStart, we cannot make a move in this direction
        if (!b.inRange(p) || b.isCellEmpty(p.getRow(), p.getColumn())) {
            return prevStart;
        }

        // return the previous position as last position to flip.
        return new Position(p.getRow() - rowJumps, p.getColumn() - colJumps);
    }

    /**
     * Checks all 8 directions from given point, and finds if cells change color in that direction.
     * If so - returns last square to change color of, other wise returns prevStart itself (no change in that direction)
     *
     * @param b board being played
     * @param prevStart position at which to check
     * @param c color to play
     * @return list of last positions to flip in a given direction
     */
    private ArrayList<Position> checkDirections(Board b, Position prevStart, CellType c) {
        ArrayList<Position> positions = new ArrayList<Position>(8);

        // get last position in every direction
        positions.add(this.lastToChange(b, prevStart, c, 0, 1)); // right
        positions.add(this.lastToChange(b, prevStart, c, 0, -1)); // left
        positions.add(this.lastToChange(b, prevStart, c, -1, 0)); // up
        positions.add(this.lastToChange(b, prevStart, c, 1, 0)); // down
        positions.add(this.lastToChange(b, prevStart, c, -1, -1)); // up-left
        positions.add(this.lastToChange(b, prevStart, c, -1, 1)); // up-right
        positions.add(this.lastToChange(b, prevStart, c, 1, -1)); // down-left
        positions.add(this.lastToChange(b, prevStart, c, 1, 1)); // down-right

        return positions;
    }
};
