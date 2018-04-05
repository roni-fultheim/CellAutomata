package gamecore;

public class Position {
    // row of location on map
    private int row_;

    // column of location on map
    private int col_;

    public Position(int row, int column) {
        this.row_ = row;
        this.col_ = column;
    }

    @Override
    public boolean equals(Object other) {
        // if other object is a point - return the comparison
        if (other instanceof Position) {
            return this.equals((Position) other);
        }
        // otherwise
        return false;
    }

    public boolean equals(Position other) {
        return (this.getRow() == other.getRow() && this.getColumn() == other.getColumn());
    }

    /**
     * Getter for row.
     * @return row of location
     */
    public int getRow() {
        return this.row_;
    }

    /**
     * Getter for column.
     * @return column of location
     */
    public int getColumn() {
        return this.col_;
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(this.row_ + 1) + "," + Integer.toString(this.col_ + 1) + ")";
    }

    public void set(int row, int col) {
        this.row_ = row;
        this.col_ = col;
    }

    public void set(Position loc) {
        this.set(loc.getRow(), loc.getColumn());
    }

    public void increment(int incR, int incC) {
        this.row_ += incR;
        this.col_ += incC;
    }
}