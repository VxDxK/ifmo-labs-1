package pokemons;


import moves.TeeterDance;

public class Leavanny extends Swadloon{
    public Leavanny(String s, int i) {
        super(s, i);
        setStats(75, 103, 80, 70, 80, 92);
        addMove(new TeeterDance());
    }
}
