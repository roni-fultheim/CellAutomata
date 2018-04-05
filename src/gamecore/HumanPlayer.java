package gamecore;

/*
 * Roni Fultheim, ID: 313465965
 * Yael Hacmon, ID: 313297897
 * HumanPlayer.cpp
 */
public class HumanPlayer extends Player {

    HumanPlayer(Player other) {
        super(other);
    }

    public HumanPlayer(String name, ElementInBoard c) {
        super(name, c);
    }
}