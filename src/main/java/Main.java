import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle battleField = new Battle();

        battleField.addAlly(new Furret("Poke0", 3));
        battleField.addAlly(new Latias("Poke1", 2));
        battleField.addAlly(new Leavanny("Poke2", 4));

        battleField.addFoe(new Sentret("Poke3", 4));
        battleField.addFoe(new Sewaddle("Poke4", 3));
        battleField.addFoe(new Swadloon("Poke5`", 2));

        battleField.go();
    }
}
