public class Main {

    public static void main(String[] args) {
        /** SETUP **/

        // TODO - add parser that parses arguments from commandline and creates randomizer accordingly

        // create randomizer - default values (0.5 all)
        Randomizer randomizer = new Randomizer();

        // create listener to follow measures
        Listener list = new Listener();

        // create automaton matrix
        // size is 101*101 to allow a border of empty cells
        int mSize = 101;
        Cell[][] matrix = new Cell[mSize][mSize];

        // initialize matrix
        initMatrix(matrix, randomizer);

        // create new manager
        AutomatonManager manager = new AutomatonManager(matrix, randomizer, list);

        /** START GAME **/
        // start game: 200 rounds
        for (int round = 0; round < 200; round++) {
            // clear screen
            clearScreen();

            // show automaton
            for (int i = 0; i < mSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    // print cell
                    System.out.print(matrix[i][j]);
                }
                // end line at end of row
                System.out.println();
            }

            // play next round (round i+1)
            manager.playRound();

            // sleep 0.6 seconds (so that user can see new state)
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // clear screen for last round
        clearScreen();

        // show last round of the automaton, don't clear screen
        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

        // TODO - show local/global measures / do something with them - maybe write to files?

    }

    /**
     * Clears screen using ANSI escape code
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Initializes the given matrix by the given randomized - beautifier
     * @param matrix to initialize
     * @param rand randomizer with probabilites
     */
    public static void initMatrix(Cell[][] matrix, Randomizer rand) {
        // get inner matrix size
        int mSize = matrix.length - 1;

        // initialize inner matrix (real automaton) to empty\tree according to given probability (in randomizer)
        for (int i = 1; i < mSize; i++) {
            for (int j = 1; j < mSize; j++) {
                // randomly set to tree (probability of d) or empty (prob. 1-d)
                if (rand.startAsTree()) {
                    matrix[i][j] = Cell.TREE;
                } else {
                    matrix[i][j] = Cell.EMPTY;
                }
            }
        }

        // initialize border cells to empty
        // return to outer matrix size
        mSize++;

        // top row
        for (int i = 0; i < mSize; i++) {
            matrix[0][i] = Cell.EMPTY;
        }
        // bottom row
        for (int i = 0; i < mSize; i++) {
            matrix[mSize - 1][i] = Cell.EMPTY;
        }
        // left column
        for (int i = 0; i < mSize; i++) {
            matrix[i][0] = Cell.EMPTY;
        }
        // right column
        for (int i = 0; i < mSize; i++) {
            matrix[i][mSize - 1] = Cell.EMPTY;
        }
    }
}
