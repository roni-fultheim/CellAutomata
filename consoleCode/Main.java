
public class Main {
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

    public static void main(String[] args) {
        Cell[][] matrix = new Cell[100][100];

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                matrix[i][j] = Cell.EMPTY;
            }
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println("");
        }

        System.out.print(Cell.TREE);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        clearScreen();

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.print(Cell.FIRE);
            }
            System.out.println("");
        }

    }

    /**
     * Clears screen using ANSI escape code
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
