
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Listens to the cellular automaton, and observes the changes. Holds the lables that should be changed
 */
public class Listener {
    // global measure = (#of tree cells)/(#of empty cells)
    private double globalMeasure;

    // local measure = #of 10*10 cells where at least 2/3 of the subboard's cells are empty or trees
    private int localMeasure;

    /**
     * Creates listener. All measures are initialized to 0.
     */
    public Listener() {
        this.globalMeasure = 0;
        this.localMeasure = 0;
    }

    /**
     * Updates current global measurement (#of tree cells)/(#of empty cells)
     * @param measure new measurement
     */
    public void changeGlobalMeasure(double measure) {
        this.globalMeasure = measure;
    }

    /**
     * Updates current local measurement (#of 10*10 cells where at least 2/3 of the subboard's cells are empty or trees)
     * @param local new measurement
     */
    public void changeLocalMeasure(int local) {
        this.localMeasure = local;
    }

}