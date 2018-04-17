package reversiap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gamecore.AutomatonManager;
import gamecore.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ReversiGameController implements Initializable {
    @FXML
    private HBox root;

    @FXML
    private GridPane board;

    // global measure = (#of tree cells)/(#of empty cells)
    @FXML
    private Label globalMeasure;

    // local measure = #of 10*10 cells where at least 2/3 of the subboard's cells are empty or trees
    @FXML
    private Label localMeasure;

    @FXML
    private Label messages;

    @FXML
    private Button start;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // open file to get settings
        // create FileReader from given path - must use try-catch (exception thrown if file does not exist)
        // read and parse probabilities:
        double p = 0.5, g = 0.5, f = 0.5, d = 0.5; // initialize to default values - all 0.5

        try {
            File path = new File("settings.txt");

            // if there is no settings file - use default values (all 0.5)
            if (!path.exists() || path.isDirectory()) {
                this.createAutomata(p, g, f, d);
                return;
            }

            FileReader fReader = new FileReader(path);

            // create buffered reader for easy file reading
            BufferedReader br = new BufferedReader(fReader);

            // reading probabilities + parsing
            p = Double.parseDouble(br.readLine().trim());
            g = Double.parseDouble(br.readLine().trim());
            f = Double.parseDouble(br.readLine().trim());
            d = Double.parseDouble(br.readLine().trim());

            // finished reading - close reader
            br.close();

        } catch (FileNotFoundException e) {
            // catch file not found exception
            System.out.println("File was not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occured while reading input file");
            e.printStackTrace();
        }

        // create the automata
        this.createAutomata(p, g, f, d);

    }

    /**
     * Creates the cell automata with the given parameters
     * @param p tree creation prob.
     * @param g catching fire from neighbor prob.
     * @param f catching fire randomly prob.
     * @param d start as tree prob.
     */
    private void createAutomata(double p, double g, double f, double d) {
        // intialize labels
        this.globalMeasure.setText("Global measure: ");
        this.localMeasure.setText("Local measure: ");

        this.messages.setWrapText(true);

        // create listener with the given labels
        Listener listener = new Listener(this.globalMeasure, this.localMeasure, this.messages);

        // create board by given probability
        // size is 101*101 to allow for unchanging empty cells frame
        Board board = new Board(101, d);

        // create randomizer
        Randomizer randomizer = new Randomizer(p, g, f);

        // allocate game manager on stack, sending abstract types by pointer and actual types by reference
        AutomatonManager game_manger = new AutomatonManager(board, randomizer, listener);

        // create board controller to show board
        BoardController boardController = new BoardController(board, game_manger);
        boardController.setPrefWidth(650);
        boardController.setPrefHeight(650);
        this.root.getChildren().add(0, boardController);

        // TODO
        // boardController.startAutomaton();

        // set the button
        this.start.setOnAction((event) -> {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    boardController.startAutomaton();
                }
            });
            thread.start();

        });

        boardController.draw();

        System.out.println("after draw");
    }
}