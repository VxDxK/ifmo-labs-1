package pokemons;

import moves.FireBlast;
import moves.HyperBeam;
import moves.Meditate;
import moves.MudSlap;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Latias extends Pokemon {
    public Latias(String s, int i) {
        super(s, i);
        setType(Type.DRAGON, Type.PSYCHIC);
        setStats(80, 80, 90, 110, 130, 110);
        setMove(new MudSlap(), new FireBlast(), new Meditate(), new HyperBeam());
    }
}
