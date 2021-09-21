package pokemons;

import moves.DragonClaw;

public class Lopunny extends Buneary{
    public Lopunny(String s, int i) {
        super(s, i);
        setStats(65, 76, 84, 54, 96, 105);
        addMove(new DragonClaw());
    }
}
