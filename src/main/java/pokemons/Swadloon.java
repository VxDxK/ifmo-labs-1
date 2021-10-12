package pokemons;

import moves.Bonemerang;

public class Swadloon extends Sewaddle{
    public Swadloon(String s, int i) {
        super(s, i);
        setStats(55, 63, 90, 50, 80, 42);
        addMove(new Bonemerang());
    }
}
