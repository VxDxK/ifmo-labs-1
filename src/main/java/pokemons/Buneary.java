package pokemons;

import moves.Confide;
import moves.DoubleTeam;
import moves.Facade;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Buneary extends Pokemon {
    public Buneary(String s, int i) {
        super(s, i);
        setStats(55, 66, 44, 44, 56, 85);
        setType(Type.NORMAL);
        setMove(new Confide(), new Facade(), new DoubleTeam());
    }
}
