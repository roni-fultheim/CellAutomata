
import java.util.Random;

/**
 * Class for generating boolean values according to the given probability easily
 */
public class Randomizer {
    // random number generator
    private Random random;

    // probability of a cell starting as a tree
    private double d;

    // probability of empty cell growing a tree
    private double p;

    // probability of catching fire from neighbor
    private double g;

    // probability of catching fire randomly (lightning)
    private double f;

    /**
     * Creates a new Randomizer by the default probabilities (0.5 all)
     */
    public Randomizer() {
        this(0.5, 0.5, 0.5, 0.5);
    }

    /**
     * Creates a new Randomizer by the given probabilities
     * @param d prob. of cell starting as a tree
     * @param p prob. of empty cell growing a tree
     * @param g prob. of catching fire from neighbor
     * @param f prob. of catching fire randomly (lightning)
     */
    public Randomizer(double d, double p, double g, double f) {
        this.random = new Random();
        this.d = d;
        this.p = p;
        this.g = g;
        this.f = f;
    }

    /**
     * Returns a boolean value by the given probability
     * @param prob probabilty of getting "true"
     * @return true prob of the times, false (1-prob) of the times
     */
    private boolean generateValueByProbability(double prob) {
        double randomNum = this.random.nextDouble(); // return number between 0 to 1
        // if number is smaller than or equal to given probability -> then return true
        return randomNum <= prob;
    }

    /**
     * Returns if a cell should start off as a tree, according to the wanted probability
     * @return true d of the times, false (1-d) of the times
     */
    public boolean startAsTree() {
        return this.generateValueByProbability(this.d);
    }

    /**
     * Returns if a tree currently caught on fire from neighbor, according to the wanted probability
     * @return true g of the times, false (1-g) of the times
     */
    public boolean caughtFireFromNeighbor() {
        return this.generateValueByProbability(this.g);
    }

    /**
     * Returns if a tree currently caught on fire randomly, according to the wanted probability
     * @return true f of the times, false (1-f) of the times
     */
    public boolean caughtFireRandomly() {
        return this.generateValueByProbability(this.f);
    }

    /**
     * Returns if a tree currently grew in an empty cell, according to the wanted probability
     * @return true p of the times, false (1-p) of the times
     */
    public boolean grewTree() {
        return this.generateValueByProbability(this.p);
    }

}
