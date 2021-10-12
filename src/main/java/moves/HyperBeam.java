package moves;

import ru.ifmo.se.pokemon.*;

public class HyperBeam extends SpecialMove {
    private int turn = 0;
    public HyperBeam() {
        super(Type.NORMAL, 150, 90);
    }

    @Override
    protected double calcBaseDamage(Pokemon pokemon, Pokemon pokemon1) {
        power = 150 / (double)((turn % 2) + 1);
        turn++;
        return super.calcBaseDamage(pokemon, pokemon1);
    }

    @Override
    protected String describe() {
        return "Hyper Beam deals damage, but the user must recharge on the next turn.";
    }
}
