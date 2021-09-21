package pokemons;

import moves.ScaryFace;
import ru.ifmo.se.pokemon.Type;

public class Nidoqueen extends Nidorina{
    public Nidoqueen(String s, int i) {
        super(s, i);
        setStats(90, 92, 87, 75, 85, 86);
        addType(Type.GROUND);
        addMove(new ScaryFace());
    }
}
