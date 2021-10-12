package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class TakeDown extends PhysicalMove {
    public TakeDown() {
        super(Type.NORMAL, 90, 85);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
    }

    @Override
    protected void applySelfDamage(Pokemon pokemon, double v) {
        pokemon.setMod(Stat.HP, (int)Math.round(v));
    }

    @Override
    protected String describe() {
        return "Take Down deals damage, but the user receives 1⁄4 of the damage it inflicted in recoil." +
                " In other words, if the attack does 100 HP damage to the opponent, the user will lose 25 HP.";
    }
}
