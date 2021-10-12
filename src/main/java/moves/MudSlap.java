package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class MudSlap extends SpecialMove {
    public MudSlap() {
        super(Type.GROUND, 20, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ACCURACY, -1);
    }

    @Override
    protected String describe() {
        return "Mud-Slap deals damage and lowers the target's Accuracy by one stage.";
    }
}
