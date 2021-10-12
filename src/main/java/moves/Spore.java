package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Spore extends StatusMove {
    public Spore() {
        super(Type.GRASS, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect.sleep(pokemon);
    }

    @Override
    protected String describe() {
        return "Spore puts the target to sleep.";
    }
}
