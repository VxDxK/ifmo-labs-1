import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle battleField = new Battle();
        battleField.addAlly(new Solrock("Lando", 2));
        battleField.addAlly(new Buneary("Loly", 3));
        battleField.addAlly(new Lopunny("Olly", 2));

        battleField.addFoe(new NidoranF("Kella", 4));
        battleField.addFoe(new Nidorina("Lolita", 2));
        battleField.addFoe(new Nidoqueen("Irina", 3));
        battleField.go();
    }
}
