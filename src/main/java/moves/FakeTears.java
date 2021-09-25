package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class FakeTears extends StatusMove {
    public FakeTears() {
        super(Type.DARK, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_DEFENSE, -2);
    }

    @Override
    protected String describe() {
        return "Fake Tears lowers the target's Special Defense by two stages.";
    }
}
