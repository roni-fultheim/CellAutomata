
public enum Cell {
    TREE, EMPTY, FIRE;

    /*
     * // examples public static final String ANSI_RESET = "\u001B[0m";
     *
     * public static final String ANSI_BLACK = "\u001B[30m";
     *
     * public static final String ANSI_RED = "\u001B[31m";
     *
     * public static final String ANSI_GREEN = "\u001B[32m";
     *
     * public static final String ANSI_YELLOW = "\u001B[33m";
     *
     * public static final String ANSI_BLUE = "\u001B[34m";
     *
     * public static final String ANSI_PURPLE = "\u001B[35m";
     *
     * public static final String ANSI_CYAN = "\u001B[36m";
     *
     * public static final String ANSI_WHITE = "\u001B[37m";
     *
     * public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
     *
     * public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
     *
     * public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
     *
     * public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
     *
     * public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
     *
     * public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
     *
     * public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
     *
     * public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
     *
     * // green text + background to create tree block (green) public static final String TREE_STRING =
     * ANSI_GREEN_BACKGROUND + ANSI_GREEN + "T " + ANSI_RESET;
     *
     * public static final String EMPTY_STRING = ANSI_WHITE_BACKGROUND + ANSI_WHITE + "E " + ANSI_RESET;
     *
     * public static final String FIRE_STRING = ANSI_YELLOW_BACKGROUND + ANSI_YELLOW + "F " + ANSI_RESET;
     */

    // static variables with ANSI escape codes
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";

    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // ANSI codes and string combination: colored text + background to create cell blocks
    public static final String TREE_STRING = ANSI_GREEN_BACKGROUND + ANSI_GREEN + "T " + ANSI_RESET; // green

    public static final String EMPTY_STRING = ANSI_WHITE_BACKGROUND + ANSI_WHITE + "E " + ANSI_RESET; // white

    public static final String FIRE_STRING = ANSI_YELLOW_BACKGROUND + ANSI_YELLOW + "F " + ANSI_RESET; // yellow

    /**
     * Method for returning string with ANSI escape codes to create cell blocks on screen
     * @return
     */
    @Override
    public String toString() {
        switch (this) {
            case TREE:
                return TREE_STRING;
            case EMPTY:
                return EMPTY_STRING;
            case FIRE:
                return FIRE_STRING;
            default:
                throw new IllegalArgumentException();
        }
    }
}
