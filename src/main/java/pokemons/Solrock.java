package pokemons;

import moves.DoubleSlap;
import moves.FakeTears;
import moves.Rest;
import moves.Swagger;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Solrock extends Pokemon {
    public Solrock(String s, int i) {
        super(s, i);
        setStats(90, 95, 85, 55, 65, 70);
        setType(Type.ROCK, Type.PSYCHIC);
        setMove(new Rest(), new FakeTears(), new DoubleSlap(), new Swagger());
    }
}
