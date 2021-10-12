package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class ExtremeSpeed extends PhysicalMove{
    public ExtremeSpeed() {
        super(Type.NORMAL, 80, 100, 2, 1);
    }

    @Override
    protected String describe() {
        return "Extreme Speed deals damage and attacks before the majority of other moves";
    }

}
