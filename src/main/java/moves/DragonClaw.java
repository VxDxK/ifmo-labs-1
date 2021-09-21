package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class DragonClaw extends PhysicalMove {
    public DragonClaw() {
        super(Type.DRAGON, 80, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        pokemon.setMod(Stat.HP, (int)v);
    }

    @Override
    protected String describe() {
        return "Dragon Claw deals damage with no additional effect.";
    }
}
