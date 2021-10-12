package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Bonemerang extends PhysicalMove{
    public Bonemerang() {
        super(Type.GROUND, 50, 90, 0, 2);
    }

    @Override
    protected String describe() {
        return "Bonemerang deals damage and will strike twice.";
    }

}
