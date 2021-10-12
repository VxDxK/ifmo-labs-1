package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Rollout extends PhysicalMove {
    public Rollout() {
        super(Type.ROCK, 30, 90);
    }

    @Override
    protected double calcBaseDamage(Pokemon pokemon, Pokemon pokemon1) {
        double s = super.calcBaseDamage(pokemon, pokemon1);
        for(int i = 2; i < 9 && super.checkAccuracy(pokemon, pokemon1); i += 2){
            s += (0.4D * (double)pokemon.getLevel() + 2.0D) * this.power * i / 150.0D;
        }
        return s;
    }

    @Override
    protected String describe() {
        return "Rollout deals damage for 5 turns, doubling in power each turn. The move stops if it misses on any turn. +" +
                "If it doesn't miss, Rollout will deal 30, 60, 120, 240 and 480 base power damage each turn respectively.";
    }
}
