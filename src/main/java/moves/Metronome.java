package moves;

import pokemons.Trainable;
import ru.ifmo.se.pokemon.*;

public class Metronome extends StatusMove {
    public Metronome() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        if(pokemon instanceof Trainable){
            Move moveToLearn;
            switch ((int) (Math.random() * 8)){
                case 0:
                    moveToLearn = new Bonemerang();
                    break;
                case 1:
                    moveToLearn = new ExtremeSpeed();
                    break;
                case 2:
                    moveToLearn = new FireBlast();
                    break;
                case 3:
                    moveToLearn = new TakeDown();
                    break;
                case 4:
                    moveToLearn = new Rollout();
                    break;
                case 5:
                    moveToLearn = new HyperBeam();
                    break;
                case 6:
                    moveToLearn = new MudSlap();
                    break;
                case 7:
                    moveToLearn = new TeeterDance();
                    break;
                case 8:
                    moveToLearn = new Meditate();
                    break;
                default:
                    moveToLearn = new Spore();
                    break;

            }
            ((Trainable) pokemon).learnMove(moveToLearn);
        }
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "Metronome uses a random move chosen from nearly all existing moves, even if the user cannot normally learn it.";
    }
}
