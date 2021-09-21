package pokemons;

import moves.DoubleTeam;
import moves.Facade;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class NidoranF extends Pokemon {
    public NidoranF(String s, int i) {
        super(s, i);
        setStats(55, 47, 52, 40, 40, 41);
        setType(Type.POISON);
        setMove(new Facade(), new DoubleTeam());
    }
}
