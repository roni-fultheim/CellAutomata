package reversiap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gamecore.Board;
import gamecore.ElementInBoard;
import gamecore.GameManager;
import gamecore.HumanPlayer;
import gamecore.StandardMoveLogic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ReversiGameController implements Initializable {
    @FXML
    private HBox root;

    @FXML
    private GridPane board;

    @FXML
    private Label currPlayerText;

    @FXML
    private Label whitePlayerScoreText;

    @FXML
    private Label blackPlayerScoreText;

    @FXML
    private Label messages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // open file to get settings
        // create FileReader from given path - must use try-catch (exception thrown if file does not exist)
        String size = "";
        String color1 = "";
        String color2 = "";

        try {
            File path = new File("settings.txt");

            // if there is no settings file - use default values
            if (!path.exists() || path.isDirectory()) {
                this.createGame(8, "Black", "White");
                return;
            }

            FileReader fReader = new FileReader(path);

            // create buffered reader for easy file reading
            BufferedReader br = new BufferedReader(fReader);

            // reading color of first player
            color1 = br.readLine().trim();

            // reading color of second player
            color2 = br.readLine().trim();

            // reading size of board
            size = br.readLine().trim();

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

        this.createGame(Integer.parseInt(size), color1, color2);

    }

    private void createGame(int bSize, String playerX, String playerO) {
        // intialize labels
        this.currPlayerText.setText("Current player: " + playerX);
        this.blackPlayerScoreText.setText(playerX + " player score: 2");
        this.whitePlayerScoreText.setText(playerO + " player score: 2");

        this.messages.setWrapText(true);

        // create listener with the given labels
        ReversiListener listener = new ReversiListener(playerX, playerO, this.currPlayerText, this.whitePlayerScoreText,
                this.blackPlayerScoreText, this.messages);

        // create board by given size
        Board board = new Board(bSize);

        // create move logic
        StandardMoveLogic ml = new StandardMoveLogic();

        // allocate dynamically due to using abstract base type
        // first player is always the human player and is black
        HumanPlayer player1 = new HumanPlayer(playerX, ElementInBoard.FIRE);

        HumanPlayer player2 = new HumanPlayer(playerO, ElementInBoard.TREE);

        // allocate game manager on stack, sending abstract types by pointer and actual types by reference
        GameManager game_manger = new GameManager(board, player1, player2, ml, listener);

        // create board controller to show board
        BoardController boardController = new BoardController(board, playerX, playerO, game_manger);
        boardController.setPrefWidth(480);
        boardController.setPrefHeight(480);
        this.root.getChildren().add(0, boardController);

        boardController.draw();
    }
}