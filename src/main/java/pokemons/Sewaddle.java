package pokemons;

import moves.Metronome;
import moves.Rollout;
import ru.ifmo.se.pokemon.Move;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Sewaddle extends Pokemon implements Trainable {
    public Sewaddle(String s, int i) {
        super(s, i);
        setType(Type.BUG, Type.GRASS);
        setStats(45, 53,70,40,60,42);
        setMove(new Rollout(), new Metronome());
    }
    public void learnMove(Move move){
        addMove(move);
    }
}
