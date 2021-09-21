package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class DoubleSlap extends PhysicalMove {
    public DoubleSlap() {
        super(Type.NORMAL, 15, 85);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        applyOppDamage(pokemon, v, 1);
    }

    protected void applyOppDamage(Pokemon pokemon, double v, int hit){
        if(hit > 5) return;
        if((hit == 2 || hit == 3) && Math.random() > 0.375){
            return;
        }
        if((hit == 4 || hit == 5) && Math.random() > 0.125){
            return;
        }
        pokemon.setMod(Stat.HP, (int) (0.15 * hit * v));
    }

    @Override
    protected String describe() {
        return "Double Slap hits 2-5 times per turn used. " +
                "The probability of each interval is shown to the right, " +
                "with the total power after each hit.";
    }
}
