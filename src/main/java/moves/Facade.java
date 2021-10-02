package moves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 70, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        double damage = v;
        if(pokemon.getCondition().equals(Status.BURN) ||
                pokemon.getCondition().equals(Status.POISON) ||
                pokemon.getCondition().equals(Status.PARALYZE)){
            damage *= 2;
        }
        super.applyOppDamage(pokemon, damage);
    }

    @Override
    protected String describe() {
        return "Facade deals damage, and hits with double power if the user is burned, poisoned or paralyzed.";
    }
}
