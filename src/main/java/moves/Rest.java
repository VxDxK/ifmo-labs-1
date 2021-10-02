package moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect effect = new Effect().condition(Status.SLEEP).turns(2);
        pokemon.addEffect(effect);
        pokemon.setMod(Stat.HP,  -(int)(pokemon.getStat(Stat.HP) -  pokemon.getHP()));
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "User sleeps for 2 turns, but user is fully healed.";
    }
}
