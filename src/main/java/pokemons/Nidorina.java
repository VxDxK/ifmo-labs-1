package pokemons;

import moves.Growl;

public class Nidorina extends NidoranF{


    public Nidorina(String s, int i) {
        super(s, i);
        setStats(70, 62, 67, 55, 55,56);
        addMove(new Growl());
    }
}
