package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class FireBlast extends SpecialMove{
    public FireBlast() {
        super(Type.FIRE, 110, 85);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if(0.1 <= Math.random()){
            Effect.burn(pokemon);
        }
    }

    @Override
    protected String describe() {
        return "Fire Blast deals damage and has a 10% chance of burning the target.";
    }
}
