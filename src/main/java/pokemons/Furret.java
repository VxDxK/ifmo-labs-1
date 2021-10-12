package pokemons;

import moves.ExtremeSpeed;

public class Furret extends Sentret{
    public Furret(String s, int i) {
        super(s, i);
        setStats(85, 76, 64, 45, 55, 90);
        addMove(new ExtremeSpeed());
    }
}
