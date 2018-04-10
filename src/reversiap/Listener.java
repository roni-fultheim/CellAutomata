package reversiap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Listens to the cellular automaton, and observes the changes. Holds the lables that should be changed
 */
public class Listener {
    // global measure = (#of tree cells)/(#of empty cells)
    @FXML
    private Label globalMeasure;

    // local measure = #of 10*10 cells where at least 2/3 of the subboard's cells are empty or trees
    @FXML
    private Label localMeasure;

    @FXML
    private Label messages;

    /**
     * Creates listener with given labels
     * @param globalMeasure label of global measure
     * @param localMeasure label of local measure
     * @param m messages to user
     */
    public Listener(Label global, Label local, Label m) {
        this.globalMeasure = global;
        this.localMeasure = local;
        this.messages = m;
    }

    /**
     * Updates the message to the player. For showing problems, conclusions, etc.
     * @param m message to show
     */
    public void updateMessage(String m) {
        this.messages.setText(m);
    }

    /**
     * Updates current global measurement (#of tree cells)/(#of empty cells)
     * @param measure new measurement
     */
    public void changeGlobalMeasure(Double measure) {
        this.globalMeasure.setText("Global measure: " + measure);
    }

    /**
     * Updates current local measurement (#of 10*10 cells where at least 2/3 of the subboard's cells are empty or trees)
     * @param local new measurement
     */
    public void changeLocalMeasure(int local) {
        this.localMeasure.setText("Local measure: " + local);
    }

}