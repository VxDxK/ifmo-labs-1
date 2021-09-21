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
        if(pokemon.getStat(Stat.SPECIAL_DEFENSE)  >= -4){
            pokemon.setMod(Stat.SPECIAL_DEFENSE, -2);
        }else if(pokemon.getStat(Stat.SPECIAL_DEFENSE)  == -5){
            pokemon.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }

    @Override
    protected String describe() {
        return "Fake Tears lowers the target's Special Defense by two stages.";
    }
}
