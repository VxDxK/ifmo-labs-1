package pokemons;

import moves.HyperBeam;
import moves.Spore;
import moves.TakeDown;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Sentret extends Pokemon {
    public Sentret(String s, int i) {
        super(s, i);
        setType(Type.NORMAL);
        setStats(35, 46, 34, 35, 45, 20);
        setMove(new Spore(), new HyperBeam(), new TakeDown());
    }
}
